package pl.edu.agh.controller;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import lombok.Setter;
import pl.edu.agh.model.Account;
import pl.edu.agh.service.AccountService;
import pl.edu.agh.viewElements.AccountViewElement;

public class AccountController {
    @FXML
    public GridPane gridPane;

    @Setter
    private AccountService accountService;

    @FXML
    public void initialize() {
        new Thread(() -> {
            int i = 0;
            for (Account account : accountService.getAllAccounts()){
                int finalI = i;
                Platform.runLater(() -> gridPane.add(new AccountViewElement(account), finalI, 0));
                i++;
            }
        }).start();
    }
}

