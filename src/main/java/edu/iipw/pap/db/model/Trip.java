package edu.iipw.pap.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

@Entity
@Table(name = "trips")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "trip_id")
    private IntegerProperty tripId = new SimpleIntegerProperty();

    public IntegerProperty tripIdProperty() {
        return tripId;
    }

    public int getTripId() {
        return tripId.get();
    }

    public void setTripId(int value) {
        tripId.set(value);
    }

    @Column(name = "departure")
    private IntegerProperty departure = new SimpleIntegerProperty();

    public IntegerProperty departureProperty() {
        return departure;
    }

    public int getDeparture() {
        return departure.get();
    }

    public void setDeparture(int value) {
        departure.set(value);
    }

    @ManyToOne
    @JoinColumn(name = "pattern_id", nullable = false)
    private ObjectProperty<Pattern> pattern = new SimpleObjectProperty<>();

    public ObjectProperty<Pattern> patternProperty() {
        return pattern;
    }

    public Pattern getPattern() {
        return pattern.get();
    }

    public void setPattern(Pattern value) {
        pattern.set(value);
    }

    @ManyToOne
    @JoinColumn(name = "calendar_id", nullable = false)
    private ObjectProperty<Calendar> calendar = new SimpleObjectProperty<>();

    public ObjectProperty<Calendar> calendarProperty() {
        return calendar;
    }

    public Calendar getCalendar() {
        return calendar.get();
    }

    public void setCalendar(Calendar value) {
        calendar.set(value);
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

    public String toString() {
        return String.format(
                "Trip(%d, departure=%d, pattern=%s, calendar=%s, wheelchairAccessible=%s)",
                getTripId(),
                getDeparture(),
                getPattern(),
                getCalendar(),
                getWheelchairAccessible());
    }
}
