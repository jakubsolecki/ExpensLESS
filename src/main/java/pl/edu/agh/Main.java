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
import pl.edu.agh.util.Router;

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
            BorderPane mainLayout = loader.load();
            Scene mainScene = new Scene(mainLayout);
            Router.setMainScene(mainScene);
            Router.addPane("Account", mainLayout);
            AccountController controller = loader.getController();
            controller.setAccountService(accountService);

            primaryStage.setTitle("ExpensLESS");
            primaryStage.setScene(mainScene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
