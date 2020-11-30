package pl.edu.agh.dao;

import pl.edu.agh.model.Subcategory;

import java.util.List;

public interface ISubcategoryDao {

    void saveSubcategory(Subcategory subcategory);

    List<Subcategory> getAllSubcategories();

}
