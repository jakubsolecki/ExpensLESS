package pl.edu.agh.dao;

import pl.edu.agh.model.CategoryBudget;

import java.math.BigDecimal;

public interface ICategoryBudgetDao {

    void saveCategoryBudget(CategoryBudget categoryBudget);

//    BigDecimal getExpenses(CategoryBudget categoryBudget); // TODO: do we need this?
}
