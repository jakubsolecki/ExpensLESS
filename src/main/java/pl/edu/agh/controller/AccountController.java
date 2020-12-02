package pl.edu.agh.controller;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Setter;
import pl.edu.agh.model.Account;
import pl.edu.agh.service.AccountService;
import pl.edu.agh.viewelements.AccountViewElement;

import java.io.IOException;
import java.util.List;

public class AccountController {
    @FXML
    public GridPane gridPane;

    @FXML
    public Button addButton;

    @Setter
    private AccountService accountService;

    private int accountsNumber = 0;

    private AccountViewElement addAccountToPane(Account account){
        if (accountsNumber <= 10 ){
            AccountViewElement accountViewElement = new AccountViewElement(account);
            gridPane.add(accountViewElement, accountsNumber % 4, accountsNumber / 4);
            accountsNumber++;
            return accountViewElement;
        }
        return null;
    }

    @FXML
    private void handleAddAction(ActionEvent event) throws IOException {
        AccountViewElement accountViewElement = addAccountToPane(new Account());
        if (accountViewElement != null){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/accountsDialog.fxml"));
            Pane pane = fxmlLoader.load();
            AccountDialogController controller = fxmlLoader.getController();
            controller.setAccountService(accountService);

            controller.setAccountViewElement(accountViewElement);


            Scene scene = new Scene(pane);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
        }
    }


    @FXML
    public void initialize() {
        new Thread(() -> {
            List<Account> accountList = accountService.getAllAccounts();
            Platform.runLater(() -> accountList.forEach(this::addAccountToPane));
        }).start();
    }
}

