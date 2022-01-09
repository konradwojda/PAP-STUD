package edu.iipw.pap;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class TestsTravelTimeConverter {
    @ParameterizedTest
    @CsvSource({
        "15:00,900",
        "120:00,7200",
        "120,120",
        "120:120,7320",
        "4:20,260",
        "0:0,0",
        "00:00,0",
        "0,0",
        "foo,-1",
        "2:00:00,-1"
    })
    void checkFromString(String s, int d) {
        assertEquals(d, TravelTimeConverter.INSTANCE.fromString(s));
    }

    @ParameterizedTest
    @CsvSource({
        "15:00,900",
        "120:00,7200",
        "2:00,120",
        "122:00,7320",
        "4:20,260",
        "0:00,0",
    })
    void checkToString(String s, int d) {
        assertEquals(s, TravelTimeConverter.INSTANCE.toString(d));
    }
}
