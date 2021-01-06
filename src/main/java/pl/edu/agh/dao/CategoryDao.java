package pl.edu.agh.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.edu.agh.model.Category;
import pl.edu.agh.model.Subcategory;
import pl.edu.agh.util.SessionUtil;

import javax.inject.Inject;
import java.util.List;

public class CategoryDao extends Dao {
    public List<Category> getAllCategories() {
        Transaction transaction = null;

        try {
            Session session = SessionUtil.getSession();
            transaction = session.beginTransaction();

            return session.createQuery("FROM Categories", Category.class).getResultList();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            throw e;
        }
    }

    public Category findCategoryByName(String name) {
        Transaction transaction = null;

        try {
            Session session = SessionUtil.getSession();
            transaction = session.beginTransaction();

            Category res = (Category) session.createQuery("FROM Categories where name = :name")
                    .setParameter("name", name)
                    .getSingleResult();
            transaction.commit();

            return res;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            throw e;
        }
    }

    public void deleteCategory(Category category){
        Transaction transaction = null;

        try {
            Session session = SessionUtil.getSession();


            transaction = session.beginTransaction();
            for (Subcategory subcategory : category.getSubcategories()){
                String sql = String.format("UPDATE Transactions SET subCategory_id = null WHERE subCategory_id = %d", subcategory.getId());
                session.createNativeQuery(sql).executeUpdate();
            }

            session.remove(category);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            throw e;
        }
    }
}
