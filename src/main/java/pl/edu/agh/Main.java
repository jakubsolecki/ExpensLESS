package pl.edu.agh;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pl.edu.agh.controller.AccountController;
import pl.edu.agh.controller.CategoryController;
import pl.edu.agh.controller.AccountDialogController;
import pl.edu.agh.guice.AppModule;
import pl.edu.agh.model.Account;
import pl.edu.agh.model.Category;
import pl.edu.agh.model.Subcategory;
import pl.edu.agh.service.AccountService;
import pl.edu.agh.service.CategoryService;
import pl.edu.agh.util.Router;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main extends Application {

    private AccountService accountService;
    private Pane mainPane;
    private CategoryService categoryService;

    public void createMockAccounts(){
        accountService.createAccount(new Account("Moje konto 1", 21.37));
        accountService.createAccount(new Account("Moje konto 2", 21.37));
        accountService.createAccount(new Account("Moje konto 3", 21.37));
        accountService.createAccount(new Account("Moje konto 4", 21.37));
    }

    public void createMockCategories() {
        List<Category> categoryList = Arrays.asList(
                new Category("Category 1"),
                new Category("Category 2"),
                new Category("Category 3"),
                new Category("Category 4")
        );

        for (Category category : categoryList) {
            categoryService.createCategory(category);
        }

        categoryService.createSubcategory(new Subcategory("Subcategory a", categoryList.get(0)));
        categoryService.createSubcategory(new Subcategory("Subcategory b", categoryList.get(0)));
        categoryService.createSubcategory(new Subcategory("Subcategory c", categoryList.get(0)));
        categoryService.createSubcategory(new Subcategory("Subcategory e", categoryList.get(1)));
        categoryService.createSubcategory(new Subcategory("Subcategory f", categoryList.get(1)));
        categoryService.createSubcategory(new Subcategory("Subcategory g", categoryList.get(1)));
        categoryService.createSubcategory(new Subcategory("Subcategory h", categoryList.get(2)));
        categoryService.createSubcategory(new Subcategory("Subcategory i", categoryList.get(3)));
    }


    @Override
    public void start(Stage primaryStage) {
        Injector injector = Guice.createInjector(new AppModule());

        accountService = injector.getInstance(AccountService.class);
        categoryService = injector.getInstance(CategoryService.class);
        createMockAccounts();
        createMockCategories();

        try{
            initializeAccounts();
//            initializeHello();
            initializeCategories();
            initializeAccountsDialog();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        Scene mainScene = new Scene(mainPane);
        mainScene.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());

        Router.setMainScene(mainScene);
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

        Router.addPane("Account", accountsPane);
        mainPane = accountsPane;
    }

    private void initializeAccountsDialog() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/accountsDialog.fxml"));
        Pane accountsDialogPane = fxmlLoader.load();

        AccountDialogController controller = fxmlLoader.getController();
        controller.setAccountService(accountService);

        Router.addPane("AccountDialog", accountsDialogPane);
    }

    private void initializeHello() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/helloView.fxml"));
        Pane accountPane = fxmlLoader.load();

//        Router.addPane("Hello", accountPane);
    }

    private void initializeCategories () throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/categoriesView.fxml"));
        Pane pane = fxmlLoader.load();

        CategoryController controller = fxmlLoader.getController();
        controller.setCategoryService(categoryService);

        Router.addPane("Categories", pane);
    }
}
