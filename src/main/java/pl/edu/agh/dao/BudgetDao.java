package pl.edu.agh.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.edu.agh.model.Budget;
import pl.edu.agh.util.SessionUtil;

public class BudgetDao implements IBudgetDao {

    @Override
    public void saveBudget(Budget budget) {
        Session session = SessionUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(budget);
        transaction.commit();
    }
}
