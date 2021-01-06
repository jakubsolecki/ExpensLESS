package pl.edu.agh.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.edu.agh.model.Category;
import pl.edu.agh.model.Subcategory;
import pl.edu.agh.util.SessionUtil;

import javax.persistence.NoResultException;
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

    public Subcategory findSubcategoryByName(String name) {
        Transaction transaction = null;

        try {
            Session session = SessionUtil.getSession();
            transaction = session.beginTransaction();

            Subcategory res = (Subcategory) session.createQuery("FROM Subcategories where name = :name")
                    .setParameter("name", name)
                    .getSingleResult();
            transaction.commit();

            return res;
        } catch (NoResultException n){
          return null;
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
}
