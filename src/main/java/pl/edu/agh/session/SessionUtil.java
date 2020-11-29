package pl.edu.agh.session;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionUtil {
    public static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    private static Session session;

    public static void openSession() {
        session = sessionFactory.openSession();
    }

    public static Session getSession() {
        if (session == null){
            throw new IllegalStateException("Session not opened!");
        }
        return session;
    }

    public static void closeSession() {
        session.close();
    }

}
