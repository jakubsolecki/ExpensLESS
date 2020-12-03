package pl.edu.agh;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pl.edu.agh.controller.AccountController;
import pl.edu.agh.guice.AppModule;
import pl.edu.agh.model.Account;
import pl.edu.agh.model.Category;
import pl.edu.agh.model.Subcategory;
import pl.edu.agh.model.Transaction;
import pl.edu.agh.service.AccountService;
import pl.edu.agh.service.CategoryService;
import pl.edu.agh.service.TransactionService;
import pl.edu.agh.util.Router;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class Main extends Application {

    private AccountService accountService;
    private Pane mainPane;
    private CategoryService categoryService;
    private TransactionService transactionService;

    public List<Account> createMockAccounts(){

        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account("Moje konto 1", 21.37));
        accounts.add(new Account("Moje konto 2", 21.37));
        accounts.add(new Account("Moje konto 3", 21.37));
        accounts.add(new Account("Moje konto 4", 21.37));

        for (Account account : accounts){
            accountService.createAccount(account);
        }

        return accounts;
    }

    public List<Subcategory> createMockCategories() {
        List<Category> categoryList = Arrays.asList(
                new Category("Category 1"),
                new Category("Category 2"),
                new Category("Category 3"),
                new Category("Category 4")
        );

        for (Category category : categoryList) {
            categoryService.createCategory(category);
        }

        List<Subcategory> subcategories = new ArrayList<>();
        subcategories.add(new Subcategory("Subcategory a", categoryList.get(0)));
        subcategories.add(new Subcategory("Subcategory b", categoryList.get(0)));
        subcategories.add(new Subcategory("Subcategory c", categoryList.get(0)));
        subcategories.add(new Subcategory("Subcategory e", categoryList.get(1)));
        subcategories.add(new Subcategory("Subcategory f", categoryList.get(1)));
        subcategories.add(new Subcategory("Subcategory g", categoryList.get(1)));
        subcategories.add(new Subcategory("Subcategory h", categoryList.get(2)));
        subcategories.add(new Subcategory("Subcategory i", categoryList.get(3)));

        for (Subcategory subcategory : subcategories){
            categoryService.createSubcategory(subcategory);
        }

        return subcategories;
    }

    public void createMockTransactions(List<Account> accounts, List<Subcategory> subcategories){
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("Podatek CIT", -89.90,
                Calendar.getInstance().getTime(), accounts.get(0), subcategories.get(0)));
        transactions.add(new Transaction("Warzywa", -24.20,
                Calendar.getInstance().getTime(), accounts.get(0), subcategories.get(3)));
        transactions.add(new Transaction("Urodziny", 100.0,
                Calendar.getInstance().getTime(), accounts.get(1), subcategories.get(2)));
        transactions.add(new Transaction("Kwiaty", -42.21,
                Calendar.getInstance().getTime(), accounts.get(1), subcategories.get(1)));
        transactions.add(new Transaction("Podatek", -59.90,
                Calendar.getInstance().getTime(), accounts.get(2), subcategories.get(6)));
        transactions.add(new Transaction("Przelew", 200.0,
                Calendar.getInstance().getTime(), accounts.get(3), subcategories.get(4)));

        for (Transaction transaction : transactions){
            accountService.addTransaction(transaction.getAccount(), transaction);
        }
    }


    @Override
    public void start(Stage primaryStage) {
        Injector injector = Guice.createInjector(new AppModule());

        accountService = injector.getInstance(AccountService.class);
        categoryService = injector.getInstance(CategoryService.class);
        transactionService = injector.getInstance(TransactionService.class);
        List<Account> accounts = createMockAccounts();
        List<Subcategory> subcategories = createMockCategories();
        createMockTransactions(accounts, subcategories);

        try{
            initializeAccounts();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        Scene mainScene = new Scene(mainPane);
        mainScene.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());

        Router.setMainScene(mainScene);
        Router.setAccountService(accountService);
        Router.setCategoryService(categoryService);
        primaryStage.setTitle("ExpensLESS");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    private void initializeAccounts() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/accountsView.fxml"));
        Pane accountsPane = fxmlLoader.load();

        AccountController controller = fxmlLoader.getController();
        controller.setAccountService(accountService);
        mainPane = accountsPane;
    }
}
