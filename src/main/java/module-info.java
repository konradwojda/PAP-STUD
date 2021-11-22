module edu.iipw.pap {
    requires javafx.controls;
    requires transitive javafx.graphics;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires java.sql;

    exports edu.iipw.pap;
    opens edu.iipw.pap;
}
