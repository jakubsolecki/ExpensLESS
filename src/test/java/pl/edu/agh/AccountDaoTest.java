package pl.edu.agh;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.dao.AccountDao;
import pl.edu.agh.model.Account;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountDaoTest {
    private static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    @Test
    public void saveAccountTest(){
        //Given
        AccountDao accountDao = new AccountDao(sessionFactory);
        Account account = new Account("Moje konto", 100.0);

        //when
        accountDao.saveAccount(account);

        //then
        Account result = sessionFactory.openSession().createQuery("FROM Accounts", Account.class).getSingleResult();
        System.out.println(account);
        System.out.println(result);
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
        int size = accountDao.getAllAccounts().size();
        assertEquals(2, size);
    }
}
