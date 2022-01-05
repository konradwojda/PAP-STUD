package edu.iipw.pap.db.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

@Entity
@Table(name = "agencies")
public final class Agency {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "agency_id")
    private IntegerProperty agencyId = new SimpleIntegerProperty();

    public IntegerProperty agencyIdProperty() {
        return agencyId;
    }

    public int getAgencyId() {
        return agencyId.get();
    }

    public void setAgencyId(int value) {
        agencyId.set(value);
    }

    @Column(name = "name", nullable = false)
    private StringProperty name = new SimpleStringProperty();

    public StringProperty nameProperty() {
        return name;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String value) {
        name.set(value);
    }

    @Column(name = "website")
    private StringProperty website = new SimpleStringProperty();

    public StringProperty websiteProperty() {
        return website;
    }

    public String getWebsite() {
        return website.get();
    }

    public void setWebsite(String value) {
        website.set(value);
    }

    @Column(name = "timezone")
    private StringProperty timezone = new SimpleStringProperty();

    public StringProperty timezoneProperty() {
        return timezone;
    }

    public String getTimezone() {
        return timezone.get();
    }

    public void setTimezone(String value) {
        timezone.set(value);
    }

    @Column(name = "telephone")
    private StringProperty telephone = new SimpleStringProperty();

    public StringProperty telephoneProperty() {
        return telephone;
    }

    public String getTelephone() {
        return telephone.get();
    }

    public void setTelephone(String value) {
        telephone.set(value);
    }

    @OneToMany(mappedBy = "agency")
    private SetProperty<Line> lines = new SimpleSetProperty<Line>();
    private Set<Line> linesRaw;

    public SetProperty<Line> linesProperty() {
        return lines;
    }

    @Deprecated
    public Set<Line> getLines() {
        assert lines.equals(linesRaw) : "Illegal state - linesProperty().set() was probably called";
        return linesRaw;
    }

    public void setLines(Set<Line> value) {
        linesRaw = value;
        lines.set(FXCollections.observableSet(value));
    }

    public String toString() {
        return String.format("Agency(%d, %s)", getAgencyId(), getName());
    }
}
