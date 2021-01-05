package pl.edu.agh.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import pl.edu.agh.util.Router;
import pl.edu.agh.util.View;

// TODO remove
public class MenuController {

    @FXML
    private BorderPane mainPayne;

    @FXML
    public void accountsButtonClicked(MouseEvent mouseEvent) {
        Router.routeTo(View.ACCOUNTS);

    }

    @FXML
    public void budgetsButtonClicked(MouseEvent mouseEvent) {
        Router.routeTo(View.BUDGETS);
    }

    @FXML
    public void handleAccountsButton(ActionEvent event) {
        Router.routeTo(View.ACCOUNTS);
    }

    @FXML
    public void handleBudgetButton(ActionEvent event) {
        Router.routeTo(View.BUDGETS);
    }
}
