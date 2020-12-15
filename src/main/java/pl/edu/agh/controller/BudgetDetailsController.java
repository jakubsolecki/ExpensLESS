package pl.edu.agh.controller;

import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
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

import java.util.List;

public class BudgetDetailsController {
    public Label nameLabel;
    public TreeView<String> categoryTree;
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

        TreeItem<String> rootItem = new TreeItem<>("Categories");
        rootItem.setExpanded(true);

        for (CategoryBudget cat : categoryBudgetList) {
            TreeItem<String> categoryTreeItem = new TreeItem<>(cat.getCategory().getName() + " " + cat.getPlannedBudget());

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
