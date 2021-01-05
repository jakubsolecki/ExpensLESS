package pl.edu.agh.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import lombok.Setter;
import pl.edu.agh.controller.*;
import pl.edu.agh.model.Account;
import pl.edu.agh.model.Budget;
import pl.edu.agh.service.AccountService;
import pl.edu.agh.service.BudgetService;
import pl.edu.agh.service.CategoryService;
import pl.edu.agh.service.TransactionService;


// TODO consider removing
public class Router {
    @Setter
    private static MainViewController mainViewController;

    public static void routeTo(View view, Object object){
        try{
            switch (view) {
                case ACCOUNT_DETAILS -> mainViewController.setCenterScene(View.ACCOUNT_DETAILS, object);
                case BUDGET_DETAILS -> mainViewController.setCenterScene(View.BUDGET_DETAILS, object);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void routeTo(View view){
        routeTo(view, null);
    }
}
