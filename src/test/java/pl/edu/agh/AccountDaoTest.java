package pl.edu.agh;

import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.dao.AccountDao;
import pl.edu.agh.dao.IAccountDao;
import pl.edu.agh.model.Account;
import pl.edu.agh.session.SessionUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountDaoTest {
    @BeforeEach
    public void before() {
        SessionUtil.openSession();
    }

    @AfterEach
    public void after() {
        SessionUtil.closeSession();
    }

    @Test
    public void saveAccountTest(){
        //Given
        IAccountDao accountDao = new AccountDao();
        Account account = new Account("Moje konto", 100.0);

        //when
        accountDao.saveAccount(account);

        //then
        Account result = SessionUtil.getSession()
                .createQuery("FROM Accounts", Account.class).getSingleResult();
        assertEquals(result, account);
    }

    @Test
    public void getAllAccountsTest(){
        //Given
        IAccountDao accountDao = new AccountDao();
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
