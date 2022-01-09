package edu.iipw.pap;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.util.StringConverter;

/**
 * TravelTimeConverter is a StringConverter
 * to convert travel times (stored as integers)
 * into human-readable MM:SS strings.
 */
public class TravelTimeConverter extends StringConverter<Integer> {
    public final static TravelTimeConverter INSTANCE = new TravelTimeConverter();
    private final static Pattern READ_PATTERN = Pattern.compile("^([0-9]+(?::))?([0-9]+)$");

    private TravelTimeConverter() {
    }

    /**
     * Converts travel time into a MM:SS string.
     * Undefined behavior if provided travel time is negative.
     */
    @Override
    public String toString(Integer object) {
        if (object == null)
            return null;
        int minutes = object.intValue() / 60;
        int seconds = object.intValue() % 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    /**
     * Parses a [MM:]SS string into travel time.
     * No restrictions on how big both minutes or seconds are, so
     * `1:120` evaluates to `180` (3 minutes).
     *
     * Returns -1 for invalid inputs.
     */
    @Override
    public Integer fromString(String string) {
        if (string == null)
            return null;

        Matcher r = READ_PATTERN.matcher(string);
        if (!r.matches())
            return -1;

        int minutes = r.group(1) != null ? Integer.parseInt(r.group(1).replaceAll(":", "")) : 0;
        int seconds = Integer.parseInt(r.group(2));

        return minutes * 60 + seconds;
    }
}
