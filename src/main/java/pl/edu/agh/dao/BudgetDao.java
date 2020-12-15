package pl.edu.agh.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.edu.agh.model.Budget;
import pl.edu.agh.model.Category;
import pl.edu.agh.util.SessionUtil;

import java.time.Month;
import java.util.List;

public class BudgetDao implements IBudgetDao {

    @Override
    public void saveBudget(Budget budget) {
        Session session = SessionUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(budget);
        transaction.commit();
    }

    @Override
    public List<Budget> getBudgetsByYear(int year) {
        SessionUtil.openSession();
        Session session = SessionUtil.getSession();
                List<Budget> result = session.createQuery("FROM Budget B where B.year = :year ", Budget.class)
                .setParameter("year", year)
                .getResultList();
        session.close();

        return result;
    }

    @Override
    public Budget getBudgetByMonth(int year, Month month) {
        return null;
    }
}
