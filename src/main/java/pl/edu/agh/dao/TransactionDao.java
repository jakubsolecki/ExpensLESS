package pl.edu.agh.dao;

import org.hibernate.Session;
import pl.edu.agh.model.Account;
import pl.edu.agh.model.Transaction;
import pl.edu.agh.util.SessionUtil;

import java.util.List;

public class TransactionDao implements ITransactionDao {
    @Override
    public void saveTransaction(Transaction transaction, Account account) {
        Session session = SessionUtil.getSession();
        org.hibernate.Transaction hibernateTransaction = session.beginTransaction();
        session.save(transaction);
        hibernateTransaction.commit();
    }

    @Override
    public List<Transaction> getAllTransactionsOfAccount(Account account) {
        Session session = SessionUtil.getSession();
        List<Transaction> transactionList = session.
                createQuery("FROM Transactions WHERE Accounts .id = :id", Transaction.class).
                setParameter("id", account.getId()).
                getResultList();
        session.close();
        return transactionList;
    }
}
