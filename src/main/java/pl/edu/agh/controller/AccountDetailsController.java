package pl.edu.agh.controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.Data;
import lombok.Setter;
import pl.edu.agh.model.Account;
import pl.edu.agh.model.Category;
import pl.edu.agh.model.Subcategory;
import pl.edu.agh.model.Transaction;
import pl.edu.agh.service.AccountService;
import pl.edu.agh.service.CategoryService;
import pl.edu.agh.service.TransactionService;
import pl.edu.agh.util.Router;
import pl.edu.agh.util.View;

import java.util.Date;
import java.util.List;

public class AccountDetailsController {

    @FXML
    public TableColumn<Transaction, String> nameColumn;
    @FXML
    public TableColumn<Transaction, Double> priceColumn;
    @FXML
    public TableColumn<Transaction, Date> dateColumn;
    @FXML
    public TableColumn<Transaction, String> descriptionColumn;
    @FXML
    public TableView<Transaction> transactionsTable;

    @Setter
    private CategoryService categoryService;

    @Setter
    private AccountService accountService;

    @Setter
    private TransactionService transactionService;

    @Setter
    private Account account;

    @FXML
    private TreeView<String> categoryTreeView = new TreeView<>();

    @FXML
    public void initialize() {

        new Thread(() -> {
            List<Category> categoryList = categoryService.getAllCategories();
            List<Transaction> transactions = transactionService.getAllTransactionsOfAccount(account);

            Platform.runLater(() -> {
                TreeItem<String> rootItem = new TreeItem<>("Categories");
                rootItem.setExpanded(true);

                for (Category cat : categoryList) {
                    TreeItem<String> categoryTreeItem = new TreeItem<>(cat.getName());

                    for (Subcategory subcat : cat.getSubcategories()) {
                        if (subcat != null) {
                            categoryTreeItem.getChildren().add(new TreeItem<>(subcat.getName()));
                        }
                    }
                    rootItem.getChildren().add(categoryTreeItem);
                }
                categoryTreeView.setRoot(rootItem);
                categoryTreeView.setShowRoot(false);

                transactionsTable.setItems(FXCollections.observableList(transactions));
                nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
                priceColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getPrice()));
                dateColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDate()));
                descriptionColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescription()));
            });
        }).start();

    }

    @FXML
    public void backButtonClicked(ActionEvent event) {
        Router.routeTo(View.ACCOUNTS);
    }
}
