package pl.edu.agh.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Setter;
import pl.edu.agh.model.Account;
import pl.edu.agh.model.Transaction;
import pl.edu.agh.service.AccountService;
import pl.edu.agh.service.CategoryService;
import pl.edu.agh.service.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    //@FXML
    //public ChoiceBox categoryChoiceBox;

    @FXML
    public Button addButton;

    @FXML
    public Button cancelButton;


    @FXML
    public void initialize() {
        //List<Category> categories = categoryService.getAllCategories();
        //categoryChoiceBox.setItems(FXCollections.observableArrayList(categories));
        dateTextField.setText(LocalDate.now().toString());
    }

    public void cancelButtonClicked(ActionEvent event) {
        closeDialog(event);
    }

    public void okButtonClicked(ActionEvent event) {
        try {
            String name = nameTextField.getText();
            BigDecimal price = new BigDecimal(priceTextField.getText());

            Optional<LocalDate> date = parseDateFromString(dateTextField.getText());
            String description = descriptionTextField.getText();
            if(!name.isEmpty() && date.isPresent()){
                Transaction transaction =
                        new Transaction(name, price, date.get(), description, account);
                transactionService.saveTransaction(transaction);
                accountService.addTransaction(account, transaction);
            }
        } catch (Exception e){
            System.out.println("Wrong format");
        } finally {
            closeDialog(event);
        }
    }

    private Optional<LocalDate> parseDateFromString(String text) {
        LocalDate date = LocalDate.parse(text);
        return Optional.of(date);
    }

    public void closeDialog(ActionEvent event){
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
