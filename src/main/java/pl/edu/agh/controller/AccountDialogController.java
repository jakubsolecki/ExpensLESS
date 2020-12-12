package pl.edu.agh.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Setter;
import pl.edu.agh.model.Account;
import pl.edu.agh.service.AccountService;
import pl.edu.agh.viewelements.AccountViewElement;

import java.math.BigDecimal;

public class AccountDialogController {

    @Setter
    private AccountService accountService;

    @FXML
    public TextField nameTextField;

    @FXML
    public TextField balanceTextField;

    @Setter
    private AccountController accountController;

    @FXML
    public void handleCancelAction(ActionEvent event) {
        closeDialog(event);
    }

    @FXML
    public void handleAddAction(ActionEvent event) {
        Account account = new Account();
        try {

            account.setName(nameTextField.getText());
            account.setBalance(new BigDecimal(balanceTextField.getText()));
        } catch (NumberFormatException e){
            System.out.println("Wrong format!");
            return;
        }
        if (accountController.addAccountToPane(account) != null){
            accountService.createAccount(account);
        }

        closeDialog(event);
    }

    private void closeDialog(ActionEvent event){
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
