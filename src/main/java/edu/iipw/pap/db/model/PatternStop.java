package edu.iipw.pap.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
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
 * PatternStop is an entity representing a stoppage of a pattern at a stop.
 */
@Entity
@Table(name = "pattern_stops")
public final class PatternStop {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "pattern_stop_id")
    private IntegerProperty patternStopId = new SimpleIntegerProperty();

    /**
     * Returns an observable property of the pattern-stops's ID.
     * The ID is just a unique identifier of the line within the dataset.
     */
    public IntegerProperty patternStopIdProperty() {
        return patternStopId;
    }

    /**
     * Returns the ID of the pattern-stop at the current point in time.
     * The ID is just a unique identifier of the line within the dataset.
     */
    public int getPatternStopId() {
        return patternStopId.get();
    }

    /**
     * Updates the pattern-stop's ID
     */
    public void setPatternStopId(int value) {
        patternStopId.set(value);
    }

    @ManyToOne
    @JoinColumn(name = "stop_id", nullable = false)
    private ObjectProperty<Stop> stop = new SimpleObjectProperty<Stop>();

    /**
     * Returns an observable property onto the stop
     * of this stoppage.
     */
    public ObjectProperty<Stop> stopProperty() {
        return stop;
    }

    /**
     * Returns the stop of this stoppage at this point in time.
     */
    public Stop getStop() {
        return stop.get();
    }

    /**
     * Changes the stoppages stop.
     */
    public void setStop(Stop value) {
        stop.set(value);
    }

    @ManyToOne
    @JoinColumn(name = "pattern_id", nullable = false)
    private ObjectProperty<Pattern> pattern = new SimpleObjectProperty<Pattern>();

    /**
     * Returns an observable property onto the pattern containing this stoppage
     */
    public ObjectProperty<Pattern> patternProperty() {
        return pattern;
    }

    /**
     * Returns the pattern that contains the stoppage at this point in time.
     */
    public Pattern getPattern() {
        return pattern.get();
    }

    /**
     * Updates the pattern to which this stoppage is attached.
     */
    public void setPattern(Pattern value) {
        pattern.set(value);
    }

    @Column(name = "idx")
    private IntegerProperty index = new SimpleIntegerProperty();

    /**
     * Returns an observable property onto the index of the stoppage.
     * All stoppages of a pattern are assumed to be travelled in the order
     * this property specifies.
     */
    public IntegerProperty indexProperty() {
        return index;
    }

    /**
     * Returns the index of the stoppage at this point in time.
     * All stoppages of a pattern are assumed to be travelled in the order
     * this property specifies.
     */
    public int getIndex() {
        return index.get();
    }

    /**
     * Changes the index of the stoppage.
     */
    public void setIndex(int value) {
        index.set(value);
    }

    @Column(name = "travel_time")
    private IntegerProperty travelTime = new SimpleIntegerProperty();

    /**
     * Returns an observable property onto the travel time (from the start of the
     * pattern) until this stoppage; in seconds.
     */
    public IntegerProperty travelTimeProperty() {
        return travelTime;
    }

    /**
     * Returns the travel time (from the start of the pattern) until this stoppage;
     * in seconds.
     */
    public int getTravelTime() {
        return travelTime.get();
    }

    /**
     * Sets the travel time of this stoppage.
     */
    public void setTravelTime(int value) {
        travelTime.set(value);
    }

    /**
     * Returns a programmer-readable representation of the Calendar.
     */
    public String toString() {
        return String.format(
                "PatternStop(%d, index=%d, travel_time=%d, stop=%s, pattern=%s)",
                getPatternStopId(),
                getIndex(),
                getTravelTime(),
                getStop(),
                getPattern());
    }

    /**
     * Checks whether the PatternStop contains semantically correct data.
     * Individual setters don't check the field validity (to not annoy the
     * programmer)
     *
     * @throws InvalidData in case invalid data is detected
     */
    public void validateUserInput() throws InvalidData {
        // Validate stop
        if (getStop() == null)
            throw new InvalidData("Stop cannot be null");

        // Validate pattern
        if (getPattern() == null)
            throw new InvalidData("Pattern cannot be null");

        // Validate index
        if (getIndex() < 0)
            throw new InvalidData("Index cannot be negative");

        // Validate travel time
        if (getTravelTime() < 0)
            throw new InvalidData("Travel time cannot be negative");
    }
}
