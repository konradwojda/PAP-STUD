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

import edu.iipw.pap.exceptions.InvalidData;
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

/**
 * Stop represents a single place where vehicles can exchange passengers.
 */
@Entity
@Table(name = "stops")
public final class Stop {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "stop_id")
    private IntegerProperty stopId = new SimpleIntegerProperty();

    /**
     * Returns an observable property of the stop's ID.
     * The ID is just a unique identifier of the line within the dataset.
     */
    public IntegerProperty stopIdProperty() {
        return stopId;
    }

    /**
     * Returns the ID of the stop at the current point in time.
     * The ID is just a unique identifier of the line within the dataset.
     */
    public int getStopId() {
        return stopId.get();
    }

    /**
     * Changes the stop's ID.
     */
    public void setStopId(int value) {
        stopId.set(value);
    }

    @Column(name = "name", nullable = false)
    private StringProperty name = new SimpleStringProperty();

    /**
     * Returns an observable property on the stop's name.
     * The name should be easily recognizable by riders both looking for the stop,
     * and wanting to alight from a vehicle.
     */
    public StringProperty nameProperty() {
        return name;
    }

    /**
     * Returns the stop's name at this point in time.
     * The name should be easily recognizable by riders both looking for the stop,
     * and wanting to alight from a vehicle.
     */
    public String getName() {
        return name.get();
    }

    /**
     * Updates the stop's name.
     */
    public void setName(String value) {
        name.set(value);
    }

    @Column(name = "code")
    private StringProperty code = new SimpleStringProperty();

    /**
     * Returns an observable property onto the stop's code.
     * The stop code should be a short (up to 7/8 chars) alphanumeric code,
     * visible to passengers. Contrary to names - the code itself usually doesn't
     * provide
     * any information about _where_ the stop is.
     *
     * This information is optional.
     */
    public StringProperty codeProperty() {
        return code;
    }

    /**
     * Returns the stop code at this point time.
     * The stop code should be a short (up to 7/8 chars) alphanumeric code,
     * visible to passengers. Contrary to names - the code itself usually doesn't
     * provide
     * any information about _where_ the stop is.
     *
     * This information is optional.
     */
    public String getCode() {
        return code.get();
    }

    /**
     * Sets the stop's code.
     */
    public void setCode(String value) {
        code.set(value);
    }

    @Column(name = "lat", nullable = false)
    private DoubleProperty lat = new SimpleDoubleProperty();

    /**
     * Returns an observable property on to the stops latitude,
     * expressed as degrees on the WGS84 ellipsoid.
     */
    public DoubleProperty latProperty() {
        return lat;
    }

    /**
     * Returns the stop's latitude at this point in time,
     * expressed as degrees on the WGS84 ellipsoid.
     */
    public double getLat() {
        return lat.get();
    }

    /**
     * Sets the stop's latitude.
     */
    public void setLat(double value) {
        lat.set(value);
    }

    @Column(name = "lon", nullable = false)
    private DoubleProperty lon = new SimpleDoubleProperty();

    /**
     * Returns an observable property on to the stops longitude,
     * expressed as degrees on the WGS84 ellipsoid.
     */
    public DoubleProperty lonProperty() {
        return lon;
    }

    /**
     * Returns the stop's longitude at this point in time,
     * expressed as degrees on the WGS84 ellipsoid.
     */
    public double getLon() {
        return lon.get();
    }

    /**
     * Sets the stop's longitude
     */
    public void setLon(double value) {
        lon.set(value);
    }

    @Column(name = "wheelchair_accessible", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private ObjectProperty<WheelchairAccessibility> wheelchairAccessible = new SimpleObjectProperty<WheelchairAccessibility>(
            WheelchairAccessibility.UNKNOWN);

    /**
     * Returns an observable property on stop's wheelchair accessibility
     * information.
     */
    public ObjectProperty<WheelchairAccessibility> wheelchairAccessibleProperty() {
        return wheelchairAccessible;
    }

    /**
     * Returns stop's wheelchair accessibility information at this point in time.
     */
    public WheelchairAccessibility getWheelchairAccessible() {
        return wheelchairAccessible.get();
    }

    /**
     * Updates stop's wheelchair accessibility information.
     *
     * @param value
     */
    public void setWheelchairAccessible(WheelchairAccessibility value) {
        wheelchairAccessible.set(value);
    }

    @OneToMany(mappedBy = "stop")
    private SetProperty<PatternStop> patternStops = new SimpleSetProperty<PatternStop>();
    private Set<PatternStop> patternStopsRaw;

    /**
     * Returns an observable set of all stoppages attached to this stop.
     *
     * Due to a conflict with the classical getters and Hibernate, never call
     * `patternStopsProperty().set` (or similar functions) - use `setPatternStops()`
     * directly.
     */
    public SetProperty<PatternStop> patternStopsProperty() {
        return patternStops;
    }

    /**
     * Returns a list of all stoppages at stops attached to this pattern.
     *
     * This getter is only present to maintain compatibility with Hibernate -
     * use `patternStopsProperty()` (which implements Set<PatternStop>) directly to
     * correctly propagate changes to any listeners.
     */
    @Deprecated
    Set<PatternStop> getPatternStops() {
        assert patternStops.equals(patternStopsRaw)
                : "Illegal state - patternStopsProperty().set() was probably called";
        return patternStopsRaw;
    }

    /**
     * Swaps the underlying list of stoppages.
     */
    public void setPatternStops(Set<PatternStop> value) {
        patternStopsRaw = value;
        patternStops.set(FXCollections.observableSet(value));
    }

    /**
     * Returns a programmer-readable representation of the Calendar.
     */
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

    /**
     * Checks whether the Stop contains semantically correct data.
     * Individual setters don't check the field validity (to not annoy the
     * programmer)
     *
     * @throws InvalidData in case invalid data is detected
     */
    public void validateUserInput() throws InvalidData {
        // Validate the name
        if (getName() == null || getName().length() == 0)
            throw new InvalidData("Name cannot be empty");

        // No need to validate the code

        // Validate the latitude
        if (getLat() > 90.0 || getLat() < -90.0)
            throw new InvalidData("Latitude has to be within -90 and 90 degrees.");

        if (getLon() > 180.0 || getLon() < -180.0)
            throw new InvalidData("Longitude has to be within -180 and 180 degrees.");

        // Validate wheelchair accessibility
        if (getWheelchairAccessible() == null)
            throw new InvalidData("WheelchairAccessibility cannot be null");
    }
}
