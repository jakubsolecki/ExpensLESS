package pl.edu.agh.dao;
import org.hibernate.Session;
import pl.edu.agh.model.Account;
import pl.edu.agh.model.Transaction;
import pl.edu.agh.util.SessionUtil;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

public class AccountDao extends Dao {
    public List<Account> getAllAccounts() throws PersistenceException {
        org.hibernate.Transaction tr = null;
        List<Account> accountList = new ArrayList<>();

        try {
            Session session = SessionUtil.getSession();
            tr = session.beginTransaction();
            accountList.addAll(session.createQuery("FROM Accounts", Account.class).getResultList()); // to avoid optional
            tr.commit();

            return accountList;

        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }

            throw e;
        }
    }

    public void addTransaction(Account account, Transaction transaction){
        org.hibernate.Transaction tr = null;

        try {
            Session session = SessionUtil.getSession();
            tr = session.beginTransaction();
            account.addTransaction(transaction);
            session.update(account);
            tr.commit();

        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }

            throw e;
        }
    }
}
