package edu.iipw.pap;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

/**
 * LogFormatter is the class responsible for formatting log messages.
 * It's a simple wrapper around `java.logging.SimpleFormatter`, that
 * offloads the actual formatting there - and just inserts colors
 * depending on the record's level.
 */
public class ColoredLogger extends SimpleFormatter {
    public final static String colorSuffix = "\u001B[0m";

    public static String colorPrefix(Level logLevel) {
        if (Level.FINEST.equals(logLevel) || Level.FINER.equals(logLevel)) {
            return "\u001B[90m";
        } else if (Level.FINE.equals(logLevel) || Level.CONFIG.equals(logLevel)) {
            return "\u001B[37m";
        } else if (Level.INFO.equals(logLevel)) {
            return "\u001B[0m";
        } else if (Level.WARNING.equals(logLevel)) {
            return "\u001B[33m";
        } else if (Level.SEVERE.equals(logLevel)) {
            return "\u001B[31m";
        }
        return "";
    }

    public ColoredLogger() {
        super();
    }

    @Override
    public String format(LogRecord record) {
        String formatted = super.format(record);
        return colorPrefix(record.getLevel()) + formatted + colorSuffix;
    }
}
