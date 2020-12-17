package pl.edu.agh.service;

import com.google.inject.Inject;
import lombok.Setter;
import org.hibernate.Session;
import pl.edu.agh.dao.BudgetDao;
import pl.edu.agh.dao.CategoryBudgetDao;
import pl.edu.agh.dao.IBudgetDao;
import pl.edu.agh.dao.ICategoryBudgetDao;
import pl.edu.agh.model.Budget;
import pl.edu.agh.model.Category;
import pl.edu.agh.model.CategoryBudget;
import pl.edu.agh.model.Transaction;
import pl.edu.agh.util.SessionUtil;

import java.math.BigDecimal;
import java.time.Month;
import java.time.Year;
import java.util.List;

public class BudgetService {

    private final ICategoryBudgetDao categoryBudgetDao;

    private final IBudgetDao budgetDao;
    @Setter
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

    public BigDecimal calculateBudgetBalance(Budget budget, Category category){
        BigDecimal balance = BigDecimal.ZERO;
        List<Transaction> transactions = transactionService.findTransactionsByYearMonthCategory(budget.getYear(), budget.getMonth(), category);
        for (Transaction t : transactions) {
            balance = balance.add(t.getPrice());
        }
        return balance.multiply(BigDecimal.valueOf(-1));
    }

    public List<Budget> getBudgetsByYear(int year) {
        return budgetDao.getBudgetsByYear(year);
    }


}
