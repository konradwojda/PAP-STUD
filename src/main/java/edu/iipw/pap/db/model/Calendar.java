package edu.iipw.pap.db.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import edu.iipw.pap.db.typeConverters.BooleanConverter;
import edu.iipw.pap.db.typeConverters.LocalDateConverter;
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
import javafx.collections.ObservableSet;

@Entity
@Table(name = "calendars")
public final class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "calendar_id")
    private IntegerProperty calendarId = new SimpleIntegerProperty();

    public IntegerProperty calendarIdProperty() {
        return calendarId;
    }

    public int getCalendarId() {
        return calendarId.get();
    }

    public void setCalendarId(int value) {
        calendarId.set(value);
    }

    @Column(name = "name")
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

    @Column(name = "start_date", nullable = false)
    @Convert(converter = LocalDateConverter.class)
    private ObjectProperty<LocalDate> start = new SimpleObjectProperty<LocalDate>();

    public ObjectProperty<LocalDate> startProperty() {
        return start;
    }

    public LocalDate getStart() {
        return start.get();
    }

    public void setStart(LocalDate value) {
        start.set(value);
    }

    @Column(name = "end_date")
    @Convert(converter = LocalDateConverter.class)
    private ObjectProperty<LocalDate> end = new SimpleObjectProperty<LocalDate>();

    public ObjectProperty<LocalDate> endProperty() {
        return end;
    }

    public LocalDate getEnd() {
        return end.get();
    }

    public void setEnd(LocalDate value) {
        end.set(value);
    }

    @Column(name = "monday")
    @Convert(converter = BooleanConverter.class)
    private BooleanProperty monday = new SimpleBooleanProperty(false);

    public BooleanProperty mondayProperty() {
        return monday;
    }

    public boolean getMonday() {
        return monday.get();
    }

    public void setMonday(boolean value) {
        monday.set(value);
    }

    @Column(name = "tuesday")
    @Convert(converter = BooleanConverter.class)
    private BooleanProperty tuesday = new SimpleBooleanProperty(false);

    public BooleanProperty tuesdayProperty() {
        return tuesday;
    }

    public boolean getTuesday() {
        return tuesday.get();
    }

    public void setTuesday(boolean value) {
        tuesday.set(value);
    }

    @Column(name = "wednesday")
    @Convert(converter = BooleanConverter.class)
    private BooleanProperty wednesday = new SimpleBooleanProperty(false);

    public BooleanProperty wednesdayProperty() {
        return wednesday;
    }

    public boolean getWednesday() {
        return wednesday.get();
    }

    public void setWednesday(boolean value) {
        wednesday.set(value);
    }

    @Column(name = "thursday")
    @Convert(converter = BooleanConverter.class)
    private BooleanProperty thursday = new SimpleBooleanProperty(false);

    public BooleanProperty thursdayProperty() {
        return thursday;
    }

    public boolean getThursday() {
        return thursday.get();
    }

    public void setThursday(boolean value) {
        thursday.set(value);
    }

    @Column(name = "friday")
    @Convert(converter = BooleanConverter.class)
    private BooleanProperty friday = new SimpleBooleanProperty(false);

    public BooleanProperty fridayProperty() {
        return friday;
    }

    public boolean getFriday() {
        return friday.get();
    }

    public void setFriday(boolean value) {
        friday.set(value);
    }

    @Column(name = "saturday")
    @Convert(converter = BooleanConverter.class)
    private BooleanProperty saturday = new SimpleBooleanProperty(false);

    public BooleanProperty saturdayProperty() {
        return saturday;
    }

    public boolean getSaturday() {
        return saturday.get();
    }

    public void setSaturday(boolean value) {
        saturday.set(value);
    }

    @Column(name = "sunday")
    @Convert(converter = BooleanConverter.class)
    private BooleanProperty sunday = new SimpleBooleanProperty(false);

    public BooleanProperty sundayProperty() {
        return sunday;
    }

    public boolean getSunday() {
        return sunday.get();
    }

    public void setSunday(boolean value) {
        sunday.set(value);
    }

    @OneToMany(mappedBy = "calendar")
    private SetProperty<Trip> trips = new SimpleSetProperty<Trip>();

    public SetProperty<Trip> tripsProperty() {
        return trips;
    }

    public ObservableSet<Trip> getTrips() {
        return trips;
    }

    public void setTrips(Set<Trip> value) {
        if (ObservableSet.class.isAssignableFrom(value.getClass())) {
            trips.set((ObservableSet<Trip>) value);
        } else {
            trips.set(FXCollections.observableSet(value));
        }
    }

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
}
