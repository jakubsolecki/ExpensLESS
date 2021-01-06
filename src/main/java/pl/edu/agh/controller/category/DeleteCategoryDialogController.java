package pl.edu.agh.controller.category;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Setter;
import pl.edu.agh.controller.account.AccountDetailsController;
import pl.edu.agh.model.Category;
import pl.edu.agh.model.Subcategory;
import pl.edu.agh.service.CategoryService;

import java.util.List;

public class DeleteCategoryDialogController {
    @FXML
    public ChoiceBox<Category> categoryChoiceBox;
    @FXML
    public ChoiceBox<Subcategory> subcategoryChoiceBox;
    @Setter
    private CategoryService categoryService;
    @Setter
    private AccountDetailsController accountDetailsController;

    @FXML
    public void initialize() {
        categoryChoiceBox.setOnAction(event -> subcategoryChoiceBox.
                setItems(FXCollections.observableArrayList(categoryChoiceBox.getValue().getSubcategories())));
    }

    public void handleCancelAction(ActionEvent event) {
        closeDialog(event);
    }

    public void loadData() {
        new Thread(() -> {
            List<Category> categories = categoryService.getAllCategories();
            Platform.runLater(() -> categoryChoiceBox.setItems(FXCollections.observableArrayList(categories)));
        }).start();
    }

    public void handleDeleteAction(ActionEvent event) {

        try {
            Category category = categoryChoiceBox.getValue();
            Subcategory subcategory = subcategoryChoiceBox.getValue();
            if (subcategory != null){
                categoryService.deleteSubcategory(subcategory);
            } else {
                categoryService.deleteCategory(category);
            }


        } catch (NumberFormatException | NullPointerException e ){
            System.out.println("Wrong format!");
            return;
        }
        accountDetailsController.refresh();
        closeDialog(event);
    }

    public void closeDialog(ActionEvent event){
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }




}
