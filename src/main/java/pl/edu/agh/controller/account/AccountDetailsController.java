package pl.edu.agh.controller.account;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Setter;
import pl.edu.agh.controller.*;
import pl.edu.agh.controller.category.CategoryDialogController;
import pl.edu.agh.controller.category.DeleteCategoryDialogController;
import pl.edu.agh.controller.category.EditCategoryDialogController;
import pl.edu.agh.controller.category.SubcategoryDialogController;
import pl.edu.agh.model.Account;
import pl.edu.agh.model.Category;
import pl.edu.agh.model.Subcategory;
import pl.edu.agh.model.Transaction;
import pl.edu.agh.service.AccountService;
import pl.edu.agh.service.CategoryService;
import pl.edu.agh.service.TransactionService;

import java.io.IOException;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;

// TODO This class is too long!
public class AccountDetailsController {
    @Setter
    private CategoryService categoryService;

    @Setter
    private AccountService accountService;

    @Setter
    private TransactionService transactionService;

    @Setter
    private List<Transaction> transactions;

    @Setter
    private Account account;

    @FXML
    private Label name;
    @FXML
    private Label balance;
    @FXML
    private TableColumn<Transaction, String> nameColumn;
    @FXML
    private TableColumn<Transaction, Double> priceColumn;
    @FXML
    private TableColumn<Transaction, String> dateColumn;
    @FXML
    private TableColumn<Transaction, String> descriptionColumn;
    @FXML
    private TableColumn<Transaction, String> categoryColumn;
    @FXML
    private TableView<Transaction> transactionsTable;
    @FXML
    private TreeView<String> categoryTreeView = new TreeView<>();

    @FXML
    public void addButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/transactionDialog.fxml"));
        Pane page = loader.load();

        TransactionDialogController controller = loader.getController();
        controller.setAccount(account);
        controller.setAccountService(accountService);
        controller.setTransactionService(transactionService);
        controller.setCategoryService(categoryService);
        controller.loadData();

        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.showAndWait();
        refresh();
    }

    public void loadData(){
        new Thread(() -> {
            List<Category> categoryList = categoryService.getAllCategories();
            transactions = transactionService.getAllTransactionsOfAccount(account);
            transactions.sort(Comparator.comparing(Transaction::getDate, Comparator.reverseOrder()));

            Platform.runLater(() -> {
                refreshCategoryTree(categoryList);

                setTableView(transactions);
                balance.setText(account.getBalance() + " PLN");
                balance.setTextFill(account.getBalance().doubleValue() >= 0 ? Color.GREEN : Color.RED);
                name.setText(account.getName());
            });
        }).start();
    }

    private void setTableView(List<Transaction> transactions) {
        transactionsTable.setItems(FXCollections.observableList(transactions));
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        priceColumn.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getPrice().setScale(2, RoundingMode.DOWN).toString()));
        dateColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDate().toString()));
        descriptionColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescription()));
        categoryColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSubCategory() != null ? data.getValue().getSubCategory().getName() : ""));

    }

    public void refresh(){
        new Thread(() -> {
            transactions = transactionService.getAllTransactionsOfAccount(account);
            transactions.sort(Comparator.comparing(Transaction::getDate, Comparator.reverseOrder()));
            List<Category> categoryList = categoryService.getAllCategories();
            Platform.runLater(() -> {
                refreshCategoryTree(categoryList);
                transactionsTable.setItems(FXCollections.observableList(transactions));
                balance.setText(account.getBalance() + " PLN");
                balance.setTextFill(account.getBalance().doubleValue() >= 0 ? Color.GREEN : Color.RED);
            });
        }).start();
    }

    public void addSubcategory(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/subcategoryDialog.fxml"));
        Pane page = loader.load();

        SubcategoryDialogController controller = loader.getController();
        controller.setCategoryService(categoryService);
        controller.setAccountDetailsController(this);
        controller.loadData();
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.showAndWait();
        refresh();
    }

    public void addCategory(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/categoryDialog.fxml"));
        Pane page = loader.load();

        CategoryDialogController controller = loader.getController();
        controller.setCategoryService(categoryService);
        controller.setAccountDetailsController(this);
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.showAndWait();
        refresh();
    }

    private void refreshCategoryTree(List<Category> categoryList){
        categoryTreeView.setRoot(null);
        TreeItem<String> rootItem = new TreeItem<>("Categories");
        rootItem.setExpanded(true);

        for (Category cat : categoryList) {
            TreeItem<String> categoryTreeItem = new TreeItem<>(cat.getName());

            for (Subcategory subcategory : cat.getSubcategories()) {
                if (subcategory != null) {
                    categoryTreeItem.getChildren().add(new TreeItem<>(subcategory.getName()));
                }
            }
            rootItem.getChildren().add(categoryTreeItem);
        }
        categoryTreeView.setRoot(rootItem);
        categoryTreeView.setShowRoot(false);
    }

    public void editCategory(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/editCategoryDialog.fxml"));
        Pane page = loader.load();

        EditCategoryDialogController controller = loader.getController();
        controller.setCategoryService(categoryService);
        controller.setAccountDetailsController(this);
        controller.loadData();
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.showAndWait();
        refresh();
    }

    public void deleteCategory(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/deleteCategoryDialog.fxml"));
        Pane page = loader.load();

        DeleteCategoryDialogController controller = loader.getController();
        controller.setCategoryService(categoryService);
        controller.setAccountDetailsController(this);
        controller.loadData();
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.showAndWait();
        refresh();
    }
}


