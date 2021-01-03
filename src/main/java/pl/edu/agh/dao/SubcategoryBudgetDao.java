package pl.edu.agh.dao;

import pl.edu.agh.model.SubcategoryBudget;

public class SubcategoryBudgetDao implements ISubcategoryBudgetDao {

    @Override
    public void saveSubcategoryBudget(SubcategoryBudget subcategoryBudget) {
        CommonDaoSave.save(subcategoryBudget);
    }
}
