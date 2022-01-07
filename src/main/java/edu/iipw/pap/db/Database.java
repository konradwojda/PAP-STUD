package edu.iipw.pap.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
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
 * Database is a singleton (static) object representing
 * a connection with the database
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class Database extends ObjectPool {
    public static final Database INSTANCE = new Database();
    private static final SessionFactory SESSION_FACTORY = new Configuration().configure().buildSessionFactory();
    private Session session = null;

    /**
     * Private constructor for the database - Database is a singleton
     */
    private Database() {
    }

    /**
     * A cache for "top-level" objects - otherwise it's not possible
     * to provide an ObservableSet of those objects.
     */
    private Map<Class, ObservableSet> caches = new HashMap<>();

    /**
     * Creates a session for connection with the database
     *
     * @throws HibernateException
     */
    public void initialize() throws HibernateException {
        session = SESSION_FACTORY.openSession();
    }

    /**
     * Closes the existing connection with the database
     *
     * @throws HibernateException
     */
    public void close() throws HibernateException {
        session.close();
    }

    /**
     * Checks if the provided class is cacheable - aka whether it's a top-level
     * object.
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
    public <T> void delete(T obj) {
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
     * If an object with the same primary key is in the database - the appropriate
     * row is updated.
     * Otherwise, creates a new row; and assigns a primary key for the new object.
     */
    public <T> void save(T obj) {
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(obj);
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
     * Persists multiple objects in one transaction.
     */
    public final void batchSave(Object... objs) {
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            for (Object obj : objs)
                session.saveOrUpdate(obj);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }

        // Add to cache
        for (Object obj : objs) {
            Class objClass = obj.getClass();
            if (isCacheable(obj.getClass()) && caches.containsKey(objClass)) {
                caches.get(objClass).add(obj);
            }
        }
    }

    /**
     * Deletes multiple objects in one go.
     */
    public void batchDelete(Object... objs) {
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            for (Object obj : objs)
                session.delete(obj);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }

        // Remove from cache
        for (Object obj : objs) {
            Class objClass = obj.getClass();
            if (isCacheable(obj.getClass()) && caches.containsKey(objClass)) {
                caches.get(objClass).remove(obj);
            }
        }
    }

    /**
     * Persists and deletes multiple objects in one go.
     */
    public void batchSaveAndDelete(Object[] toSave, Object[] toDelete) {
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            for (Object obj : toDelete)
                session.delete(obj);
            for (Object obj : toSave)
                session.saveOrUpdate(obj);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }

        // Remove from cache
        for (Object obj : toDelete) {
            Class objClass = obj.getClass();
            if (isCacheable(obj.getClass()) && caches.containsKey(objClass)) {
                caches.get(objClass).remove(obj);
            }
        }

        // Add to cache
        for (Object obj : toSave) {
            Class objClass = obj.getClass();
            if (isCacheable(obj.getClass()) && caches.containsKey(objClass)) {
                caches.get(objClass).add(obj);
            }
        }
    }

    /**
     * Makes a SELECT query to the database to return all known objects of a type.
     */
    private <T> List<T> listAllFromDatabase(Class<T> cls) {
        var criteriaQuery = session.getCriteriaBuilder().createQuery(cls);
        var root = criteriaQuery.from(cls);
        criteriaQuery.select(root);

        return session.createQuery(criteriaQuery).getResultList();
    }

    /**
     * Returns all known objects of a particular type.
     */
    public <T> ObservableSet<T> listAll(Class<T> cls) {
        if (!isCacheable(cls)) {
            // TODO: Custom exception
            throw new RuntimeException("Only top-level types can be listed (Agency/Calendar/Line/Stop)");
        }

        ObservableSet<T> cached = (ObservableSet<T>) caches.get(cls);
        if (cached != null)
            return cached;

        cached = FXCollections.observableSet((T[]) listAllFromDatabase(cls).toArray());
        caches.put(cls, cached);
        return cached;
    }
}
