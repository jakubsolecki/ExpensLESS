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
import java.util.stream.Collectors;

public class MockData {
    private final AccountService accountService;
    private final BudgetService budgetService;
    private List<Category> categoryList;
    private List<Subcategory> subcategories;
    private final CategoryService categoryService;
    private final TransactionService transactionService;

    public void init(){
        List<Account> accounts = createMockAccounts();
        createMockCategories();
        createMockTransactions(accounts);
        createMockBudget();
        List<Subcategory> subcategories = categoryService.getAllCategories().stream().flatMap(category -> category.getSubcategories().stream()).collect(Collectors.toList());

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

    public void createMockCategories() {
        categoryList = Arrays.asList(
                new Category("Category 1"),
                new Category("Category 2"),
                new Category("Category 3"),
                new Category("Category 4")
        );

        for (Category category : categoryList) {
            categoryService.createCategory(category);
        }

        subcategories = new ArrayList<>();
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
    }

    public void createMockTransactions(List<Account> accounts){
        List<Subcategory> subcategories = categoryService.getAllCategories().stream().flatMap(category -> category.getSubcategories().stream()).collect(Collectors.toList());
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(Transaction.builder().
                name("Podatek CIT").price(BigDecimal.valueOf(-89.97)).date(LocalDate.now()).description("Zapłacone za mandat").account(accounts.get(0)).subCategory(subcategories.get(0)).build());
        transactions.add(Transaction.builder().
                name("Warzywa").price(BigDecimal.valueOf(-23.97)).date(LocalDate.now()).description("Dla babci").account(accounts.get(0)).subCategory(subcategories.get(1)).build());
        transactions.add(Transaction.builder().
                name("Urodziny").price(BigDecimal.valueOf(100.0)).date(LocalDate.now()).description("U cioci na imieninach").account(accounts.get(0)).subCategory(subcategories.get(2)).build());
        transactions.add(Transaction.builder().
                name("Kwiaty").price(BigDecimal.valueOf(23.0)).date(LocalDate.now()).description("Dla żony").account(accounts.get(1)).subCategory(subcategories.get(3)).build());
        transactions.add(Transaction.builder().
                name("Podatek").price(BigDecimal.valueOf(-200.0)).date(LocalDate.now()).description("No cóż").account(accounts.get(2)).subCategory(subcategories.get(4)).build());
        transactions.add(Transaction.builder().
                name("Przelew").price(BigDecimal.valueOf(10.0)).date(LocalDate.now()).description("Za buty").account(accounts.get(3)).subCategory(subcategories.get(5)).build());

        for (Transaction transaction : transactions){
            accountService.addTransaction(transaction.getAccount(), transaction);
        }
    }

    public void createMockBudget(){
        Budget budget = new Budget();
        budget.setMonth(Month.JANUARY);
        budget.setYear(2021);
        budget.setSubcategoryBudgetList(new ArrayList<>());

        for (Subcategory subcategory : subcategories){
            BigDecimal plannedBudget = new BigDecimal(200);
            if (!plannedBudget.equals(BigDecimal.ZERO)){
                budget.addSubcategoryBudget(new SubcategoryBudget(subcategory, plannedBudget));
            }
        }

        budgetService.createBudget(budget);
    }
}
