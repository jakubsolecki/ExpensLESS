package pl.edu.agh;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;
import pl.edu.agh.dao.AccountDao;
import pl.edu.agh.model.Account;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountDaoTest {
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    @Test
    public void saveAccountTest(){
        //Given
        AccountDao accountDao = new AccountDao(sessionFactory);
        Account account = new Account("Moje konto", 100.0);

        //when
        accountDao.saveAccount(account);

        //then
        Session session = sessionFactory.openSession();
        Account result = session.createQuery("FROM Accounts", Account.class).getSingleResult();
        session.close();
        assertEquals(result, account);
    }

    @Test
    public void getAllAccountsTest(){
        //Given
        AccountDao accountDao = new AccountDao(sessionFactory);
        Account account1 = new Account("Moje konto1", 100.0);
        Account account2 = new Account("Moje konto2", 100.0);

        //when
        accountDao.saveAccount(account1);
        accountDao.saveAccount(account2);

        //then
        List<Account> result = accountDao.getAllAccounts();
        assertTrue(result.contains(account1) && result.contains(account2));
    }
}
