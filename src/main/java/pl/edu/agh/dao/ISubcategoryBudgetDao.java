package pl.edu.agh.dao;

import pl.edu.agh.model.SubcategoryBudget;

public interface ISubcategoryBudgetDao {

    void saveSubcategoryBudget(SubcategoryBudget subcategoryBudget);

//    BigDecimal getExpenses(CategoryBudget categoryBudget); // TODO: do we need this?
}
