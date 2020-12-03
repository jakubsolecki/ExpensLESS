package pl.edu.agh.dao;

import pl.edu.agh.model.Account;
import pl.edu.agh.model.Transaction;

import java.util.List;

public interface ITransactionDao {

    void saveTransaction(Transaction transaction, Account account);
    List<Transaction> getAllTransactionsOfAccount(Account account);

}
