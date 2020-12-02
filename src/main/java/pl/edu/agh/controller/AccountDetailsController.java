package pl.edu.agh.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import lombok.Setter;
import pl.edu.agh.model.Account;
import pl.edu.agh.model.Category;
import pl.edu.agh.model.Subcategory;
import pl.edu.agh.service.CategoryService;
import pl.edu.agh.util.Router;
import pl.edu.agh.util.View;

import java.util.List;

public class AccountDetailsController {

    @Setter
    private CategoryService categoryService;

    @Setter
    private Account account;

    @FXML
    private TreeView<String> categoryTreeView = new TreeView<>();

    @FXML
    public void initialize() {

        new Thread(() -> {
            List<Category> categoryList = categoryService.getAllCategories();

            Platform.runLater(() -> {
                TreeItem<String> rootItem = new TreeItem<>("Categories");
                rootItem.setExpanded(true);

                for (Category cat : categoryList) {
                    TreeItem<String> categoryTreeItem = new TreeItem<>(cat.getName());

                    for (Subcategory subcat : cat.getSubcategories()) {
                        if (subcat != null) {
                            categoryTreeItem.getChildren().add(new TreeItem<>(subcat.getName()));
                        }
                    }
                    rootItem.getChildren().add(categoryTreeItem);
                }
                categoryTreeView.setRoot(rootItem);
                categoryTreeView.setShowRoot(false);
            });
        }).start();
    }

    @FXML
    public void backButtonClicked(ActionEvent event) {
        Router.routeTo(View.ACCOUNTS);
    }
}
