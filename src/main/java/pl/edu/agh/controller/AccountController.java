package pl.edu.agh.controller;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import lombok.Setter;
import pl.edu.agh.service.AccountService;
import pl.edu.agh.viewElements.AccountViewElement;

import java.util.concurrent.atomic.AtomicInteger;

public class AccountController {

    @FXML
    public GridPane gridPane;

    @Setter
    private AccountService accountService;

    @FXML
    public void initialize(){
        Platform.runLater(() -> {
            AtomicInteger i = new AtomicInteger();
            var list = accountService.getAllAccounts();
            list.forEach(account -> {gridPane.add(new AccountViewElement(account), i.get(), 0);
                i.getAndIncrement();
            });
        });


    }
}
