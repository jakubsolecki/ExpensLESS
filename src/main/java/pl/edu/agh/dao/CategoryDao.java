package pl.edu.agh.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.edu.agh.model.Category;
import pl.edu.agh.util.SessionUtil;

import java.util.List;

public class CategoryDao implements ICategoryDao {

    @Override
    public void saveCategory(Category category) {
        Transaction transaction = null;

        try {
            Session session = SessionUtil.getSession();
            transaction = session.beginTransaction();
            session.save(category);
            transaction.commit();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            throw e;
        }
    }

    public List<Category> getAllCategories() {
        Transaction transaction = null;
        List<Category> categoryList = null;

        try {
            Session session = SessionUtil.getSession();
            transaction = session.beginTransaction();
            categoryList = session.createQuery("FROM Categories", Category.class).getResultList();

            return categoryList;

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            throw e;
        }
    }
}
