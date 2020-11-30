package pl.edu.agh.controller;

import com.google.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import lombok.Setter;
import pl.edu.agh.model.Category;
import pl.edu.agh.service.CategoryService;

import java.util.stream.Collectors;

public class CategoryController {

    @Setter
    private CategoryService categoryService;

    @FXML
    private ListView<String> categoriesListView = new ListView<>();

    @FXML
    public void initialize() {

        ObservableList<String> categories = FXCollections.observableList(
                categoryService.getAllCategories().stream()
                .map(Category::getName)
                .collect(Collectors.toList())
        );

        categoriesListView.setItems(categories);

        categoriesListView.setPrefHeight(15);
        categoriesListView.setPrefWidth(150);

//        categoriesListView.setCellFactory(cat -> new ListCell<>() {
//
//        });
    }
}
