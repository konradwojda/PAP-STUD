package edu.iipw.pap;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class TestsDepartureTimeConverter {
    @ParameterizedTest
    @CsvSource({
        "8:00:00, 28800",
        "08:00:00, 28800",
        "15:00, 54000",
        "15:00:00, 54000",
        "15:20:40, 55240",
        "17:52:31, 64351",
        "25:00:00, 90000",
        "298:00:00, 1072800",
        "0:00:00, 0",
        "10:0:0, -1",
        "10:0:00, -1",
        "10:00:0, -1"
    })
    void checkFromString(String s, int d) {
        assertEquals(d, DepartureTimeConverter.INSTANCE.fromString(s));
    }

    @ParameterizedTest
    @CsvSource({
        "08:00, 28800",
        "15:00, 54000",
        "15:20:40, 55240",
        "17:52:31, 64351",
        "25:00, 90000",
        "298:00, 1072800",
        "00:00, 0",
    })
    void checkToString(String s, int d) {
        assertEquals(s, DepartureTimeConverter.INSTANCE.toString(d));
    }

    @ParameterizedTest
    @CsvSource({
        "08:00:00, 28800",
        "15:00:00, 54000",
        "15:20:40, 55240",
        "17:52:31, 64351",
        "25:00:00, 90000",
        "298:00:00, 1072800",
        "00:00:00, 0",
    })
    void checkToStringForceSeconds(String s, int d) {
        assertEquals(s, DepartureTimeConverter.INSTANCE.toString(d, true));
    }
}
