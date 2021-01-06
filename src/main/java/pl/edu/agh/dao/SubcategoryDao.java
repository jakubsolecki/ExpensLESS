package pl.edu.agh.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.edu.agh.model.Category;
import pl.edu.agh.model.Subcategory;
import pl.edu.agh.util.SessionUtil;

import java.util.List;

public class SubcategoryDao extends Dao {

    public List<Subcategory> getAllSubcategories() {
        Transaction transaction = null;

        try {
            Session session = SessionUtil.getSession();
            transaction = session.beginTransaction();
            List<Subcategory> res = session.createQuery("FROM Subcategories", Subcategory.class).getResultList();
            transaction.commit();

            return res;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            throw e;
        }
    }

    public List<Subcategory> getSubcategoriesFromCategory(Category category) {
        Transaction transaction = null;

        try {
            Session session = SessionUtil.getSession();
            transaction = session.beginTransaction();
            List<Subcategory> res =  session.createQuery("FROM Subcategories where category.id = :id", Subcategory.class).
                    setParameter("id", category.getId()).
                    getResultList();
            transaction.commit();

            return res;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            throw e;
        }
    }

    public void deleteSubcategory(Subcategory subcategory){
        Transaction transaction = null;

        try {
            Session session = SessionUtil.getSession();
            transaction = session.beginTransaction();
            subcategory.getCategory().getSubcategories().remove(subcategory);
            subcategory.setCategory(null);
            session.flush();
            session.remove(subcategory);
            String sql = String.format("UPDATE Transactions SET subCategory_id = null WHERE subCategory_id = %d", subcategory.getId());
            session.createNativeQuery(sql).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
