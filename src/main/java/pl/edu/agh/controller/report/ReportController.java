package pl.edu.agh.controller.report;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import lombok.Setter;
import pl.edu.agh.model.Budget;
import pl.edu.agh.model.SubcategoryBudget;
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
    private Label reportHeader;
    @FXML
    private StackedBarChart<String, Double> barChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    private int currentYear;

    public void initialize() {
        currentYear = Calendar.getInstance().get(Calendar.YEAR);
        reportHeader.setText("Podsumowanie budżetu na rok " + currentYear);

        xAxis.setLabel("Miesiąc");
        yAxis.setLabel("Kwota");
    }

    public void loadData() {
        new Thread(() -> {
            List<Budget> budgetList = budgetService.getBudgetsByYear(currentYear);

            xAxis.getCategories().addAll("Wydano", "Limit");

            for (Budget budget : budgetList) {
                BigDecimal sumPlanned = BigDecimal.ZERO;
                BigDecimal sumBalance = BigDecimal.ZERO;
                XYChart.Series<String, Double> series = new XYChart.Series<>();
                XYChart.Series<String, Double> series2 = new XYChart.Series<>();
                series.setName("Wydano");
                series2.setName("Zaplanowano");

                for (SubcategoryBudget subcatBud : budget.getSubcategoryBudgetList()) {
                    BigDecimal balance = budgetService.calculateBudgetBalance(budget, subcatBud.getSubcategory());
                    sumBalance = sumBalance.add(balance);
                    sumPlanned = sumPlanned.add(subcatBud.getPlannedBudget());
                }

                series.getData().add(new XYChart.Data<>("Kwota", sumBalance.doubleValue()));

                double delta = sumPlanned.subtract(sumBalance).doubleValue();

                if(delta > 0) {
                    series2.getData().add(new XYChart.Data<>("Kwota", delta));
                }

                barChart.getData().add(series);
                barChart.getData().add(series2);
            }

            Platform.runLater(() -> {
                barChart.setCategoryGap(20);
            });
        }).start();
    }

    private void addBudgetDataToChart(Budget budget) {

    }

}
