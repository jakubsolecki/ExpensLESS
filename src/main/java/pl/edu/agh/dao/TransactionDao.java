package pl.edu.agh.dao;

import org.hibernate.Session;
import pl.edu.agh.model.Account;
import pl.edu.agh.model.Subcategory;
import pl.edu.agh.model.Transaction;
import pl.edu.agh.util.SessionUtil;

public class TransactionDao implements ITransactionDao {
    @Override
    public void saveTransaction(Transaction transaction, Account account) {
        Session session = SessionUtil.getSession();
        org.hibernate.Transaction hibernateTransaction = session.beginTransaction();
        session.save(transaction);
        hibernateTransaction.commit();
    }
}
