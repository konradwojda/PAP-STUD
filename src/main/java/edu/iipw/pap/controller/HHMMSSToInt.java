package edu.iipw.pap.controller;

import javafx.util.StringConverter;

public class HHMMSSToInt extends StringConverter<Integer> {

    @Override
    public String toString(Integer object) {
        return toString(object, false);
    }

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

    @Override
    public Integer fromString(String string) {
        if (string == null)
            return null;
        String[] splitted = string.split(":", 3);
        int minutes = 0;
        int seconds = 0;
        int hours = 0;
        if (splitted.length == 3) {
            hours = Integer.parseInt(splitted[0]);
            minutes = Integer.parseInt(splitted[1]);
            seconds = Integer.parseInt(splitted[2]);
        } else if (splitted.length == 2) {
            hours = Integer.parseInt(splitted[0]);
            minutes = Integer.parseInt(splitted[1]);
        } else {
            // incorrect value
            return -1;
        }
        return hours * 3600 + minutes * 60 + seconds;
    }

}
