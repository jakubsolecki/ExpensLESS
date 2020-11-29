package pl.edu.agh.dao;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.edu.agh.model.Account;
import pl.edu.agh.session.SessionUtil;

import javax.persistence.PersistenceException;
import java.util.List;

public class AccountDao {
    public void saveAccount(Account account) throws PersistenceException {
        Session session = SessionUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(account);
        transaction.commit();
    }

    public List<Account> getAllAccounts() throws PersistenceException {
        Session session = SessionUtil.getSession();
        List<Account> accountList = session.createQuery("FROM Accounts", Account.class).getResultList();
        session.close();
        return accountList;
    }

    public void addTransaction(Account account, Object transaction){
        //TODO It has to change balance of Account!
    }
}
