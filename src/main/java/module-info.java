module edu.iipw.pap {
    requires javafx.controls;
    requires transitive javafx.graphics;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires javafx.fxml;
    requires java.sql;
    requires transitive java.logging;
    requires lombok;

    exports edu.iipw.pap;
    exports edu.iipw.pap.db;
    exports edu.iipw.pap.db.model;
    exports edu.iipw.pap.db.typeConverters;

    opens edu.iipw.pap.db.model;
    opens edu.iipw.pap.controller;
}
