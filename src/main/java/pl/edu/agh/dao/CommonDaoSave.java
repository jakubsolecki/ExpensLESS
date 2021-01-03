package pl.edu.agh.dao;

import org.hibernate.Session;
import pl.edu.agh.util.SessionUtil;

public abstract class CommonDaoSave {

    public static <T> void save(T entity) {
        org.hibernate.Transaction tr = null;

        try {
            Session session = SessionUtil.getSession();
            tr = session.beginTransaction();
            session.saveOrUpdate(entity);
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }

            throw e;
        }
    }
}
