package pl.edu.agh.controller;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import lombok.Setter;
import pl.edu.agh.model.Account;
import pl.edu.agh.service.AccountService;
import pl.edu.agh.util.Router;
import pl.edu.agh.viewelements.AccountViewElement;

import javax.transaction.Transaction;
import java.util.List;

public class AccountController {
    @FXML
    public GridPane gridPane;

    @FXML
    public Button addButton;

    @Setter
    private AccountService accountService;

    private int accountsNumber = 0;

    private void addAccountToPane(Account account){
        if (accountsNumber <= 10 ){
            gridPane.add(new AccountViewElement(account), accountsNumber % 4, accountsNumber / 4);
            accountsNumber++;
        }

    }

    @FXML
    private void handleEditAction(ActionEvent event){
        Router.createDialog("AccountDialog");
    }


    @FXML
    public void initialize() {
        new Thread(() -> {
            List<Account> accountList = accountService.getAllAccounts();
            Platform.runLater(() -> accountList.forEach(this::addAccountToPane));
        }).start();
    }

}

