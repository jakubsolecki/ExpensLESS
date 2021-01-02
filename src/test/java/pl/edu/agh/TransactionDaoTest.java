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

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        Category category = new Category("Category 1");
        Subcategory subcategory = new Subcategory("Subcategory 1", category);
        Account account = new Account("Moje konto", BigDecimal.valueOf(100.0));
        Transaction transaction = new Transaction("Warzywa", BigDecimal.valueOf(100.0), LocalDate.now(), account);
        // when
        transactionDao.saveTransaction(transaction);
        // then
        Transaction result = SessionUtil.getSession()
                .createQuery("From Transactions where id = :id", Transaction.class)
                .setParameter("id", transaction.getId())
                .getSingleResult();
        assertEquals(result, transaction);
    }
}