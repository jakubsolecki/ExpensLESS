package pl.edu.agh.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import lombok.Setter;
import pl.edu.agh.controller.AccountController;
import pl.edu.agh.controller.AccountDetailsController;
import pl.edu.agh.model.Account;
import pl.edu.agh.service.AccountService;
import pl.edu.agh.service.CategoryService;


public class Router {
    @Setter
    private static Scene mainScene;
    @Setter
    private static CategoryService categoryService;
    @Setter
    private static AccountService accountService;

    public static void routeTo(View view, Object object){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            switch (view) {
                case ACCOUNTS -> {
                    fxmlLoader.setLocation(Router.class.getResource("/view/accountsView.fxml"));
                    Pane pane = fxmlLoader.load();
                    AccountController controller = fxmlLoader.getController();
                    controller.setAccountService(accountService);
                    mainScene.setRoot(pane);
                    break;
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
                    mainScene.setRoot(pane);
                    break;
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
