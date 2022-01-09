package edu.iipw.pap;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.util.StringConverter;

/**
 * DepartureTimeConverter is a StringConverter
 * to convert departure times (stored as integers)
 * into a human-readable HH:MM:SS strings.
 */
public class DepartureTimeConverter extends StringConverter<Integer> {
    public final static DepartureTimeConverter INSTANCE = new DepartureTimeConverter();
    private final static Pattern READ_PATTERN = Pattern.compile("^([0-9]+):([0-9]{2})(?::([0-9]{2}))?$");

    private DepartureTimeConverter() {
    }

    /**
     * Converts a departure time into a HH:MM:SS string.
     * If seconds turn out to be zero, returns HH:MM.
     */
    @Override
    public String toString(Integer object) {
        return toString(object, false);
    }

    /**
     * Converts a departure time into a HH:MM:SS string.
     * If seconds turn out to be zero, and forceSeconds is false - returns HH:MM.
     *
     * Unspecified results if time is negative.
     */
    public String toString(Integer object, boolean forceSeconds) {
        if (object == null)
            return null;
        int minutes = object.intValue() / 60;
        int seconds = object.intValue() % 60;
        int hours = minutes / 60;
        minutes = minutes % 60;
        return seconds == 0 && !forceSeconds ? String.format("%02d:%02d", hours, minutes)
                : String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    /**
     * Parses a HH:MM[:SS] string and returns a departure.
     * Returns -1 on invalid strings.
     */
    @Override
    public Integer fromString(String string) {
        if (string == null)
            return null;

        Matcher r = READ_PATTERN.matcher(string);
        if (!r.matches())
            return -1;

        int hours = Integer.parseInt(r.group(1));
        int minutes = Integer.parseInt(r.group(2));
        int seconds = r.group(3) != null ? Integer.parseInt(r.group(3)) : 0;

        return hours * 3600 + minutes * 60 + seconds;
    }

}
