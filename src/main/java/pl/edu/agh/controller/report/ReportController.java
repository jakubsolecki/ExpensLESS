package pl.edu.agh.controller.report;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Text;
import javafx.util.Duration;
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

//    NOREMOVE
/*    private final Map<Text, Group> dataLabelsMap = new HashMap<>();
    private final Map<Node, ChangeListener<Parent>> cl1Map = new HashMap<>();
    private final Map<Node, ChangeListener<Bounds>> cl2Map = new HashMap<>();
    private final List<Text> dataLabels = new LinkedList<>();
*/
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

            var totalOutcome = BigDecimal.ZERO;
            var totalIncome = BigDecimal.ZERO;

            XYChart.Series<String, Double> outcomeSeries = new XYChart.Series<>();
            outcomeSeries.setName("Wydatki");

            XYChart.Series<String, Double> incomeSeries = new XYChart.Series<>();
            incomeSeries.setName("Przychody");

            for (Budget budget : budgetList) {
                BigDecimal monthPlanned = BigDecimal.ZERO;
                BigDecimal monthOutcome = BigDecimal.ZERO;
                BigDecimal monthIncome = BigDecimal.ZERO;

                for (var subcatBud : budget.getSubcategoryBudgetList()) {
                    var balance = budgetService.calculateBudgetBalance(budget, subcatBud.getSubcategory());
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

                var data = new XYChart.Data<>(budget.getMonth().toString(), monthOutcome.doubleValue());
                var data2 = new XYChart.Data<>(budget.getMonth().toString(), monthIncome.doubleValue());

                var tooltip1 = new Tooltip();
                tooltip1.setText(String.valueOf(monthOutcome.doubleValue()));
                Tooltip.install(data.getNode(), tooltip1);

//                NOREMOVE
/*                data.nodeProperty().addListener((ov, oldNode, node) -> {
                    if (node != null) {
                        displayLabelForData(data);
                    }
                });*/

                outcomeSeries.getData().add(data);
                incomeSeries.getData().add(data2);
            }

            BigDecimal finalTotalOutcome = totalOutcome;
            BigDecimal finalTotalIncome = totalIncome;
            BigDecimal finalBalance = totalIncome.subtract(totalOutcome);

            Platform.runLater(() -> {
                outcome.setText(finalTotalOutcome.toString());
                income.setText(finalTotalIncome.toString());
                balance.setText(finalBalance.toString());
                barChart.getData().add(outcomeSeries);
                barChart.getData().add(incomeSeries);

                for (var series : barChart.getData()) {
                    for (var data : series.getData()) {
                        var tip = new Tooltip(data.getYValue().toString());
                        tip.setShowDelay(Duration.ZERO);
                        Tooltip.install(data.getNode(), tip);
                    }
                }

//                barChart.setCategoryGap(10);
            });
        }).start();
    }


//    NOREMOVE
/*    private void displayLabelForData(XYChart.Data<String, Double> data) {
        Node node = data.getNode();
        Text dataText = new Text(String.valueOf(data.getYValue()));

        ChangeListener<Parent> cl = (ov, oldParent, parent) -> {
            if (parent != null) {
                Group parentGroup = (Group) parent;
                parentGroup.getChildren().add(dataText);
                dataLabelsMap.put(dataText, parentGroup);
            }
        };

        cl1Map.put(node, cl);
        node.parentProperty().addListener(cl);

        ChangeListener<Bounds> cl2 = (ov, oldBounds, bounds) -> {
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
        };

        cl2Map.put(node, cl2);
        node.boundsInParentProperty().addListener(cl2);

    }
*/

    private void refreshData() {
        yearLabel.setText(String.valueOf(currentYear));

        // NOREMOVE
/*        for (var text : dataLabels) {
            text.setVisible(false); // NOREMOVE temporary solution ?
        }

        for (var x : barChart.getData()) {
            x.getData().ge
        }

        System.out.println("\n" + dataLabels.size() + "\n");

        for (var node : cl1Map.keySet()) {
            node.parentProperty().removeListener(cl1Map.get(node));
        }
        cl1Map.clear();

        for (var node : cl2Map.keySet()) {
            node.boundsInParentProperty().removeListener(cl2Map.get(node));
        }
        cl2Map.clear();

        for (var k : dataLabelsMap.keySet()) {
            dataLabelsMap.get(k).getChildren().remove(k);
        }
        dataLabelsMap.clear();

 */

        barChart.getData().clear();
        barChart.layout();

        loadData();
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
