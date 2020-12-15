package pl.edu.agh.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import lombok.Setter;
import pl.edu.agh.model.Account;
import pl.edu.agh.model.Budget;
import pl.edu.agh.service.BudgetService;
import pl.edu.agh.util.Router;
import pl.edu.agh.util.View;
import pl.edu.agh.viewelements.AccountViewElement;
import pl.edu.agh.viewelements.BudgetViewElement;

import java.util.Calendar;


public class BudgetController {
    public Label yearLabel;

    private int currentYear;
    @Setter
    private BudgetService budgetService;

    public void backButtonClicked(MouseEvent mouseEvent) {
        Router.routeTo(View.MENU);
    }

    private int budgetNumber = 0;

    BudgetViewElement addBudgetToPane(Budget budget){
        if (budgetNumber <= 10 ){
            BudgetViewElement budgetViewElement = new BudgetViewElement(budget);
            gridPane.add(budgetViewElement, budgetNumber % 4, budgetNumber / 4);
            budgetNumber++;
            return budgetViewElement;
        }
        return null;
    }

    @FXML
    public GridPane gridPane;

    @FXML
    public void initialize(){
        currentYear = Calendar.getInstance().get(Calendar.YEAR);
        this.yearLabel.setText(Integer.toString(currentYear));
    }

    public void refreshData(){
        if (budgetService != null){
            for (Budget budget : budgetService.getBudgetsByYear(currentYear)){
                Platform.runLater(() -> addBudgetToPane(budget));
            }
        }
    }

    public void nextYear(ActionEvent event) {
        currentYear++;
        this.yearLabel.setText(Integer.toString(currentYear));
        this.gridPane.getChildren().clear();
        this.budgetNumber = 0;
        refreshData();
    }

    public void prevYear(ActionEvent event) {
        currentYear--;
        this.yearLabel.setText(Integer.toString(currentYear));
        this.gridPane.getChildren().clear();
        this.budgetNumber = 0;
        refreshData();
    }
}
