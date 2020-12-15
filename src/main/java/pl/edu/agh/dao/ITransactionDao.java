package pl.edu.agh.dao;

import pl.edu.agh.model.Account;
import pl.edu.agh.model.Category;
import pl.edu.agh.model.Transaction;

import java.time.Month;
import java.util.List;

public interface ITransactionDao {

    void saveTransaction(Transaction transaction);
    List<Transaction> getAllTransactionsOfAccount(Account account);
    List<Transaction> findTransactionByYearMonthCategory(Category category, int year, Month month);

}
