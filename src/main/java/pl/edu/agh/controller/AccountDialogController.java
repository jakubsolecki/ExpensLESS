package pl.edu.agh.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import lombok.Setter;
import pl.edu.agh.service.AccountService;
public class AccountDialogController {

    @FXML
    public Button cancelButton;

    @Setter
    private AccountService accountService;

    @FXML
    public void handleCancelAction(ActionEvent actionEvent) {

    }
}
