package edu.iipw.pap.db.model;

import java.util.List;
import java.util.Set;

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
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;

@Entity
@Table(name = "patterns")
public final class Pattern {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "pattern_id")
    private IntegerProperty patternId = new SimpleIntegerProperty();

    public IntegerProperty patternIdProperty() {
        return patternId;
    }

    public int getPatternId() {
        return patternId.get();
    }

    public void setPatternId(int value) {
        patternId.set(value);
    }

    @Column(name = "headsign")
    private StringProperty headsign = new SimpleStringProperty();

    public StringProperty headsignProperty() {
        return headsign;
    }

    public String getHeadsign() {
        return headsign.get();
    }

    public void setHeadsign(String value) {
        headsign.set(value);
    }

    @Column(name = "direction")
    @Enumerated(EnumType.ORDINAL)
    private ObjectProperty<PatternDirection> direction = new SimpleObjectProperty<PatternDirection>();

    public ObjectProperty<PatternDirection> directionProperty() {
        return direction;
    }

    public PatternDirection getDirection() {
        return direction.get();
    }

    public void setDirection(PatternDirection value) {
        direction.set(value);
    }

    @ManyToOne
    @JoinColumn(name = "line_id", nullable = false)
    private ObjectProperty<Line> line = new SimpleObjectProperty<Line>();

    public ObjectProperty<Line> lineProperty() {
        return line;
    }

    public Line getLine() {
        return line.get();
    }

    public void setLine(Line value) {
        line.set(value);
    }

    @OneToMany(mappedBy = "pattern")
    private ListProperty<PatternStop> patternStops = new SimpleListProperty<PatternStop>();

    public ListProperty<PatternStop> patternStopsProperty() {
        return patternStops;
    }

    public ObservableList<PatternStop> getPatternStops() {
        return patternStops.get();
    }

    public void setPatternStops(List<PatternStop> value) {
        if (ObservableList.class.isAssignableFrom(value.getClass())) {
            patternStops.set((ObservableList<PatternStop>) value);
        } else {
            patternStops.set(FXCollections.observableList(value));
        }
    }

    @OneToMany(mappedBy = "pattern")
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
                "Pattern(%d, headsign=%s, direction=%s, line=%s, %d stops)",
                getPatternId(),
                getHeadsign(),
                getDirection(),
                getLine(),
                getPatternStops() == null ? 0 : getPatternStops().size());
    }
}
