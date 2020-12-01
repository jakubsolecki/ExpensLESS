package pl.edu.agh.controller;

import antlr.actions.cpp.ActionLexerTokenTypes;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import lombok.Setter;
import pl.edu.agh.model.Category;
import pl.edu.agh.service.CategoryService;
import pl.edu.agh.util.Router;

import java.util.List;

public class CategoryController {

    @Setter
    private CategoryService categoryService;

    @FXML
    private TreeView<String> categoryTreeView = new TreeView<>();

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            TreeItem<String> rootItem = new TreeItem<>("Categories");
            rootItem.setExpanded(true);

            List<Category> categoryList = categoryService.getAllCategories();

            categoryList.stream()
                .map(cat -> new TreeItem<>(cat.getName()))
                .forEach(catView -> rootItem.getChildren().add(catView));

            categoryTreeView.setRoot(rootItem);
            categoryTreeView.setShowRoot(false);
        });
    }

    @FXML
    public void backButtonClicked(ActionEvent event) {
        Router.routeTo("Accounts");
    }
}
