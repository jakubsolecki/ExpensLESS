package pl.edu.agh;

import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.dao.ITransactionDao;
import pl.edu.agh.dao.TransactionDao;
import pl.edu.agh.model.Account;
import pl.edu.agh.model.Category;
import pl.edu.agh.model.Subcategory;
import pl.edu.agh.model.Transaction;
import pl.edu.agh.util.SessionUtil;

import javax.persistence.Query;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionDaoTest {
    private ITransactionDao transactionDao;
    private boolean clearDBAfterEveryTest = true;

    @BeforeEach
    void setUp() {
        SessionUtil.openSession();
        transactionDao = new TransactionDao();
    }

    @AfterEach
    void tearDown() {
        if (clearDBAfterEveryTest) {
            Session session = SessionUtil.getSession();
            org.hibernate.Transaction tx = session.getTransaction();
            if (!tx.isActive()) {
                tx.begin();
            }
            session.createQuery("DELETE FROM Transactions ").executeUpdate();
            session.createQuery("DELETE FROM Accounts ").executeUpdate();
            tx.commit();
        }
        SessionUtil.closeSession();
    }

    @Test
    void saveTransaction() {
        // given
        Account account = new Account("Moje konto", 100.0);
        Transaction transaction = new Transaction("Warzywa", 100.0, Date.from(Instant.now()), account);

        // when
        transactionDao.saveTransaction(transaction, account);

        // then
        Transaction result = SessionUtil.getSession()
                .createQuery("From Transactions ", Transaction.class).getSingleResult();
        assertEquals(result, transaction);
    }

    @Test
    void addTransactionToAccount(){
        Account account = new Account("Moje konto", 100.0);
        Transaction transaction = new Transaction("Warzywa", 100.0, Date.from(Instant.now()), account);

        // when
        transactionDao.saveTransaction(transaction, account);

        //then
        Account result = (Account) SessionUtil.getSession().
                createQuery("FROM Accounts WHERE id = :id").
                setParameter("id", account.getId()).
                getSingleResult();
        assertTrue(result.getTransactions().contains(transaction));
    }
}