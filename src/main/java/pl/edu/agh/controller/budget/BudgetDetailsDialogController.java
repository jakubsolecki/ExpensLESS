package pl.edu.agh.controller.budget;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Setter;
import pl.edu.agh.model.Budget;
import pl.edu.agh.model.Category;
import pl.edu.agh.model.Subcategory;
import pl.edu.agh.model.SubcategoryBudget;
import pl.edu.agh.service.BudgetService;
import pl.edu.agh.service.CategoryService;

import java.math.BigDecimal;
import java.util.List;

public class BudgetDetailsDialogController {

    @Setter
    private BudgetService budgetService;
    @Setter
    private CategoryService categoryService;
    @Setter
    private BudgetDetailsController budgetDetailsController;
    @Setter
    private Budget budget;

    @FXML
    public TextField budgetField;
    @FXML
    public ChoiceBox<Category> categoryChoiceBox;
    @FXML
    public ChoiceBox<Subcategory> subcategoryChoiceBox;

    @FXML
    public void initialize() {
        categoryChoiceBox.setOnAction(event -> subcategoryChoiceBox.
                setItems(FXCollections.observableArrayList(categoryChoiceBox.getValue().getSubcategories())));
    }

    @FXML
    public void handleAddAction(ActionEvent event){
        BigDecimal budgetAmount = new BigDecimal(budgetField.getText());
        Subcategory subcategory = subcategoryChoiceBox.getSelectionModel().getSelectedItem();

        if(subcategory != null){
            SubcategoryBudget subcategoryBudget = new SubcategoryBudget(subcategory, budgetAmount);
            budgetService.createSubcategoryBudget(subcategoryBudget);
            budgetService.addSubcategoryBudget(subcategoryBudget, budget);
            budgetDetailsController.loadData();
            closeDialog(event);
        }
    }

    @FXML
    public void closeDialog(ActionEvent event){
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void loadData() {
        new Thread(() -> {
            List<Category> categories = categoryService.getAllCategories();
            Platform.runLater(() -> categoryChoiceBox.setItems(FXCollections.observableArrayList(categories)));
        }).start();
    }
}
