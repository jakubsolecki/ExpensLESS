package pl.edu.agh.dao;

import pl.edu.agh.model.CategoryBudget;

public class CategoryBudgetDao implements ICategoryBudgetDao {

    @Override
    public void saveCategoryBudget(CategoryBudget categoryBudget) {
        CommonDaoSave.save(categoryBudget);
    }
}
