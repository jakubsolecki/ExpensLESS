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
import pl.edu.agh.service.TransactionService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class TransactionDialogController {

    @Setter
    private AccountService accountService;
    @Setter
    private TransactionService transactionService;
    @Setter
    private Account account;
    @Setter
    private AccountDetailsController accountDetailsController;

    @FXML
    public TextField nameTextField;

    @FXML
    public TextField priceTextField;

    @FXML
    public TextField dateTextField;

    @FXML
    public TextField descriptionTextField;

    @FXML
    public Button addButton;

    @FXML
    public Button cancelButton;


    public void cancelButtonClicked(ActionEvent event) {
        closeDialog(event);
    }

    public void addButtonClicked(ActionEvent event) {
        try {
            String name = nameTextField.getText();
            double price = Double.parseDouble(priceTextField.getText());
            Optional<Date> date = parseDateFromString(dateTextField.getText());
            String description = descriptionTextField.getText();
            if(!name.isEmpty() && date.isPresent()){
                Transaction transaction =
                        new Transaction(name, price, date.get(), description, account);
                transactionService.saveTransaction(transaction);
                accountService.addTransaction(account, transaction);
            }
        } catch (NumberFormatException e){
            System.out.println("Wrong format");
        } finally {
            closeDialog(event);
        }
    }

    private Optional<Date> parseDateFromString(String text) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        try {
            return Optional.of(simpleDateFormat.parse(text));
        } catch (ParseException e) {
            return Optional.empty();
        }
    }

    public void closeDialog(ActionEvent event){
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
