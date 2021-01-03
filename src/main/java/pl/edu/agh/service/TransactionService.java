package pl.edu.agh.service;

import com.google.inject.Inject;
import pl.edu.agh.dao.ITransactionDao;
import pl.edu.agh.model.Account;
import pl.edu.agh.model.Transaction;
import pl.edu.agh.util.SessionUtil;

import java.util.List;

public class TransactionService {
    private final ITransactionDao transactionDao;

    @Inject
    public TransactionService(ITransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    public void saveTransaction(Transaction transaction) {
        SessionUtil.openSession();
        transactionDao.saveTransaction(transaction);
        SessionUtil.closeSession();
    }

    public List<Transaction> getAllTransactionsOfAccount(Account account) {
        SessionUtil.openSession();
        List<Transaction> transactionList = transactionDao.getAllTransactionsOfAccount(account);
        SessionUtil.closeSession();

        return transactionList;
    }
}
