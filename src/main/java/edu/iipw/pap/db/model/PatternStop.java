package edu.iipw.pap.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "pattern_stops")
public final class PatternStop {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "pattern_stop_id")
    private IntegerProperty patternStopId = new SimpleIntegerProperty();

    public IntegerProperty patternStopIdProperty() {
        return patternStopId;
    }

    public int getPatternStopId() {
        return patternStopId.get();
    }

    public void setPatternStopId(int value) {
        patternStopId.set(value);
    }

    @ManyToOne
    @JoinColumn(name = "stop_id", nullable = false)
    private ObjectProperty<Stop> stop = new SimpleObjectProperty<Stop>();

    public ObjectProperty<Stop> stopProperty() {
        return stop;
    }

    public Stop getStop() {
        return stop.get();
    }

    public void setStop(Stop value) {
        stop.set(value);
    }

    @ManyToOne
    @JoinColumn(name = "pattern_id", nullable = false)
    private ObjectProperty<Pattern> pattern = new SimpleObjectProperty<Pattern>();

    public ObjectProperty<Pattern> patternProperty() {
        return pattern;
    }

    public Pattern getPattern() {
        return pattern.get();
    }

    public void setPattern(Pattern value) {
        pattern.set(value);
    }

    @Column(name = "idx")
    private IntegerProperty index = new SimpleIntegerProperty();

    public IntegerProperty indexProperty() {
        return index;
    }

    public int getIndex() {
        return index.get();
    }

    public void setIndex(int value) {
        index.set(value);
    }

    @Column(name = "travel_time")
    private IntegerProperty travelTime = new SimpleIntegerProperty();

    public IntegerProperty travelTimeProperty() {
        return travelTime;
    }

    public int getTravelTime() {
        return travelTime.get();
    }

    public void setTravelTime(int value) {
        travelTime.set(value);
    }

    public String toString() {
        return String.format(
                "PatternStop(%d, index=%d, travel_time=%d, stop=%s, pattern=%s)",
                getPatternStopId(),
                getIndex(),
                getTravelTime(),
                getStop(),
                getPattern());
    }
}
