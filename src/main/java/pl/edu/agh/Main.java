package pl.edu.agh;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pl.edu.agh.controller.AccountController;
import pl.edu.agh.guice.AppModule;
import pl.edu.agh.model.Account;
import pl.edu.agh.service.AccountService;

import java.io.IOException;

public class Main extends Application {
    public void createMockAccounts(AccountService accountService){
        accountService.createAccount(new Account("Moje konto 1", 21.37));
        accountService.createAccount(new Account("Moje konto 2", 21.37));
        accountService.createAccount(new Account("Moje konto 3", 21.37));
        accountService.createAccount(new Account("Moje konto 4", 21.37));
        accountService.createAccount(new Account("Moje konto 5", 21.37));
        accountService.createAccount(new Account("Moje konto 6", 21.37));
    }

    @Override
    public void start(Stage primaryStage) {
        Injector injector = Guice.createInjector(new AppModule());
        AccountService accountService = injector.getInstance(AccountService.class);
        createMockAccounts(accountService);

        try{
            var loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/accountsView.fxml"));
            BorderPane rootLayout = loader.load();

            AccountController controller = loader.getController();
            controller.setAccountService(accountService);

            configureStage(primaryStage, rootLayout);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void configureStage(Stage primaryStage, BorderPane rootLayout) {
        var scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.setTitle("ExpensLESS");
    }
}
