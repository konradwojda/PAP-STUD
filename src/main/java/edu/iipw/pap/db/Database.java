package edu.iipw.pap.db;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import edu.iipw.pap.db.model.Agency;

public class Database {
    private static final SessionFactory SESSION_FACTORY = new Configuration().configure().buildSessionFactory();
    private static Session session = null;

    public static void initialize() throws Exception {
        session = SESSION_FACTORY.openSession();
    }

    public static void close() throws Exception {
        session.close();
    }

    public static Collection<String> getAgencies() throws Exception {
        ArrayList<String> names = new ArrayList<String>();

        try {
            var result = session.createQuery("from Agency").list();
            for (var obj : result) {
                names.add(((Agency) obj).getName());
            }
            return names;
        } finally {
            session.close();
        }
    }

    public static void delete(Object obj) {
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.delete(obj);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    public static void add(Object obj) {
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.save(obj);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    public static <T> List<T> listAll(Class<T> cls) {
        var criteriaQuery = session.getCriteriaBuilder().createQuery(cls);
        var root = criteriaQuery.from(cls);
        criteriaQuery.select(root);

        return session.createQuery(criteriaQuery).getResultList();
    }
}
