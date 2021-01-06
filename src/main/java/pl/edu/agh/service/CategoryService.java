package pl.edu.agh.service;

import com.google.inject.Inject;
import pl.edu.agh.dao.CategoryDao;
import pl.edu.agh.dao.SubcategoryDao;
import pl.edu.agh.model.Category;
import pl.edu.agh.model.Subcategory;
import pl.edu.agh.util.SessionUtil;

import java.util.List;

public class CategoryService {

    private final CategoryDao categoryDao;
    private final SubcategoryDao subcategoryDao;

    @Inject
    public CategoryService(CategoryDao categoryDao, SubcategoryDao subcategoryDao) {
        this.categoryDao = categoryDao;
        this.subcategoryDao = subcategoryDao;
    }

    public void createCategory(Category category) {
        SessionUtil.openSession();
        categoryDao.save(category);
        SessionUtil.closeSession();
    }

    public void createSubcategory(Subcategory subcategory) {
        SessionUtil.openSession();
        subcategoryDao.save(subcategory);
        SessionUtil.closeSession();
    }

    public List<Category> getAllCategories() {
        SessionUtil.openSession();
        List<Category> categoryList = categoryDao.getAllCategories();
        SessionUtil.closeSession();

        return categoryList;
    }

    public List<Subcategory> getAllSubcategories() {
        SessionUtil.openSession();
        List<Subcategory> categoryList = subcategoryDao.getAllSubcategories();
        SessionUtil.closeSession();

        return categoryList;
    }

    public Category getCategoryByName(String name) {
        SessionUtil.openSession();
        Category category = categoryDao.findCategoryByName(name);
        SessionUtil.closeSession();

        return category;
    }

    public Subcategory getSubcategoryByName(String name) {
        SessionUtil.openSession();
        Subcategory subcategory = subcategoryDao.findSubcategoryByName(name);
        SessionUtil.closeSession();

        return subcategory;
    }

    public List<Subcategory> getSubcategoriesFromCategory(Category category) {
        SessionUtil.openSession();
        List<Subcategory> subcategoryList = subcategoryDao.getSubcategoriesFromCategory(category);
        SessionUtil.closeSession();

        return subcategoryList;
    }


}
