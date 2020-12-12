package pl.edu.agh.dao;
import org.hibernate.Session;
import pl.edu.agh.model.Account;
import pl.edu.agh.model.Transaction;
import pl.edu.agh.util.SessionUtil;

import javax.persistence.PersistenceException;
import java.util.List;

public class AccountDao implements IAccountDao {
    @Override
    public void saveAccount(Account account) throws PersistenceException {
        Session session = SessionUtil.getSession();
        org.hibernate.Transaction transaction = session.beginTransaction();
        session.save(account);
        transaction.commit();
    }

    @Override
    public List<Account> getAllAccounts() throws PersistenceException {
        Session session = SessionUtil.getSession();
        List<Account> accountList = session.createQuery("FROM Accounts", Account.class).getResultList();
        session.close();
        return accountList;
    }

    @Override
    public void addTransaction(Account account, Transaction transaction){
        Session session = SessionUtil.getSession();
        org.hibernate.Transaction hibernateTransaction = session.beginTransaction();
        account.addTransaction(transaction);
        session.update(account);
        hibernateTransaction.commit();
    }
}
