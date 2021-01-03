package pl.edu.agh.service;

import com.google.inject.Inject;
import lombok.Setter;
import org.hibernate.Session;
import pl.edu.agh.dao.*;
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

    private final ITransactionDao transactionDao;


    @Inject
    public BudgetService(CategoryBudgetDao categoryBudgetDao, BudgetDao budgetDao, TransactionDao transactionDao) {
        this.categoryBudgetDao = categoryBudgetDao;
        this.budgetDao = budgetDao;
        this.transactionDao = transactionDao;
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
        SessionUtil.openSession();
        List<Transaction> transactions = transactionDao.findTransactionByYearMonthCategory( category, budget.getYear(), budget.getMonth());
        SessionUtil.closeSession();
        for (Transaction t : transactions) {
            balance = balance.add(t.getPrice());
        }
        return balance.multiply(BigDecimal.valueOf(-1));
    }

    public List<Budget> getBudgetsByYear(int year) {
        return budgetDao.getBudgetsByYear(year);
    }


}
