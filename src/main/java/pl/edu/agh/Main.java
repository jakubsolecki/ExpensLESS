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
import pl.edu.agh.model.*;
import pl.edu.agh.service.AccountService;
import pl.edu.agh.service.BudgetService;
import pl.edu.agh.service.CategoryService;
import pl.edu.agh.service.TransactionService;
import pl.edu.agh.util.Router;

import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class Main extends Application {
    private AccountService accountService;
    private BudgetService budgetService;

    private Pane mainPane;
    private CategoryService categoryService;
    private TransactionService transactionService;

    @Override
    public void start(Stage primaryStage) {
        Injector injector = Guice.createInjector(new AppModule());

        accountService = injector.getInstance(AccountService.class);
        categoryService = injector.getInstance(CategoryService.class);
        transactionService = injector.getInstance(TransactionService.class);
        budgetService = injector.getInstance(BudgetService.class);
        List<Account> accounts = createMockAccounts();
        List<Subcategory> subcategories = createMockCategories();
        createMockTransactions(accounts, subcategories);
        try{
            initializeMenu();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        Scene mainScene = new Scene(mainPane);
        mainScene.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
        Router.setMainScene(mainScene);
        Router.setAccountService(accountService);
        Router.setCategoryService(categoryService);
        Router.setTransactionService(transactionService);
        Router.setBudgetService(budgetService);
        primaryStage.setTitle("ExpensLESS");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public List<Account> createMockAccounts(){

        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account("Moje konto 1", BigDecimal.valueOf(21.38)));
        accounts.add(new Account("Moje konto 2", BigDecimal.valueOf(21.36)));
        accounts.add(new Account("Moje konto 3", BigDecimal.valueOf(21.35)));
        accounts.add(new Account("Moje konto 4", BigDecimal.valueOf(21.21)));

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
        transactions.add(new Transaction("Podatek CIT", BigDecimal.valueOf(-89.97),
                Calendar.getInstance().getTime(), "Zapłacone za mandat", accounts.get(0), subcategories.get(0)));
        transactions.add(new Transaction("Warzywa", BigDecimal.valueOf(-24.20),
                Calendar.getInstance().getTime(), "Dla babci", accounts.get(0), subcategories.get(3)));
        transactions.add(new Transaction("Urodziny", BigDecimal.valueOf(100.0),
                Calendar.getInstance().getTime(), "U cioci na imieninach", accounts.get(1), subcategories.get(2)));
        transactions.add(new Transaction("Kwiaty", BigDecimal.valueOf(-42.21),
                Calendar.getInstance().getTime(), "Dla żony", accounts.get(1), subcategories.get(1)));
        transactions.add(new Transaction("Podatek", BigDecimal.valueOf(-59.90),
                Calendar.getInstance().getTime(), "Znowu", accounts.get(2), subcategories.get(6)));
        transactions.add(new Transaction("Przelew", BigDecimal.valueOf(200.0),
                Calendar.getInstance().getTime(), "Za buty", accounts.get(3), subcategories.get(4)));

        for (Transaction transaction : transactions){
            accountService.addTransaction(transaction.getAccount(), transaction);
        }
    }


    private void initializeMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/menuView.fxml"));
        Pane accountsPane = fxmlLoader.load();
        mainPane = accountsPane;
    }


}
