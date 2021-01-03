package pl.edu.agh.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import lombok.Setter;
import org.hibernate.Hibernate;
import pl.edu.agh.model.Budget;
import pl.edu.agh.model.Category;
import pl.edu.agh.model.SubcategoryBudget;
import pl.edu.agh.model.Subcategory;
import pl.edu.agh.service.BudgetService;
import pl.edu.agh.service.CategoryService;
import pl.edu.agh.util.Router;
import pl.edu.agh.util.View;

import java.math.BigDecimal;
import java.util.List;

public class BudgetDetailsController {
    @Setter
    private BudgetService budgetService;
    @Setter
    private Budget budget;
    @Setter
    private CategoryService categoryService;

    @FXML
    private Label nameLabel;
    @FXML
    private TreeView<Object> categoryTree;

    @FXML
    public void backButtonClicked(MouseEvent event) {
        Router.routeTo(View.MENU);
    }

    public void loadData() {
        nameLabel.setText(budget.toString());
        new Thread(() -> {
            Hibernate.initialize(budget.getSubcategoryBudgetList());
            List<SubcategoryBudget> subcategoryBudgetList = budget.getSubcategoryBudgetList();
            TreeItem<Object> rootItem = new TreeItem<>("Categories");
            rootItem.setExpanded(true);
            List<Category> categories = categoryService.getAllCategories();

            for (Category cat : categories) {
                TreeItem<Object> categoryTreeItem = new TreeItem<>(cat.getName());
                for (SubcategoryBudget subcat : subcategoryBudgetList) {
                    if (subcat.getSubcategory().getCategory().getId().equals(cat.getId())) {
                        BigDecimal balance = budgetService.calculateBudgetBalance(budget, subcat.getSubcategory());
                        Text text = new Text(balance + " / " + subcat.getPlannedBudget().toString());
                        text.setFill(subcat.getPlannedBudget().subtract(balance).doubleValue() >= 0 ? Color.GREEN : Color.RED);
                        GridPane gridPane = new GridPane();
                        gridPane.add(new Text(subcat.getSubcategory().getName()), 0, 0);
                        gridPane.add(text, 1, 0);
                        gridPane.setHgap(30);

                        categoryTreeItem.getChildren().add(new TreeItem<>(gridPane));
                    }
                }
                rootItem.getChildren().add(categoryTreeItem);
            }

            Platform.runLater(() -> {
                categoryTree.setRoot(rootItem);
                categoryTree.setShowRoot(false);
            });
        }).start();
    }
}
