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

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class TransactionDialogController {
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    @Setter
    private AccountService accountService;
    @Setter
    private TransactionService transactionService;
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
    public Button addButton;

    @FXML
    public Button cancelButton;


    @FXML
    public void initialize() {
        Date date = new Date(System.currentTimeMillis());
        dateTextField.setText(simpleDateFormat.format(date));
    }

    public void cancelButtonClicked(ActionEvent event) {
        closeDialog(event);
    }

    public void okButtonClicked(ActionEvent event) {
        try {
            String name = nameTextField.getText();
            BigDecimal price = new BigDecimal(priceTextField.getText());

            Optional<Date> date = parseDateFromString(dateTextField.getText());
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

    private Optional<Date> parseDateFromString(String text) {
        try {
            Date date = simpleDateFormat.parse(text);
            return Optional.of(date);
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
