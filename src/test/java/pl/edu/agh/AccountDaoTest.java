package pl.edu.agh;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.dao.AccountDao;
import pl.edu.agh.model.Account;
import pl.edu.agh.model.Transaction;
import pl.edu.agh.util.SessionUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
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
        AccountDao accountDao = new AccountDao();
        Account account = new Account("Moje konto", BigDecimal.valueOf(100.0));
        //when
        accountDao.save(account);

        //then
        Account result = SessionUtil.getSession()
                .createQuery("FROM Accounts", Account.class).getSingleResult();
        assertEquals(result, account);
    }

    @Test
    public void getAllAccountsTest(){
        //Given
        AccountDao accountDao = new AccountDao();
        Account account1 = new Account("Moje konto1", BigDecimal.valueOf(100.0));
        Account account2 = new Account("Moje konto2", BigDecimal.valueOf(100.0));

        //when
        accountDao.save(account1);
        accountDao.save(account2);

        //then
        List<Account> result = accountDao.getAllAccounts();
        assertTrue(result.contains(account1) && result.contains(account2));
    }

    @Test
    public void addTransactionTest(){
        AccountDao accountDao = new AccountDao();
        Account account = new Account("Moje konto", BigDecimal.valueOf(100.0));
        Transaction transaction = Transaction.builder().name("Warzywa").price(BigDecimal.valueOf(-69.0)).date(LocalDate.now()).account(account).build();
        // when
        accountDao.save(account);
        accountDao.addTransaction(account, transaction);

        //then
        Account result = (Account) SessionUtil.getSession().
                createQuery("FROM Accounts WHERE id = :id").
                setParameter("id", account.getId()).
                getSingleResult();
        assertTrue(result.getTransactions().contains(transaction));
    }
}
