package edu.iipw.pap.db;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import edu.iipw.pap.db.model.Agency;

public class Database {
    public static Collection<String> agencyNames() throws Exception {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        ArrayList<String> names = new ArrayList<String>();

        try {
            var result = session.createQuery("from Agency").list();
            for (var obj : result) {
                names.add(((Agency)obj).getName());
            }
            return names;
        } finally {
            session.close();
        }

    }
}
