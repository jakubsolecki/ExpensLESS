package pl.edu.agh.service;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import pl.edu.agh.dao.ICategoryDao;
import pl.edu.agh.dao.ISubcategoryDao;
import pl.edu.agh.model.Category;
import pl.edu.agh.model.Subcategory;
import pl.edu.agh.util.SessionUtil;

import java.util.List;

public class CategoryService {

    private final ICategoryDao categoryDao;
    private final ISubcategoryDao subcategoryDao;

    @Inject
    public CategoryService(ICategoryDao categoryDao, ISubcategoryDao subcategoryDao) {
        this.categoryDao = categoryDao;
        this.subcategoryDao = subcategoryDao;
    }

    public void createCategory(Category category) {
        SessionUtil.openSession();
        categoryDao.saveCategory(category);
        SessionUtil.closeSession();
    }

    public void createSubcategory(Subcategory subcategory) {
        SessionUtil.openSession();
        subcategoryDao.saveSubcategory(subcategory);
        SessionUtil.closeSession();
    }

    public List<Category> getAllCategories() {
        SessionUtil.openSession();
        List<Category> categoryList = categoryDao.getAllCategories();
        SessionUtil.closeSession();

        return categoryList;
    }

    public Category getCategoryByName(String name) {
        SessionUtil.openSession();
        Category category = categoryDao.findCategoryByName(name);
        SessionUtil.closeSession();

        return category;
    }


}
