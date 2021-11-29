module edu.iipw.pap {
    requires javafx.controls;
    requires transitive javafx.graphics;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires javafx.fxml;
    requires java.sql;

    exports edu.iipw.pap;
    exports edu.iipw.pap.db.model;
    opens edu.iipw.pap.db.model;
    opens edu.iipw.pap.controller;
}
