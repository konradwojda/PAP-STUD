package edu.iipw.pap.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import edu.iipw.pap.db.model.Agency;
import edu.iipw.pap.db.model.Line;
import edu.iipw.pap.db.model.Stop;
import edu.iipw.pap.db.model.Calendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

// cSpell: word rawtypes

/**
 * Database is a singleton (static) object representing a connection with the database
 */
@SuppressWarnings("rawtypes")
public class Database {
    private static final SessionFactory SESSION_FACTORY = new Configuration().configure().buildSessionFactory();
    private static Session session = null;

    /**
     * A cache for "top-level" objects - otherwise it's not possible
     * to provide an ObservableSet of those objects.
     */
    private static Map<Class, ObservableSet> caches = new HashMap<>();

    /**
     * Creates a session for connection with the database
     * @throws Exception
     */
    public static void initialize() throws Exception {
        session = SESSION_FACTORY.openSession();
    }

    /**
     * Closes the existing connection with the database
     * @throws Exception
     */
    public static void close() throws Exception {
        session.close();
    }

    /**
     * Checks if the provided class is cacheable - aka whether it's a top-level object.
     */
    public static boolean isCacheable(Class cls) {
        return cls.equals(Agency.class)
            || cls.equals(Calendar.class)
            || cls.equals(Line.class)
            || cls.equals(Stop.class);
    }

    /**
     * Removes an object from the database - invalidating it.
     */
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

        // Remove from cache
        Class objClass = obj.getClass();
        if (isCacheable(obj.getClass()) && caches.containsKey(objClass)) {
            caches.get(objClass).remove(obj);
        }
    }

    /**
     * Persists an object.
     * Use `save()` instead - the name `add` is confusing.
     */
    @Deprecated public static void add(Object obj) {
        save(obj);
    }

    /**
     * Persists an object.
     * If an object with the same primary key is in the database - the appropriate row is updated.
     * Otherwise, creates a new row; and assigns a primary key for the new object.
     */
    public static void save(Object obj) {
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

        // Add to cache
        Class objClass = obj.getClass();
        if (isCacheable(obj.getClass()) && caches.containsKey(objClass)) {
            caches.get(objClass).add(obj);
        }
    }

    /**
     * Makes a SELECT query to the database to return all known objects of a type.
     */
    private static <T> List<T> listAllFromDatabase(Class<T> cls) {
        var criteriaQuery = session.getCriteriaBuilder().createQuery(cls);
        var root = criteriaQuery.from(cls);
        criteriaQuery.select(root);

        return session.createQuery(criteriaQuery).getResultList();
    }

    /**
     * Returns all known objects of a particular type.
     */
    @SuppressWarnings("unchecked")
    public static <T> ObservableSet<T> listAll(Class<T> cls) {
        if (!isCacheable(cls)) {
            // TODO: Custom exception
            throw new RuntimeException("Only top-level types can be listed (Agency/Calendar/Line/Stop)");
        }

        ObservableSet<T> cached = (ObservableSet<T>)caches.get(cls);
        if (cached != null) return cached;

        cached = FXCollections.observableSet((T[])listAllFromDatabase(cls).toArray());
        caches.put(cls, cached);
        return cached;
    }
}
