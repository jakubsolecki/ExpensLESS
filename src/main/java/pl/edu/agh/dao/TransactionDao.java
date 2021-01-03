package pl.edu.agh.dao;

import lombok.SneakyThrows;
import org.hibernate.Session;
import pl.edu.agh.model.Account;
import pl.edu.agh.model.Category;
import pl.edu.agh.model.Transaction;
import pl.edu.agh.util.SessionUtil;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TransactionDao implements ITransactionDao {

    //TODO JAKUB ZRÓB REFACTOR :((((((
    @Override
    public void saveTransaction(Transaction transaction) {
        Session session = SessionUtil.getSession();
        org.hibernate.Transaction hibernateTransaction = session.beginTransaction();
        session.saveOrUpdate(transaction);
        hibernateTransaction.commit();
    }

    @Override
    public List<Transaction> getAllTransactionsOfAccount(Account account) {
        Session session = SessionUtil.getSession();
        List<Transaction> transactionList = session.
                createQuery("FROM Transactions WHERE account.id = :id", Transaction.class).
                setParameter("id", account.getId()).
                getResultList();
        session.close();
        return transactionList;
    }

    @Override
    public List<Transaction> findTransactionByYearMonthCategory(Category category, int year, Month month){
        Session session = SessionUtil.getSession();

        String pattern = "dd-MM-yyyy";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);

        LocalDate startDate = LocalDate.parse("01-" + (month.getValue() < 10 ? "0" + month.getValue() : month.getValue()) + "-" + year, dateTimeFormatter);
        if (month == Month.DECEMBER){
            year++;
        }
        LocalDate endDate = LocalDate.parse("01-"+ (month.plus(1).getValue() < 10 ? "0" + month.plus(1).getValue() : month.plus(1).getValue()) + "-" + year, dateTimeFormatter);

        List<Transaction> transactions = session
                .createQuery("SELECT T FROM Transactions T inner join T.subCategory sc where sc.category = :category and T.date >= :start and T.date <= :end ", Transaction.class)
                .setParameter("category", category)
                .setParameter("start", startDate)
                .setParameter("end", endDate)
                .getResultList();
        return transactions;
    }
}
