package pl.edu.agh.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.edu.agh.model.Budget;
import pl.edu.agh.util.SessionUtil;

import java.time.Month;
import java.util.List;

public class BudgetDao implements IBudgetDao {

    @Override
    public void saveBudget(Budget budget) {
        CommonDaoSave.save(budget);
    }

    @Override
    public List<Budget> getBudgetsByYear(int year) {
        Transaction transaction = null;

        try {
            Session session = SessionUtil.getSession();
            transaction = session.beginTransaction();
             List <Budget> res = session.createQuery("FROM Budget B where B.year = :year ", Budget.class)
                    .setParameter("year", year)
                    .getResultList();
            transaction.commit();

             return res;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            throw e;
        }
    }

    @Override
    public Budget getBudgetByMonth(int year, Month month) {
        return null; // FIXME bruh
    }
}
