package pl.edu.agh.controller.report;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import lombok.Setter;
import pl.edu.agh.model.Budget;
import pl.edu.agh.model.SubcategoryBudget;
import pl.edu.agh.model.Type;
import pl.edu.agh.service.AccountService;
import pl.edu.agh.service.BudgetService;
import pl.edu.agh.service.CategoryService;
import pl.edu.agh.service.TransactionService;

import java.math.BigDecimal;
import java.time.Month;
import java.time.Year;
import java.util.*;

public class ReportController {
    @Setter
    private BudgetService budgetService;
    @Setter
    private CategoryService categoryService;
    @Setter
    TransactionService transactionService;
    @Setter
    private AccountService accountService;

    @FXML
    private ChoiceBox<String> typeChoiceBox;
    @FXML
    private ChoiceBox<Month> monthChoiceBox;
    @FXML
    private ChoiceBox<Year> yearChoiceBox;
    @FXML
    private Label income;
    @FXML
    private Label outcome;
    @FXML
    private Label balance;
    @FXML
    private Label yearLabel;
    @FXML
    private BarChart<String, Double> barChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    private int currentYear;

    public void initialize() {
        currentYear = Calendar.getInstance().get(Calendar.YEAR);
        yearLabel.setText(String.valueOf(currentYear));

        // TODO show actual month

        xAxis.setLabel("Miesiąc");
        yAxis.setLabel("Kwota");
    }

    public void loadData() {
        new Thread(() -> {
            List<Budget> budgetList = budgetService.getBudgetsByYear(currentYear);

            BigDecimal totalOutcome = BigDecimal.ZERO;
            BigDecimal totalIncome = BigDecimal.ZERO;

            XYChart.Series<String, Double> series = new XYChart.Series<>();
            series.setName("Wydano");

            for (Budget budget : budgetList) {
                BigDecimal monthPlanned = BigDecimal.ZERO;
                BigDecimal monthOutcome = BigDecimal.ZERO;
                BigDecimal monthIncome = BigDecimal.ZERO;

                for (SubcategoryBudget subcatBud : budget.getSubcategoryBudgetList()) {
                    BigDecimal balance = budgetService.calculateBudgetBalance(budget, subcatBud.getSubcategory());
                    if (subcatBud.getSubcategory().getCategory().getType() == Type.EXPENSE) {
                        monthOutcome = monthOutcome.add(balance);
                    }
                    else {
                        monthIncome = monthIncome.add(balance);
                    }
                    monthPlanned = monthPlanned.add(subcatBud.getPlannedBudget());
                }

                monthOutcome = monthOutcome.multiply(new BigDecimal(-1));
                monthIncome = monthIncome.multiply(new BigDecimal(-1));
                totalOutcome = totalOutcome.add(monthOutcome);
                totalIncome = totalIncome.add(monthIncome);

                XYChart.Data<String, Double> data = new XYChart.Data<>(budget.getMonth().toString(), monthOutcome.doubleValue());
                data.nodeProperty().addListener((ov, oldNode, node) -> {
                    if (node != null) {
                        displayLabelForData(data);
                    }
                });

                series.getData().add(data);
            }

            BigDecimal finalTotalOutcome = totalOutcome;
            BigDecimal finalTotalIncome = totalIncome;
            BigDecimal finalBalance = totalIncome.subtract(totalOutcome);

            Platform.runLater(() -> {
                outcome.setText(finalTotalOutcome.toString());
                income.setText(finalTotalIncome.toString());
                balance.setText(finalBalance.toString());
                barChart.getData().add(series);
                barChart.setCategoryGap(10);
            });
        }).start();
    }

    private void displayLabelForData(XYChart.Data<String, Double> data) {
        final Node node = data.getNode();
        final Text dataText = new Text(data.getYValue() + "");
        node.parentProperty().addListener((ov, oldParent, parent) -> {
            Group parentGroup = (Group) parent;
            parentGroup.getChildren().add(dataText);
        });

        node.boundsInParentProperty().addListener((ov, oldBounds, bounds) -> {
            dataText.setLayoutX(
                    Math.round(
                            bounds.getMinX() + bounds.getWidth() / 2 - dataText.prefWidth(-1) / 2
                    )
            );
            dataText.setLayoutY(
                    Math.round(
                            bounds.getMinY() - dataText.prefHeight(-1) * 0.5
                    )
            );
        });
    }

    private void refreshData() {
        yearLabel.setText(String.valueOf(currentYear));
    }

    public void nextYear(ActionEvent event) {
        currentYear++;
        refreshData();
    }

    public void prevYear(ActionEvent event) {
        currentYear--;
        refreshData();
    }

}
