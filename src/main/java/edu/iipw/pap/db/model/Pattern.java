package edu.iipw.pap.db.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import edu.iipw.pap.exceptions.InvalidData;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

/**
 * Pattern represents a sequence of stops (with travel times between them) -
 * attached to a single line. In other words, it's a _variant_ of a line.
 */
@Entity
@Table(name = "patterns")
public final class Pattern {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pattern_id")
    private IntegerProperty patternId = new SimpleIntegerProperty();

    /**
     * Returns an observable property of the pattern's ID.
     * The ID is just a unique identifier of the line within the dataset.
     */
    public IntegerProperty patternIdProperty() {
        return patternId;
    }

    /**
     * Returns the ID of the pattern at the current point in time.
     * The ID is just a unique identifier of the line within the dataset.
     */
    public int getPatternId() {
        return patternId.get();
    }

    /**
     * Sets the pattern's ID.
     */
    public void setPatternId(int value) {
        patternId.set(value);
    }

    @Column(name = "headsign")
    private StringProperty headsign = new SimpleStringProperty();

    /**
     * Returns an observable property of the pattern's headsign.
     * A headsign should be the text shown on the front of the vehicle
     * (usually the destination) that help riders choose the correct vehicle.
     *
     * This information is optional - applications usually fallback to the last stop's name.
     */
    public StringProperty headsignProperty() {
        return headsign;
    }

    /**
     * Returns the pattern's headsign at the current point in time.
     * A headsign should be the text shown on the front of the vehicle
     * (usually the destination) that help riders choose the correct vehicle.
     *
     * This information is optional - applications usually fallback to the last stop's name.
     */
    public String getHeadsign() {
        return headsign.get();
    }

    /**
     * Updates the pattern's headsign.
     */
    public void setHeadsign(String value) {
        headsign.set(value);
    }

    @Column(name = "direction")
    @Enumerated(EnumType.ORDINAL)
    private ObjectProperty<PatternDirection> direction = new SimpleObjectProperty<PatternDirection>();

    /**
     * Returns an observable property onto the pattern's direction.
     * Pattern's direction is a simple representation to group `outbound` and
     * `inbound`
     * variants of a line.
     */
    public ObjectProperty<PatternDirection> directionProperty() {
        return direction;
    }

    /**
     * Returns the pattern's direction at this point in time.
     * Pattern's direction is a simple representation to group `outbound` and
     * `inbound`
     * variants of a line.
     */
    public PatternDirection getDirection() {
        return direction.get();
    }

    /**
     * Sets the pattern's direction.
     */
    public void setDirection(PatternDirection value) {
        direction.set(value);
    }

    @ManyToOne
    @JoinColumn(name = "line_id", nullable = false)
    private ObjectProperty<Line> line = new SimpleObjectProperty<Line>();

    /**
     * Returns an observable property onto the line to which the pattern is
     * assigned.
     */
    public ObjectProperty<Line> lineProperty() {
        return line;
    }

    /**
     * Returns the line to which the pattern is attached to.
     */
    public Line getLine() {
        return line.get();
    }

    /**
     * Swaps the attached line of the pattern.
     */
    public void setLine(Line value) {
        line.set(value);
    }

    @OneToMany(mappedBy = "pattern")
    private ListProperty<PatternStop> patternStops = new SimpleListProperty<PatternStop>();
    private List<PatternStop> patternStopsRaw;

    /**
     * Returns an observable list of all stoppages at stops attached to this
     * pattern.
     *
     * Due to a conflict with the classical getters and Hibernate, never call
     * `patternStopsProperty().set` (or similar functions) - use `setPatternStops()`
     * directly.
     */
    public ListProperty<PatternStop> patternStopsProperty() {
        return patternStops;
    }

    /**
     * Returns a list of all stoppages at stops attached to this pattern.
     *
     * This getter is only present to maintain compatibility with Hibernate -
     * use `patternStopsProperty()` (which implements List<PatternStop>) directly to
     * correctly propagate changes to any listeners.
     */
    @Deprecated
    public List<PatternStop> getPatternStops() {
        assert patternStops.equals(patternStopsRaw)
                : "Illegal state - patternStopsProperty().set() was probably called";
        return patternStopsRaw;
    }

    /**
     * Swaps the underlying list of stoppages at stops.
     */
    public void setPatternStops(List<PatternStop> value) {
        patternStopsRaw = value;
        patternStops.set(FXCollections.observableList(value));
    }

    @OneToMany(mappedBy = "pattern")
    private SetProperty<Trip> trips = new SimpleSetProperty<Trip>();
    private Set<Trip> tripsRaw;

    /**
     * Returns an observable property of the set of all trips attached to this
     * pattern.
     *
     * Due to a conflict with the classical getters and Hibernate, never call
     * `tripsProperty().set` (or similar functions) - use `setTrips()` directly.
     */
    public SetProperty<Trip> tripsProperty() {
        return trips;
    }

    /**
     * Returns a set of all trips attached to this pattern.
     *
     * This getter is only present to maintain compatibility with Hibernate -
     * use `tripsProperty()` (which implements Set<Trip>) directly to correctly
     * propagate changes to any listeners.
     */
    @Deprecated
    public Set<Trip> getTrips() {
        assert trips.equals(tripsRaw) : "Illegal state - tripsProperty().set() was probably called";
        return tripsRaw;
    }

    /**
     * Swaps the underlying set of attached trips.
     */
    public void setTrips(Set<Trip> value) {
        if(value == null) {
            value = new HashSet<>();
        }
        tripsRaw = value;
        trips.set(FXCollections.observableSet(value));
    }

    /**
     * Returns a programmer-readable representation of the Calendar.
     */
    public String toString() {
        return String.format(
                "Pattern(%d, headsign=%s, direction=%s, line=%s, %d stops)",
                getPatternId(),
                getHeadsign(),
                getDirection(),
                getLine(),
                getPatternStops() == null ? 0 : getPatternStops().size());
    }

    /**
     * Checks whether the Pattern contains semantically correct data.
     * Individual setters don't check the field validity (to not annoy the
     * programmer)
     *
     * @throws InvalidData in case invalid data is detected
     */
    public void validateUserInput() throws InvalidData {
        // Validate line
        if (getLine() == null)
            throw new InvalidData("Line cannot be empty");

        // Validate the direction
        if (getDirection() == null)
            throw new InvalidData("Direction cannot be empty");

        // Headsign can be anything

        // Validate the stoppages (no travel time and no duplicate indices)
        Set<Integer> seenIndices = new HashSet<>();
        int prevDeparture = -1;
        boolean first = true;

        for (PatternStop s : patternStopsProperty()) {
            s.validateUserInput();

            Integer sIdx = Integer.valueOf(s.getIndex());
            int sTravelTime = s.getTravelTime();
            if (!seenIndices.add(sIdx))
                throw new InvalidData(String.format("Duplicate PatternStop index: %d", sIdx));

            if (first && sTravelTime != 0)
                throw new InvalidData("First stoppage's travel time must be zero");

            if (prevDeparture >= sTravelTime)
                throw new InvalidData(
                        String.format("Stoppage %d - travel time must be grater than previous travel time", sIdx));

            prevDeparture = sTravelTime;
        }

        // Validate the trips
        for (Trip t : tripsProperty()) {
            t.validateUserInput();
        }
    }

    /**
     * Resets all of the indices of the attached patterStops.
     * Should be called after updating the patternStops list
     * (unless the move doesn't invalidate the indices).
     */
    public void refreshIndices() {
        int i = 0;
        for (var patternStop : this.patternStopsProperty())
            patternStop.setIndex(i++);
    }

    /**
     * Generate all StopTime from all trips of this pattern.
     */
    public Stream<StopTime> getStopTimes() {
        return tripsProperty()
            .stream()
            .flatMap(t -> t.getStopTimes());
    }
}
