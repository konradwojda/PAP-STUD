package edu.iipw.pap.db.model;

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
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

@Entity
@Table(name = "lines")
public final class Line {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "line_id")
    private IntegerProperty lineId = new SimpleIntegerProperty();

    public IntegerProperty lineIdProperty() {
        return lineId;
    }

    public int getLineId() {
        return lineId.get();
    }

    public void setLineId(int value) {
        lineId.set(value);
    }

    @Column(name = "code", nullable = false)
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

    @Column(name = "description")
    private StringProperty description = new SimpleStringProperty();

    public StringProperty descriptionProperty() {
        return description;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String value) {
        description.set(value);
    }

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private ObjectProperty<LineType> type = new SimpleObjectProperty<LineType>();

    public ObjectProperty<LineType> typeProperty() {
        return type;
    }

    public LineType getType() {
        return type.get();
    }

    public void setType(LineType value) {
        type.set(value);
    }

    @ManyToOne
    @JoinColumn(name = "agency_id", nullable = false)
    private ObjectProperty<Agency> agency = new SimpleObjectProperty<Agency>();

    public ObjectProperty<Agency> agencyProperty() {
        return agency;
    }

    public Agency getAgency() {
        return agency.get();
    }

    public void setAgency(Agency value) {
        agency.set(value);
    }

    @OneToMany(mappedBy = "line")
    private SetProperty<Pattern> patterns = new SimpleSetProperty<Pattern>();

    public SetProperty<Pattern> patternsProperty() {
        return patterns;
    }

    public ObservableSet<Pattern> getPatterns() {
        return patterns.get();
    }

    public void setPatterns(Set<Pattern> value) {
        if (ObservableSet.class.isAssignableFrom(value.getClass())) {
            patterns.set((ObservableSet<Pattern>) value);
        } else {
            patterns.set(FXCollections.observableSet(value));
        }
    }

    public String toString() {
        return String.format(
                "Line(%d, %s, description=%s, type=%s, agency=%s)",
                getLineId(),
                getCode(),
                getDescription(),
                getType(),
                getAgency());
    }
}
