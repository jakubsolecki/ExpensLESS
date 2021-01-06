package pl.edu.agh.controller.budget;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Setter;
import pl.edu.agh.model.Budget;
import pl.edu.agh.model.Category;
import pl.edu.agh.service.BudgetService;
import pl.edu.agh.service.CategoryService;

import java.time.Month;
import java.util.*;

public class BudgetDialogController {
    private final Map<Category, TextField> textFieldMap = new HashMap<>();

    @Setter
    private CategoryService categoryService;
    @Setter
    private BudgetService budgetService;
    @Setter
    private BudgetController budgetController;
    @Setter
    private int year;

    @FXML
    public ChoiceBox<Month> monthBox;
    @FXML
    public VBox mainBox;

    @FXML
    public void cancelButtonClicked(ActionEvent event) {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void okButtonClicked(ActionEvent event) {
        try {
            Budget budget = new Budget();
            budget.setMonth(monthBox.getValue());
            budget.setYear(year);
            budget.setSubcategoryBudgetList(new ArrayList<>());
//            for (Category category : textFieldMap.keySet()){
//                if (textFieldMap.get(category).getText().equals("")){
//                    continue;
//                }
//                BigDecimal plannedBudget = new BigDecimal(textFieldMap.get(category).getText());
//                if (!plannedBudget.equals(BigDecimal.ZERO)){
//                    budget.addSubcategoryBudget(new SubcategoryBudget(category, plannedBudget));
//                }
//            }
            budgetService.createBudget(budget);

        } catch (NumberFormatException e){
            System.out.println("Wrong format!");
            return;
        }
        budgetController.refreshData();

        cancelButtonClicked(event);
    }

    @FXML
    public void initialize(){
        monthBox.setItems(FXCollections.observableArrayList(Month.values()));
    }

    public void loadData(){
//        for (Category category : categoryService.getAllCategories()){
//            TextField textField = new TextField();
//            textField.setPromptText("Budżet na " + category.getName() );
////            textFieldMap.put(category, textField);
//            mainBox.getChildren().add(textField);
//        }
    }


}
