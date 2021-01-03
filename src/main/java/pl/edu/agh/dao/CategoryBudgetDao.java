package pl.edu.agh.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.edu.agh.model.CategoryBudget;
import pl.edu.agh.util.SessionUtil;

public class CategoryBudgetDao implements ICategoryBudgetDao {

    @Override
    public void saveCategoryBudget(CategoryBudget categoryBudget) {
        Transaction transaction = null;
        SessionUtil.openSession();

        try (Session session = SessionUtil.getSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(categoryBudget);
            transaction.commit();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            throw e;
        }
    }
}
