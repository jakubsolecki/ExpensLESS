package pl.edu.agh.controller;

import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import lombok.Setter;
import org.hibernate.Hibernate;
import pl.edu.agh.model.Budget;
import pl.edu.agh.model.Category;
import pl.edu.agh.model.CategoryBudget;
import pl.edu.agh.model.Subcategory;
import pl.edu.agh.service.BudgetService;
import pl.edu.agh.service.CategoryService;
import pl.edu.agh.util.Router;
import pl.edu.agh.util.View;

import java.math.BigDecimal;
import java.util.List;

public class BudgetDetailsController {
    public Label nameLabel;
    public TreeView<Object> categoryTree;
    @Setter
    private Budget budget;

    @Setter
    private BudgetService budgetService;

    public void backButtonClicked(MouseEvent event) {
        Router.routeTo(View.MENU);
    }

    public void load(){
        nameLabel.setText(budget.toString());
        Hibernate.initialize(budget.getCategoryBudgetList());
        List<CategoryBudget> categoryBudgetList = budget.getCategoryBudgetList();

        TreeItem<Object> rootItem = new TreeItem<>("Categories");
        rootItem.setExpanded(true);

        for (CategoryBudget cat : categoryBudgetList) {
            BigDecimal balance = budgetService.calculateBudgetBalance(budget, cat.getCategory());
            Text text = new Text(balance+" / "+cat.getPlannedBudget().toString());
            text.setFill(cat.getPlannedBudget().subtract(balance).doubleValue() >= 0 ? Color.GREEN : Color.RED);
            GridPane gridPane = new GridPane();
            gridPane.add(new Text(cat.getCategory().getName()), 0, 0);
            gridPane.add(text, 1, 0);

            gridPane.setHgap(30);


            TreeItem<Object> categoryTreeItem = new TreeItem<>(gridPane);

            for (Subcategory subcategory : cat.getCategory().getSubcategories()) {
                if (subcategory != null) {
                    categoryTreeItem.getChildren().add(new TreeItem<>(subcategory.getName()));
                }
            }
            rootItem.getChildren().add(categoryTreeItem);
        }

        categoryTree.setRoot(rootItem);
        categoryTree.setShowRoot(false);
    }
}
