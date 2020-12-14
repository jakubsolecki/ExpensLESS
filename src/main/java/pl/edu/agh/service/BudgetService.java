package pl.edu.agh.service;

import com.google.inject.Inject;
import pl.edu.agh.dao.BudgetDao;
import pl.edu.agh.dao.CategoryBudgetDao;
import pl.edu.agh.dao.IBudgetDao;
import pl.edu.agh.dao.ICategoryBudgetDao;
import pl.edu.agh.model.Budget;
import pl.edu.agh.model.CategoryBudget;
import pl.edu.agh.model.Transaction;
import pl.edu.agh.util.SessionUtil;

import java.math.BigDecimal;
import java.util.List;

public class BudgetService {

    private final ICategoryBudgetDao categoryBudgetDao;

    private final IBudgetDao budgetDao;
    private TransactionService transactionService;

    @Inject
    public BudgetService(CategoryBudgetDao categoryBudgetDao, BudgetDao budgetDao) {
        this.categoryBudgetDao = categoryBudgetDao;
        this.budgetDao = budgetDao;
    }

    public void createCategoryBudget(CategoryBudget categoryBudget) {
        SessionUtil.openSession();
        categoryBudgetDao.saveCategoryBudget(categoryBudget);
        SessionUtil.closeSession();
    }

    public void createBudget(Budget budget){
        SessionUtil.openSession();
        budgetDao.saveBudget(budget);
        SessionUtil.closeSession();
    }

    public BigDecimal calculateBudgetBalance(CategoryBudget categoryBudget){
        Budget budget = categoryBudget.getBudget();
        BigDecimal balance = categoryBudget.getPlannedBudget();
        List<Transaction> transactions = transactionService.findTransactionsByYearMonthCategory(budget.getYear(), budget.getMonth(), categoryBudget.getCategory());
        for (Transaction t : transactions) {
            balance = balance.add(t.getPrice());
        }
        return balance;
    }

}
