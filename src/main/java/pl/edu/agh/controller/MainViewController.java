package pl.edu.agh.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import lombok.Setter;
import pl.edu.agh.model.Account;
import pl.edu.agh.model.Budget;
import pl.edu.agh.service.AccountService;
import pl.edu.agh.service.BudgetService;
import pl.edu.agh.service.CategoryService;
import pl.edu.agh.service.TransactionService;
import pl.edu.agh.util.View;

public class MainViewController {

    @Setter
    private CategoryService categoryService;
    @Setter
    private AccountService accountService;
    @Setter
    private TransactionService transactionService;
    @Setter
    private BudgetService budgetService;
    @Setter
    private static MainViewController mvc;

    private Scene scene; // TODO remove?

    @FXML
    private BorderPane borderPane;

    @FXML
    public void backButtonClicked(MouseEvent event) {
        setCenterScene(View.MENU, null);
    }

    @FXML
    public void accountsButtonClicked(MouseEvent mouseEvent) {
        setCenterScene(View.ACCOUNTS, null);
    }

    @FXML
    public void budgetsButtonClicked(MouseEvent mouseEvent) {
        setCenterScene(View.BUDGETS, null);
    }

    public static void routeTo(View view, Object object) {
        mvc.setCenterScene(view, object);
    }

    public void setCenterScene(View view, Object object) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            switch (view) {
                case ACCOUNTS -> {
                    fxmlLoader.setLocation(MainViewController.class.getResource("/view/accountsView.fxml"));
                    Pane pane = fxmlLoader.load();
                    AccountController controller = fxmlLoader.getController();
                    controller.setAccountService(accountService);
                    controller.loadData();
                    borderPane.setCenter(pane);
                    scene.setRoot(pane);
                }
                case ACCOUNT_DETAILS -> {
                    if (object == null) {
                        throw new IllegalArgumentException("Account is required");
                    }

                    Account account = (Account) object;
                    fxmlLoader.setLocation(MainViewController.class.getResource("/view/categoriesView.fxml"));
                    Pane pane = fxmlLoader.load();
                    AccountDetailsController controller = fxmlLoader.getController();
                    controller.setAccount(account);
                    controller.setCategoryService(categoryService);
                    controller.setAccountService(accountService);
                    controller.setTransactionService(transactionService);
                    controller.loadData();
                    borderPane.setCenter(pane);
                    scene.setRoot(pane);
                }
                case MENU -> { // TODO remove
                    fxmlLoader.setLocation(MainViewController.class.getResource("/view/menuView.fxml"));
                    Pane pane = fxmlLoader.load();
                    borderPane.setCenter(pane);
                    scene.setRoot(pane);
                }
                case BUDGETS -> {

                    fxmlLoader.setLocation(MainViewController.class.getResource("/view/budgetsView.fxml"));
                    Pane pane = fxmlLoader.load();
                    BudgetController budgetController = fxmlLoader.getController();
                    budgetController.setBudgetService(budgetService);
                    budgetController.setCategoryService(categoryService);
                    budgetController.refreshData();
                    borderPane.setCenter(pane);
                    scene.setRoot(pane);
                }
                case BUDGET_DETAILS -> {
                    if (object == null) {
                        throw new IllegalArgumentException("Account is required");
                    }

                    fxmlLoader.setLocation(MainViewController.class.getResource("/view/budgetDetailsView.fxml"));
                    Pane pane = fxmlLoader.load();
                    BudgetDetailsController controller = fxmlLoader.getController();
                    controller.setBudget((Budget)object);
                    controller.setBudgetService(budgetService);
                    controller.setCategoryService(categoryService);
                    controller.loadData();
                    borderPane.setCenter(pane);
                    scene.setRoot(pane);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
