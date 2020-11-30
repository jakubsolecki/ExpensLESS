package pl.edu.agh.dao;

import pl.edu.agh.model.Category;

import java.util.List;

public interface ICategoryDao {

    void saveCategory(Category category);

    public List<Category> getAllCategories();
}
