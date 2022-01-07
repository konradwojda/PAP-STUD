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

import edu.iipw.pap.exceptions.InvalidData;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

/**
 * Line is an entity grouping multiple patterns and trips
 * under a single coherent signage, organized by a single agency.
 */
@Entity
@Table(name = "lines")
public final class Line {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "line_id")
    private IntegerProperty lineId = new SimpleIntegerProperty();

    /**
     * Returns an observable property of the line's ID.
     * The ID is just a unique identifier of the line within the dataset.
     */
    public IntegerProperty lineIdProperty() {
        return lineId;
    }

    /**
     * Returns the ID of the line at the current point in time.
     * The ID is just a unique identifier of the line within the dataset.
     */
    public int getLineId() {
        return lineId.get();
    }

    /**
     * Changes the line's ID.
     */
    public void setLineId(int value) {
        lineId.set(value);
    }

    @Column(name = "code", nullable = false)
    private StringProperty code = new SimpleStringProperty();

    /**
     * Returns an observable property of the line's code.
     * Code is a short String identifying a line. Examples include:
     * `115`, `C`, `Jubilee`. The code shouldn't be empty.
     */
    public StringProperty codeProperty() {
        return code;
    }

    /**
     * Returns the code of the line at this point in time.
     * Code is a short (up to around 7 chars) String identifying a line.
     * Examples include: `115`, `C`, `白61`. The code shouldn't be empty.
     */
    public String getCode() {
        return code.get();
    }

    /**
     * Sets the route's code.
     */
    public void setCode(String value) {
        code.set(value);
    }

    @Column(name = "description")
    private StringProperty description = new SimpleStringProperty();

    /**
     * Returns an observable property onto the line's description.
     * Descriptions extends on the code. Is still should be short (up to ~64 chars),
     * and it shouldn't contain the code. Examples (corresponding the `code`
     * examples):
     * `PKP Mokry Ług - Aleksandrów`, `Chiyoda Line`, `練馬車庫-新宿西口`.
     * The description can be empty.
     */
    public StringProperty descriptionProperty() {
        return description;
    }

    /**
     * Returns line's description at this point in time.
     * Descriptions extends on the code. Is still should be short (up to ~64 chars),
     * and it shouldn't contain the code. Examples (corresponding the `code`
     * examples):
     * `PKP Mokry Ług - Aleksandrów`, `Chiyoda Line`, `練馬車庫-新宿西口`.
     * The description can be empty.
     */
    public String getDescription() {
        return description.get();
    }

    /**
     * Sets the route's description.
     */
    public void setDescription(String value) {
        description.set(value);
    }

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private ObjectProperty<LineType> type = new SimpleObjectProperty<LineType>();

    /**
     * Returns an observable property onto the line's type.
     * The LineType is an enum representing what
     * kind of vehicles are operated on the line.
     */
    public ObjectProperty<LineType> typeProperty() {
        return type;
    }

    /**
     * Returns the LineType at this point in time.
     * The LineType is an enum representing what
     * kind of vehicles are operated on the line.
     */
    public LineType getType() {
        return type.get();
    }

    /**
     * Sets the line's type.
     */
    public void setType(LineType value) {
        type.set(value);
    }

    @ManyToOne
    @JoinColumn(name = "agency_id", nullable = false)
    private ObjectProperty<Agency> agency = new SimpleObjectProperty<Agency>();

    /**
     * Returns an observable property onto the agency
     * responsible for this line.
     */
    public ObjectProperty<Agency> agencyProperty() {
        return agency;
    }

    /**
     * Returns the agency responsible for this line.
     */
    public Agency getAgency() {
        return agency.get();
    }

    /**
     * Sets the agency responsible for managing the line.
     */
    public void setAgency(Agency value) {
        agency.set(value);
    }

    @OneToMany(mappedBy = "line")
    private SetProperty<Pattern> patterns = new SimpleSetProperty<Pattern>();
    private Set<Pattern> patternsRaw;

    /**
     * Returns an observable property of the set of all patterns attached to this
     * line.
     *
     * Due to a conflict with the classical getters and Hibernate, never call
     * `patternsProperty().set` (or similar functions) - use `setPatterns()`
     * directly.
     */
    public SetProperty<Pattern> patternsProperty() {
        return patterns;
    }

    /**
     * Returns a set of all patterns attached to this line.
     *
     * This getter is only present to maintain compatibility with Hibernate -
     * use `patternsProperty()` (which implements Set<Pattern>) directly to
     * correctly
     * propagate changes to any listeners.
     */
    @Deprecated
    public Set<Pattern> getPatterns() {
        assert patterns.equals(patternsRaw) : "Illegal state - patternsProperty().set() was probably called";
        return patternsRaw;
    }

    /**
     * Swaps the underlying set of attached patterns.
     */
    public void setPatterns(Set<Pattern> value) {
        patternsRaw = value;
        patterns.set(FXCollections.observableSet(value));
    }

    /**
     * Returns a programmer-readable representation of the agency.
     */
    public String toString() {
        return String.format(
                "Line(%d, %s, description=%s, type=%s, agency=%s)",
                getLineId(),
                getCode(),
                getDescription(),
                getType(),
                getAgency());
    }

    /**
     * Checks whether the Line contains semantically correct data.
     * Individual setters don't check the field validity (to not annoy the
     * programmer)
     *
     * @throws InvalidData in case invalid data is detected
     */
    public void validateUserInput() throws InvalidData {
        // Validate the agency
        if (getAgency() == null)
            throw new InvalidData("Agency cannot be empty");

        // Validate the code
        if (getCode() == null || getCode().length() == 0)
            throw new InvalidData("Code cannot be empty");

        // Validate the line type
        if (getType() == null)
            throw new InvalidData("Type cannot be empty");
    }
}
