package edu.iipw.pap.db.model;

import java.util.stream.Stream;

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

import edu.iipw.pap.exceptions.InvalidData;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Trip represents a single vehicle following a pattern,
 * at a particular time and on a particular calendar.
 */
@Entity
@Table(name = "trips")
public final class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id")
    private IntegerProperty tripId = new SimpleIntegerProperty();

    /**
     * Returns an observable property of the trips's ID.
     * The ID is just a unique identifier of the line within the dataset.
     */
    public IntegerProperty tripIdProperty() {
        return tripId;
    }

    /**
     * Returns the ID of the trip at the current point in time.
     * The ID is just a unique identifier of the line within the dataset.
     */
    public int getTripId() {
        return tripId.get();
    }

    /**
     * Changes the trip's ID.
     */
    public void setTripId(int value) {
        tripId.set(value);
    }

    @Column(name = "departure")
    private IntegerProperty departure = new SimpleIntegerProperty();

    /**
     * Returns an observable property on the trip's departure time.
     * The departure time represents a timestamp of the departure
     * from the first stop of the pattern.
     *
     * Expressed as seconds since midnight.
     *
     * Cannot be negative, but can be greater than 24 hours
     * (frequently used for night buses).
     */
    public IntegerProperty departureProperty() {
        return departure;
    }

    /**
     * Returns trip's departure time at this point in time.
     * The departure time represents a timestamp of the departure
     * from the first stop of the pattern.
     *
     * Expressed as seconds since midnight.
     *
     * Cannot be negative, but can be greater than 24 hours
     * (frequently used for night buses).
     */
    public int getDeparture() {
        return departure.get();
    }

    /**
     * Sets trip's departure time.
     */
    public void setDeparture(int value) {
        departure.set(value);
    }

    @ManyToOne
    @JoinColumn(name = "pattern_id", nullable = false)
    private ObjectProperty<Pattern> pattern = new SimpleObjectProperty<>();

    /**
     * Returns an observable property onto the pattern of this trip.
     */
    public ObjectProperty<Pattern> patternProperty() {
        return pattern;
    }

    /**
     * Returns the pattern of this trip at this point in time.
     */
    public Pattern getPattern() {
        return pattern.get();
    }

    /**
     * Changes the pattern of this trip.
     */
    public void setPattern(Pattern value) {
        pattern.set(value);
    }

    @ManyToOne
    @JoinColumn(name = "calendar_id", nullable = false)
    private ObjectProperty<Calendar> calendar = new SimpleObjectProperty<>();

    /**
     * Returns an observable property onto the calendar to which this trip is
     * attached.
     */
    public ObjectProperty<Calendar> calendarProperty() {
        return calendar;
    }

    /**
     * Returns the calendar to which this trip is attached at this point in time.
     */
    public Calendar getCalendar() {
        return calendar.get();
    }

    /**
     * Changes the calendar to which the trip is attached.
     */
    public void setCalendar(Calendar value) {
        calendar.set(value);
    }

    @Column(name = "wheelchair_accessible", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private ObjectProperty<WheelchairAccessibility> wheelchairAccessible = new SimpleObjectProperty<WheelchairAccessibility>(
            WheelchairAccessibility.UNKNOWN);

    /**
     * Returns an observable property onto the accessibility of the trip
     * to accommodate riders in wheelchairs.
     */
    public ObjectProperty<WheelchairAccessibility> wheelchairAccessibleProperty() {
        return wheelchairAccessible;
    }

    /**
     * Returns the accessibility of the trip to accommodate riders in wheelchairs at
     * this point in time.
     */
    public WheelchairAccessibility getWheelchairAccessible() {
        return wheelchairAccessible.get();
    }

    /**
     * Updates the wheelchair accessibility info of this trip.
     */
    public void setWheelchairAccessible(WheelchairAccessibility value) {
        wheelchairAccessible.set(value);
    }

    /**
     * Returns a programmer-readable representation of the Calendar.
     */
    public String toString() {
        return String.format(
                "Trip(%d, departure=%d, pattern=%s, calendar=%s, wheelchairAccessible=%s)",
                getTripId(),
                getDeparture(),
                getPattern(),
                getCalendar(),
                getWheelchairAccessible());
    }

    /**
     * Checks whether the Trip contains semantically correct data.
     * Individual setters don't check the field validity (to not annoy the
     * programmer)
     *
     * @throws InvalidData in case invalid data is detected
     */
    public void validateUserInput() throws InvalidData {
        // Validate the departure
        if (getDeparture() < 0)
            throw new InvalidData("Departure time cannot be negative");

        // Validate the pattern
        if (getPattern() == null)
            throw new InvalidData("Pattern cannot be empty");

        // Validate the calendar
        if (getCalendar() == null)
            throw new InvalidData("Calendar cannot be empty");

        // Validate the wheelchair accessibility
        if (getWheelchairAccessible() == null)
            throw new InvalidData("WheelchairAccessibility cannot be null");
    }

    /**
     * Generate all StopTimes from combining this trip and the corresponding pattern stop
     */
    public Stream<StopTime> getStopTimes() {
        return getPattern()
            .patternStopsProperty()
            .stream()
            .map(ps -> StopTime.tripAtPatternStop(this, ps));
    }

}
