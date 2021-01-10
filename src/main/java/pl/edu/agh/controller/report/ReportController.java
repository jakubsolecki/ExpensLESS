package pl.edu.agh.controller.report;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
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
import java.util.zip.DeflaterOutputStream;

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
    private BarChart<String, Double> barChart;
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
//            List<XYChart.Series<String, Double>> seriesList = new LinkedList<>();

            XYChart.Series<String, Double> series = new XYChart.Series<>();
            series.setName("Wydano");

            for (Budget budget : budgetList) {
                BigDecimal sumPlanned = BigDecimal.ZERO;
                BigDecimal sumBalance = BigDecimal.ZERO;

                for (SubcategoryBudget subcatBud : budget.getSubcategoryBudgetList()) {
                    BigDecimal balance = budgetService.calculateBudgetBalance(budget, subcatBud.getSubcategory());
                    sumBalance = sumBalance.add(balance);
                    sumPlanned = sumPlanned.add(subcatBud.getPlannedBudget());
                }

                sumBalance = sumBalance.multiply(new BigDecimal(-1));

                XYChart.Data<String, Double> data = new XYChart.Data<>(budget.getMonth().toString(), sumBalance.doubleValue());
                data.nodeProperty().addListener(new ChangeListener<Node>() {
                    @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                        if (node != null) {
                            displayLabelForData(data);
                        }
                    }
                });
                series.getData().add(data);


            }

            Platform.runLater(() -> {

                barChart.getData().add(series);
                barChart.setCategoryGap(10);
            });
        }).start();
    }

    private void addBudgetDataToChart(Budget budget) {

    }

    private void displayLabelForData(XYChart.Data<String, Double> data) {
        final Node node = data.getNode();
        final Text dataText = new Text(data.getYValue() + "");
        node.parentProperty().addListener(new ChangeListener<Parent>() {
            @Override public void changed(ObservableValue<? extends Parent> ov, Parent oldParent, Parent parent) {
                Group parentGroup = (Group) parent;
                parentGroup.getChildren().add(dataText);
            }
        });

        node.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
            @Override public void changed(ObservableValue<? extends Bounds> ov, Bounds oldBounds, Bounds bounds) {
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
            }
        });
    }

}
