package pl.edu.agh.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import lombok.Setter;
import pl.edu.agh.controller.AccountController;
import pl.edu.agh.controller.AccountDetailsController;
import pl.edu.agh.controller.BudgetController;
import pl.edu.agh.controller.BudgetDetailsController;
import pl.edu.agh.model.Account;
import pl.edu.agh.model.Budget;
import pl.edu.agh.service.AccountService;
import pl.edu.agh.service.BudgetService;
import pl.edu.agh.service.CategoryService;
import pl.edu.agh.service.TransactionService;


public class Router {
    @Setter
    private static Scene mainScene;
    @Setter
    private static CategoryService categoryService;
    @Setter
    private static AccountService accountService;
    @Setter
    private static TransactionService transactionService;
    @Setter
    private static BudgetService budgetService;

    public static void routeTo(View view, Object object){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            switch (view) {
                case ACCOUNTS -> {
                    fxmlLoader.setLocation(Router.class.getResource("/view/accountsView.fxml"));
                    Pane pane = fxmlLoader.load();
                    AccountController controller = fxmlLoader.getController();
                    controller.setAccountService(accountService);
                    controller.loadData();
                    mainScene.setRoot(pane);
                }
                case ACCOUNT_DETAILS -> {
                    if (object == null) {
                        throw new IllegalArgumentException("Account is required");
                    }

                    Account account = (Account) object;
                    fxmlLoader.setLocation(Router.class.getResource("/view/categoriesView.fxml"));
                    Pane pane = fxmlLoader.load();
                    AccountDetailsController controller = fxmlLoader.getController();
                    controller.setAccount(account);
                    controller.setCategoryService(categoryService);
                    controller.setAccountService(accountService);
                    controller.setTransactionService(transactionService);
                    controller.loadData();
                    mainScene.setRoot(pane);
                }
                case MENU -> {
                    fxmlLoader.setLocation(Router.class.getResource("/view/menuView.fxml"));
                    Pane pane = fxmlLoader.load();
                    mainScene.setRoot(pane);
                }
                case BUDGETS -> {

                    fxmlLoader.setLocation(Router.class.getResource("/view/budgetsView.fxml"));
                    Pane pane = fxmlLoader.load();
                    BudgetController budgetController = fxmlLoader.getController();
                    budgetController.setBudgetService(budgetService);
                    budgetController.setCategoryService(categoryService);
                    budgetController.refreshData();
                    mainScene.setRoot(pane);
                }
                case BUDGET_DETAILS -> {
                    if (object == null) {
                        throw new IllegalArgumentException("Account is required");
                    }
                    fxmlLoader.setLocation(Router.class.getResource("/view/budgetDetailsView.fxml"));
                    Pane pane = fxmlLoader.load();
                    BudgetDetailsController controller = fxmlLoader.getController();
                    controller.setBudget((Budget)object);
                    controller.setBudgetService(budgetService);
                    controller.setCategoryService(categoryService);
                    controller.loadData();
                    mainScene.setRoot(pane);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void routeTo(View view){
        routeTo(view, null);
    }
}
