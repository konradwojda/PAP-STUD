package edu.iipw.pap.db.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

@Entity
@Table(name = "stops")
public class Stop {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "stop_id")
    private IntegerProperty stopId = new SimpleIntegerProperty();

    public IntegerProperty stopIdProperty() {
        return stopId;
    }

    public int getStopId() {
        return stopId.get();
    }

    public void setStopId(int value) {
        stopId.set(value);
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

    @Column(name = "code")
    private StringProperty code = new SimpleStringProperty();

    public StringProperty codeProperty() {
        return code;
    }

    public String getCode() {
        return code.get();
    }

    public void setCode(String value) {
        code.set(value);
    }

    @Column(name = "lat", nullable = false)
    private DoubleProperty lat = new SimpleDoubleProperty();

    public DoubleProperty latProperty() {
        return lat;
    }

    public double getLat() {
        return lat.get();
    }

    public void setLat(double value) {
        lat.set(value);
    }

    @Column(name = "lon", nullable = false)
    private DoubleProperty lon = new SimpleDoubleProperty();

    public DoubleProperty lonProperty() {
        return lon;
    }

    public double getLon() {
        return lon.get();
    }

    public void setLon(double value) {
        lon.set(value);
    }

    @Column(name = "wheelchair_accessible", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private ObjectProperty<WheelchairAccessibility> wheelchairAccessible = new SimpleObjectProperty<WheelchairAccessibility>(
            WheelchairAccessibility.UNKNOWN);

    public ObjectProperty<WheelchairAccessibility> wheelchairAccessibleProperty() {
        return wheelchairAccessible;
    }

    public WheelchairAccessibility getWheelchairAccessible() {
        return wheelchairAccessible.get();
    }

    public void setWheelchairAccessible(WheelchairAccessibility value) {
        wheelchairAccessible.set(value);
    }

    @OneToMany(mappedBy = "stop")
    private SetProperty<PatternStop> patternStops = new SimpleSetProperty<PatternStop>();

    public SetProperty<PatternStop> patternStopsProperty() {
        return patternStops;
    }

    public ObservableSet<PatternStop> getPatternStops() {
        return patternStops.get();
    }

    public void setPatternStops(Set<PatternStop> value) {
        if (ObservableSet.class.isAssignableFrom(value.getClass())) {
            patternStops.set((ObservableSet<PatternStop>) value);
        } else {
            patternStops.set(FXCollections.observableSet(value));
        }
    }

    public String toString() {
        return String.format(
                "Stop(%d, %s, code=%s, lat=%f, lon=%f, wheelchairAccessible=%s)",
                getStopId(),
                getName(),
                getCode(),
                getLat(),
                getLon(),
                getWheelchairAccessible());
    }
}
