package edu.iipw.pap.db.model;

import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

import edu.iipw.pap.exceptions.InvalidData;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

/**
 * Agency represents an entity responsible for managing
 * (i.e. setting up schedules, contracting out the actual buses)
 * a set of public transportation lines.
 */
public final class Agency {
    private IntegerProperty agencyId = new SimpleIntegerProperty();

    /**
     * Returns an observable property of the agency's ID.
     * The ID is just a unique identifier of the agency within a dataset.
     */
    public IntegerProperty agencyIdProperty() {
        return agencyId;
    }

    /**
     * Returns the ID of the agency at the current point in time.
     * The ID is just a unique identifier of the agency within a dataset.
     */
    public int getAgencyId() {
        return agencyId.get();
    }

    /**
     * Changes the agency's ID.
     */
    public void setAgencyId(int value) {
        agencyId.set(value);
    }

    private StringProperty name = new SimpleStringProperty();

    /**
     * Returns an observable property of the agency's name.
     * The name is a piece of text by which a rider can uniquely identify the
     * agency.
     */
    public StringProperty nameProperty() {
        return name;
    }

    /**
     * Returns the agency's name at this point in time.
     * The name is a piece of text by which a rider can uniquely identify the
     * agency.
     */
    public String getName() {
        return name.get();
    }

    /**
     * Changes the agency's name.
     */
    public void setName(String value) {
        name.set(value);
    }

    private StringProperty website = new SimpleStringProperty();

    /**
     * Returns an observable property of the link to the agency's website.
     * `website` is a String that should contain a URL pointing to a website
     * where riders can find information about operated lines (e.g. fares, maps,
     * schedules).
     */
    public StringProperty websiteProperty() {
        return website;
    }

    /**
     * Returns the agency's website at this point in time.
     * `website` is a String that should contain a URL pointing to a website
     * where riders can find information about operated lines (e.g. fares, maps,
     * schedules).
     */
    public String getWebsite() {
        return website.get();
    }

    /**
     * Sets the agency's website.
     */
    public void setWebsite(String value) {
        website.set(value);
    }

    private StringProperty timezone = new SimpleStringProperty();

    /**
     * Returns an observable property of the agency's timezone.
     * `timezone` is a String containing the name of a timezone from the tz
     * database.
     */
    public StringProperty timezoneProperty() {
        return timezone;
    }

    /**
     * Returns the agency's timezone at this point in time.
     * `timezone` is a String containing the name of a timezone from the tz
     * database.
     */
    public String getTimezone() {
        return timezone.get();
    }

    /**
     * Sets the agency's timezone.
     */
    public void setTimezone(String value) {
        timezone.set(value);
    }

    private StringProperty telephone = new SimpleStringProperty();

    /**
     * Returns an observable property at the agency's phone number
     * `telephone` is a String containing in a format understandable by the riders.
     */
    public StringProperty telephoneProperty() {
        return telephone;
    }

    /**
     * Returns the agency's telephone number at this point in time.
     * `telephone` is a String containing in a format understandable by the riders.
     */
    public String getTelephone() {
        return telephone.get();
    }

    /**
     * Sets the agency's phone number.
     */
    public void setTelephone(String value) {
        telephone.set(value);
    }

    private SetProperty<Line> lines = new SimpleSetProperty<Line>();
    private Set<Line> linesRaw;

    /**
     * Returns an observable property of the set of all lines controlled by this
     * agency.
     *
     * Due to a conflict with the classical getters and Hibernate, never call
     * `linesProperty().set` (or similar functions) - use `setLines()` directly.
     */
    public SetProperty<Line> linesProperty() {
        if (lines.get() == null)
            setLines(new HashSet<>());
        return lines;
    }

    /**
     * Returns a set of all lines controlled by this agency.
     *
     * This getter is only present to maintain compatibility with Hibernate -
     * use `linesProperty()` (which implements Set<Line>) directly to correctly
     * propagate changes to any listeners.
     */
    @Deprecated
    public Set<Line> getLines() {
        assert lines.equals(linesRaw) : "Illegal state - linesProperty().set() was probably called";
        return linesRaw;
    }

    /**
     * Swaps the underlying set of controlled lines to a different set.
     */
    public void setLines(Set<Line> value) {
        linesRaw = value;
        lines.set(FXCollections.observableSet(value));
    }

    /**
     * Returns a programmer-readable representation of the agency.
     */
    public String toString() {
        return String.format("Agency(%d, %s)", getAgencyId(), getName());
    }

    /**
     * Checks whether the Agency contains semantically correct data.
     * Individual setters don't check the field validity (to not annoy the
     * programmer)
     *
     * @throws InvalidData in case invalid data is detected
     */
    public void validateUserInput() throws InvalidData {
        // Validate the name
        if (getName() == null || getName().length() == 0)
            throw new InvalidData("Name cannot be empty");

        // Validate the website
        if (getWebsite() == null || getWebsite().length() == 0)
            throw new InvalidData("Website cannot be empty");
        if (!getWebsite().startsWith("http://") && !getWebsite().startsWith("https://"))
            throw new InvalidData("Website should start with http:// or https://");

        // Validate the timezone
        if (!ZoneId.getAvailableZoneIds().contains(getTimezone()))
            throw new InvalidData("Timezone has to be a valid IANA timezone name");

        // NOTE: No validation on the phone number - maybe we could use Google's
        // libphonenumber?
    }
}
