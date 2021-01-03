package pl.edu.agh.service;

import com.google.inject.Inject;
import pl.edu.agh.dao.*;
import pl.edu.agh.model.*;
import pl.edu.agh.util.SessionUtil;

import java.math.BigDecimal;
import java.util.List;

public class BudgetService {

    private final ISubcategoryBudgetDao categoryBudgetDao;

    private final IBudgetDao budgetDao;

    private final ITransactionDao transactionDao;


    @Inject
    public BudgetService(SubcategoryBudgetDao subcategoryBudgetDao, BudgetDao budgetDao, TransactionDao transactionDao) {
        this.categoryBudgetDao = subcategoryBudgetDao;
        this.budgetDao = budgetDao;
        this.transactionDao = transactionDao;
    }

    public void createCategoryBudget(SubcategoryBudget subcategoryBudget) {
        SessionUtil.openSession();
        categoryBudgetDao.saveSubcategoryBudget(subcategoryBudget);
        SessionUtil.closeSession();
    }

    public void createBudget(Budget budget){
        SessionUtil.openSession();
        budgetDao.saveBudget(budget);
        SessionUtil.closeSession();
    }

    public BigDecimal calculateBudgetBalance(Budget budget, Subcategory subcategory){
        BigDecimal balance = BigDecimal.ZERO;
        SessionUtil.openSession();
        List<Transaction> transactions = transactionDao.findTransactionByYearMonthSubcategory( subcategory, budget.getYear(), budget.getMonth());
        SessionUtil.closeSession();
        for (Transaction t : transactions) {
            balance = balance.add(t.getPrice());
        }
        return balance.multiply(BigDecimal.valueOf(-1));
    }

    public List<Budget> getBudgetsByYear(int year) {
        SessionUtil.openSession();
        List<Budget> budgetList = budgetDao.getBudgetsByYear(year);
        SessionUtil.closeSession();

        return budgetList;
    }


}
