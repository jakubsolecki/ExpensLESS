package pl.edu.agh.dao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pl.edu.agh.model.Account;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

public class AccountDao {
    private final SessionFactory sessionFactory;

    public AccountDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveAccount(Account account) throws PersistenceException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(account);
        transaction.commit();
        session.close();
    }

    public List<Account> getAllAccounts() throws PersistenceException {
        Session session = sessionFactory.openSession();
        List<Account> accountList = session.createQuery("FROM Accounts", Account.class).getResultList();
        session.close();
        return accountList;
    }

    public void addTransaction(Account account, Object transaction){
        //TODO It has to change balance of Account!
    }
}
