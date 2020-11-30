package pl.edu.agh.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.edu.agh.model.Subcategory;
import pl.edu.agh.util.SessionUtil;

public class SubcategoryDao implements ISubcategoryDao {

    @Override
    public void saveSubcategory(Subcategory subcategory) {
        Session session = SessionUtil.getSession();
        Transaction transaction = session.getTransaction();
        session.save(subcategory);
        transaction.commit();
    }
}
