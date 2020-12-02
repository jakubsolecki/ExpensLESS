package pl.edu.agh.dao;

import pl.edu.agh.model.Account;
import pl.edu.agh.model.Transaction;

public interface ITransactionDao {

    void saveTransaction(Transaction transaction, Account account);

}
