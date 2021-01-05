package pl.edu.agh.service;

import com.google.inject.Inject;
import pl.edu.agh.dao.TransactionDao;
import pl.edu.agh.model.Account;
import pl.edu.agh.model.Transaction;
import pl.edu.agh.util.SessionUtil;

import java.util.List;

public class TransactionService {
    private final TransactionDao transactionDao;

    @Inject
    public TransactionService(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    public void saveTransaction(Transaction transaction) {
        SessionUtil.openSession();
        transactionDao.save(transaction);
        SessionUtil.closeSession();
    }

    public List<Transaction> getAllTransactionsOfAccount(Account account) {
        SessionUtil.openSession();
        List<Transaction> transactionList = transactionDao.getAllTransactionsOfAccount(account);
        SessionUtil.closeSession();

        return transactionList;
    }
}
