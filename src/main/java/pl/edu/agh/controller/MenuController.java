package pl.edu.agh.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import pl.edu.agh.util.Router;
import pl.edu.agh.util.View;

public class MenuController {
    @FXML
    public void handleAccountsButton(ActionEvent event) {
        Router.routeTo(View.ACCOUNTS);
    }

    @FXML
    public void handleBudgetButton(ActionEvent event) {
        Router.routeTo(View.BUDGETS);
    }
}
