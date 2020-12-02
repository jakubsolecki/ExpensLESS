package pl.edu.agh.controller;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import lombok.Setter;
import pl.edu.agh.model.Account;
import pl.edu.agh.service.AccountService;
import pl.edu.agh.viewelements.AccountViewElement;

import java.util.List;

public class AccountController {
    @FXML
    public GridPane gridPane;

    @Setter
    private AccountService accountService;

    private int accountsNumber = 0;

    private void addAccountToPane(Account account){
        gridPane.add(new AccountViewElement(account), accountsNumber % 3, accountsNumber / 3);
        accountsNumber++;
    }


    @FXML
    public void initialize() {
        new Thread(() -> {
            List<Account> accountList = accountService.getAllAccounts();
            Platform.runLater(() -> accountList.forEach(this::addAccountToPane));
        }).start();
    }
}

