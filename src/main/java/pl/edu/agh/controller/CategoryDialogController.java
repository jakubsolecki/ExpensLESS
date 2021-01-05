package pl.edu.agh.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Setter;
import pl.edu.agh.model.Account;
import pl.edu.agh.model.Category;
import pl.edu.agh.service.CategoryService;

import java.math.BigDecimal;

public class CategoryDialogController {
    @FXML
    public TextField nameTextField;

    @Setter
    private CategoryService categoryService;

    @Setter
    private AccountDetailsController accountDetailsController;

    @FXML
    public void handleCancelAction(ActionEvent event) {
        closeDialog(event);
    }

    @FXML
    public void handleAddAction(ActionEvent event) {
        Category category;
        try {
            category = new Category(nameTextField.getText());
        } catch (NumberFormatException e){
            System.out.println("Wrong format!");
            return;
        }
        categoryService.createCategory(category);
        accountDetailsController.refresh();
        closeDialog(event);
    }

    private void closeDialog(ActionEvent event){
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
