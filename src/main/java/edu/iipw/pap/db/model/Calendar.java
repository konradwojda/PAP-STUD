package edu.iipw.pap.db.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import edu.iipw.pap.exceptions.InvalidData;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

/**
 * Calendar represents an entity holding information on the dates,
 * when a particular Trip is running. Calendars might overlap.
 */
public final class Calendar {
    private IntegerProperty calendarId = new SimpleIntegerProperty();

    /**
     * Returns an observable property of the calendars's ID.
     * The ID is just a unique identifier of the calendar within a dataset.
     */
    public IntegerProperty calendarIdProperty() {
        return calendarId;
    }

    /**
     * Returns the ID of the calendar at the current point in time.
     * The ID is just a unique identifier of the calendar within a dataset.
     */
    public int getCalendarId() {
        return calendarId.get();
    }

    /**
     * Changes the calendar's ID.
     */
    public void setCalendarId(int value) {
        calendarId.set(value);
    }

    private StringProperty name = new SimpleStringProperty();

    /**
     * Returns an observable property of the calendar's name.
     * The name should be a very short description of operating dates
     * understandable by a rider.
     */
    public StringProperty nameProperty() {
        return name;
    }

    /**
     * Returns the calendar's name at this point in time.
     * The name should be a very short description of operating dates
     * understandable by a rider.
     */
    public String getName() {
        return name.get();
    }

    /**
     * Sets the calendar name.
     */
    public void setName(String value) {
        name.set(value);
    }

    private ObjectProperty<LocalDate> start = new SimpleObjectProperty<LocalDate>();

    /**
     * Returns an observable property of the start of the calendar (inclusive).
     * The start date represents the first date when Trips might operate -
     * start date is applies before the individual days.
     *
     * A calendar is not considered valid until it has a start date,
     * which might be in the past.
     */
    public ObjectProperty<LocalDate> startProperty() {
        return start;
    }

    /**
     * Returns the start of the calendar (inclusive) at this point in time.
     * The start date represents the first date when Trips might operate -
     * start date is applies before the individual days.
     */
    public LocalDate getStart() {
        return start.get();
    }

    /**
     * Sets the calendar's start date.
     */
    public void setStart(LocalDate value) {
        start.set(value);
    }

    private ObjectProperty<LocalDate> end = new SimpleObjectProperty<LocalDate>();

    /**
     * Returns an observable property of the end of the calendar (inclusive).
     * The end date represents the last date when Trips might operate -
     * end date is applies after the individual days.
     *
     * Contrary to the start date - end date might be unknown (null).
     */
    public ObjectProperty<LocalDate> endProperty() {
        return end;
    }

    /**
     * Returns the end of the calendar (inclusive) at this point in time.
     * The end date represents the last date when Trips might operate -
     * end date is applies after the individual days.
     *
     * Contrary to the start date - end date might be unknown (null).
     */
    public LocalDate getEnd() {
        return end.get();
    }

    /**
     * Sets the calendar's end date.
     */
    public void setEnd(LocalDate value) {
        end.set(value);
    }

    private BooleanProperty monday = new SimpleBooleanProperty(false);

    /**
     * Indicates whether trips attached to this calendar are active
     * on Mondays (after applying the start-end date range).
     */
    public BooleanProperty mondayProperty() {
        return monday;
    }

    /**
     * Indicates whether trips attached to this calendar are active
     * on Mondays (after applying the start-end date range).
     */
    public boolean getMonday() {
        return monday.get();
    }

    /**
     * Decides whether trips of this calendar are active on Mondays.
     */
    public void setMonday(boolean value) {
        monday.set(value);
    }

    private BooleanProperty tuesday = new SimpleBooleanProperty(false);

    /**
     * Indicates whether trips attached to this calendar are active
     * on Tuesdays (after applying the start-end date range).
     */
    public BooleanProperty tuesdayProperty() {
        return tuesday;
    }

    /**
     * Indicates whether trips attached to this calendar are active
     * on Tuesdays (after applying the start-end date range).
     */
    public boolean getTuesday() {
        return tuesday.get();
    }

    /**
     * Decides whether trips of this calendar are active on Tuesdays.
     */
    public void setTuesday(boolean value) {
        tuesday.set(value);
    }

    private BooleanProperty wednesday = new SimpleBooleanProperty(false);

    /**
     * Indicates whether trips attached to this calendar are active
     * on Wednesdays (after applying the start-end date range).
     */
    public BooleanProperty wednesdayProperty() {
        return wednesday;
    }

    /**
     * Indicates whether trips attached to this calendar are active
     * on Wednesdays (after applying the start-end date range).
     */
    public boolean getWednesday() {
        return wednesday.get();
    }

    /**
     * Decides whether trips of this calendar are active on Wednesdays.
     */
    public void setWednesday(boolean value) {
        wednesday.set(value);
    }

    private BooleanProperty thursday = new SimpleBooleanProperty(false);

    /**
     * Indicates whether trips attached to this calendar are active
     * on Thursdays (after applying the start-end date range).
     */
    public BooleanProperty thursdayProperty() {
        return thursday;
    }

    /**
     * Indicates whether trips attached to this calendar are active
     * on Thursdays (after applying the start-end date range).
     */
    public boolean getThursday() {
        return thursday.get();
    }

    /**
     * Decides whether trips of this calendar are active on Thursdays.
     */
    public void setThursday(boolean value) {
        thursday.set(value);
    }

    private BooleanProperty friday = new SimpleBooleanProperty(false);

    /**
     * Indicates whether trips attached to this calendar are active
     * on Fridays (after applying the start-end date range).
     */
    public BooleanProperty fridayProperty() {
        return friday;
    }

    /**
     * Indicates whether trips attached to this calendar are active
     * on Fridays (after applying the start-end date range).
     */
    public boolean getFriday() {
        return friday.get();
    }

    /**
     * Decides whether trips of this calendar are active on Fridays.
     */
    public void setFriday(boolean value) {
        friday.set(value);
    }

    private BooleanProperty saturday = new SimpleBooleanProperty(false);

    /**
     * Indicates whether trips attached to this calendar are active
     * on Saturdays (after applying the start-end date range).
     */
    public BooleanProperty saturdayProperty() {
        return saturday;
    }

    /**
     * Indicates whether trips attached to this calendar are active
     * on Saturdays (after applying the start-end date range).
     */
    public boolean getSaturday() {
        return saturday.get();
    }

    /**
     * Decides whether trips of this calendar are active on Saturdays.
     */
    public void setSaturday(boolean value) {
        saturday.set(value);
    }

    private BooleanProperty sunday = new SimpleBooleanProperty(false);

    /**
     * Indicates whether trips attached to this calendar are active
     * on Sundays (after applying the start-end date range).
     */
    public BooleanProperty sundayProperty() {
        return sunday;
    }

    /**
     * Indicates whether trips attached to this calendar are active
     * on Sundays (after applying the start-end date range).
     */
    public boolean getSunday() {
        return sunday.get();
    }

    /**
     * Decides whether trips of this calendar are active on Sundays.
     */
    public void setSunday(boolean value) {
        sunday.set(value);
    }

    private SetProperty<Trip> trips = new SimpleSetProperty<Trip>();
    private Set<Trip> tripsRaw;

    /**
     * Returns an observable property of the set of all trips attached to this
     * calendar.
     *
     * Due to a conflict with the classical getters and Hibernate, never call
     * `tripsProperty().set` (or similar functions) - use `setTrips()` directly.
     */
    public SetProperty<Trip> tripsProperty() {
        if (trips.get() == null)
            setTrips(new HashSet<>());
        return trips;
    }

    /**
     * Returns a set of all trips attached to this calendar.
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
        tripsRaw = value;
        trips.set(FXCollections.observableSet(value));
    }

    /**
     * Returns a programmer-readable representation of the Calendar.
     */
    public String toString() {
        return String.format(
                "Calendar(%d, %s, start=%s, end=%s, monday=%b, tuesday=%b, wednesday=%b, thursday=%b, "
                        + "friday=%b, saturday=%b, sunday=%b)",
                getCalendarId(),
                getName(),
                getStart().format(DateTimeFormatter.ISO_LOCAL_DATE),
                getEnd() == null ? null : getEnd().format(DateTimeFormatter.ISO_LOCAL_DATE),
                getMonday(),
                getTuesday(),
                getWednesday(),
                getThursday(),
                getFriday(),
                getSaturday(),
                getSunday());
    }

    /**
     * Checks whether the Calendar contains semantically correct data.
     * Individual setters don't check the field validity (to not annoy the
     * programmer)
     *
     * @throws InvalidData in case invalid data is detected
     */
    public void validateUserInput() throws InvalidData {
        // Name - no validation (GTFS has no names)

        // Start & end date
        if (getStart() == null)
            throw new InvalidData("Start date cannot be empty");
        if (getEnd() != null && getEnd().isBefore(getStart()))
            throw new InvalidData("Calendar cannot end before it starts");

        // One of the weekdays should be on
        if (!getMonday() && !getTuesday() && !getWednesday() && !getThursday() && !getFriday() && !getSaturday()
                && !getSunday())
            throw new InvalidData("Calendar has to be active on at least one weekday");
    }
}
