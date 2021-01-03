package pl.edu.agh;

import pl.edu.agh.model.*;
import pl.edu.agh.service.AccountService;
import pl.edu.agh.service.BudgetService;
import pl.edu.agh.service.CategoryService;
import pl.edu.agh.service.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MockData {
    private final AccountService accountService;
    private final BudgetService budgetService;
    private List<Category> categoryList;
    private final CategoryService categoryService;
    private final TransactionService transactionService;

    public void init(){
        List<Account> accounts = createMockAccounts();
        List<Subcategory> subcategories = createMockCategories();
        createMockTransactions(accounts, subcategories);
        createMockBudget();
    }

    public MockData(AccountService accountService, BudgetService budgetService, CategoryService categoryService, TransactionService transactionService) {
        this.accountService = accountService;
        this.budgetService = budgetService;
        this.categoryService = categoryService;
        this.transactionService = transactionService;
    }

    public List<Account> createMockAccounts(){
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account("Moje konto 1", BigDecimal.valueOf(21.38)));
        accounts.add(new Account("Moje konto 2", BigDecimal.valueOf(21.36)));
        accounts.add(new Account("Moje konto 3", BigDecimal.valueOf(21.35)));
        accounts.add(new Account("Moje konto 4", BigDecimal.valueOf(21.21)));

        for (Account account : accounts){
            accountService.createAccount(account);
        }

        return accounts;
    }

    public List<Subcategory> createMockCategories() {
        categoryList = Arrays.asList(
                new Category("Category 1"),
                new Category("Category 2"),
                new Category("Category 3"),
                new Category("Category 4")
        );

        for (Category category : categoryList) {
            categoryService.createCategory(category);
        }

        List<Subcategory> subcategories = new ArrayList<>();
        subcategories.add(new Subcategory("Subcategory a", categoryList.get(0)));
        subcategories.add(new Subcategory("Subcategory b", categoryList.get(0)));
        subcategories.add(new Subcategory("Subcategory c", categoryList.get(0)));
        subcategories.add(new Subcategory("Subcategory e", categoryList.get(1)));
        subcategories.add(new Subcategory("Subcategory f", categoryList.get(1)));
        subcategories.add(new Subcategory("Subcategory g", categoryList.get(1)));
        subcategories.add(new Subcategory("Subcategory h", categoryList.get(2)));
        subcategories.add(new Subcategory("Subcategory i", categoryList.get(3)));

        for (Subcategory subcategory : subcategories){
            categoryService.createSubcategory(subcategory);
        }

        return subcategories;
    }

    public void createMockTransactions(List<Account> accounts, List<Subcategory> subcategories){
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("Podatek CIT", BigDecimal.valueOf(-89.97),
                LocalDate.of(19,10, 20), "Zapłacone za mandat", accounts.get(0), subcategories.get(0)));
        transactions.add(new Transaction("Warzywa", BigDecimal.valueOf(-24.20),
                LocalDate.now(), "Dla babci", accounts.get(0), subcategories.get(0)));
        transactions.add(new Transaction("Urodziny", BigDecimal.valueOf(100.0),
                LocalDate.now(), "U cioci na imieninach", accounts.get(1), subcategories.get(2)));
        transactions.add(new Transaction("Kwiaty", BigDecimal.valueOf(-42.21),
                LocalDate.now(), "Dla żony", accounts.get(1), subcategories.get(1)));
        transactions.add(new Transaction("Podatek", BigDecimal.valueOf(-59.90),
                LocalDate.now(), "Znowu", accounts.get(2), subcategories.get(6)));
        transactions.add(new Transaction("Przelew", BigDecimal.valueOf(200.0),
                LocalDate.now(), accounts.get(3), subcategories.get(5)));

        for (Transaction transaction : transactions){
            accountService.addTransaction(transaction.getAccount(), transaction);
        }
    }

    public void createMockBudget(){
        Budget budget = new Budget();
        budget.setMonth(Month.JANUARY);
        budget.setYear(2021);
        budget.setCategoryBudgetList(new ArrayList<>());
        for (Category category : categoryList){
            BigDecimal plannedBudget = new BigDecimal(200);
            if (!plannedBudget.equals(BigDecimal.ZERO)){
                budget.addCategoryBudget(new CategoryBudget(category, plannedBudget));
            }
        }

        budgetService.createBudget(budget);
    }
}
