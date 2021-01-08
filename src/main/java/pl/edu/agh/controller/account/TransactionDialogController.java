package pl.edu.agh.controller.account;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Setter;
import pl.edu.agh.model.Account;
import pl.edu.agh.model.Category;
import pl.edu.agh.model.Subcategory;
import pl.edu.agh.model.Transaction;
import pl.edu.agh.service.AccountService;
import pl.edu.agh.service.CategoryService;
import pl.edu.agh.service.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class TransactionDialogController {
    @Setter
    private AccountService accountService;
    @Setter
    private TransactionService transactionService;
    @Setter
    private CategoryService categoryService;
    @Setter
    private Account account;

    @FXML
    public TextField nameTextField;
    @FXML
    public TextField priceTextField;
    @FXML
    public TextField dateTextField;
    @FXML
    public TextField descriptionTextField;
    @FXML
    public ChoiceBox<Category> categoryChoiceBox;
    @FXML
    public ChoiceBox<Subcategory> subcategoryChoiceBox;
    @FXML
    public void initialize() {
        dateTextField.setText(LocalDate.now().toString());
        categoryChoiceBox.setOnAction(event -> subcategoryChoiceBox.
                setItems(FXCollections.observableArrayList(categoryChoiceBox.getValue().getSubcategories())));
    }

    @FXML
    public void okButtonClicked(ActionEvent event) {
        try {
            String name = nameTextField.getText();
            BigDecimal price = new BigDecimal(priceTextField.getText());

            Optional<LocalDate> date = parseDateFromString(dateTextField.getText());
            String description = descriptionTextField.getText();
            Subcategory subcategory = subcategoryChoiceBox.getSelectionModel().getSelectedItem();
            if(!name.isEmpty() && date.isPresent() && subcategory != null){
                Transaction transaction = Transaction.builder().
                        name(name).price(price).date(date.get()).
                        description(description).account(account).subCategory(subcategory).build();
                transactionService.saveTransaction(transaction);
                accountService.addTransaction(account, transaction);
                closeDialog(event);
            }
        } catch (Exception e){
            System.out.println("Wrong format");
        }
    }

    @FXML
    public void closeDialog(ActionEvent event){
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void loadData(){
        new Thread(() -> {
            List<Category> categories = categoryService.getAllCategories();
            Platform.runLater(() -> categoryChoiceBox.setItems(FXCollections.observableArrayList(categories)));
        }).start();
    }

    private Optional<LocalDate> parseDateFromString(String text) {
        LocalDate date = LocalDate.parse(text);
        return Optional.of(date);
    }
}
