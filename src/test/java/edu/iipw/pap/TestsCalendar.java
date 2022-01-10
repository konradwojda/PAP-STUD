package edu.iipw.pap;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import edu.iipw.pap.db.model.Calendar;
import edu.iipw.pap.exceptions.InvalidData;

public class TestsCalendar {
    @Test
    void checkCounts() {
        assertEquals(2, Dataset.INSTANCE.listAll(Calendar.class).size());
        assertEquals(24, Dataset.INSTANCE.calendarWeekday.tripsProperty().size());
        assertEquals(16, Dataset.INSTANCE.calendarWeekend.tripsProperty().size());
    }

    @Test
    void checkValidateUserInput() {
        Calendar c = new Calendar();
        c.setStart(LocalDate.of(2022, 1, 1));
        c.setName("Soboty i Niedziele");
        c.setSaturday(true);
        c.setSunday(true);

        // Check against a valid Calendar
        assertDoesNotThrow(() -> c.validateUserInput());

        // Validate start-date-checking
        c.setStart(null);
        assertThrows(InvalidData.class, () -> c.validateUserInput());
        c.setStart(LocalDate.of(1, 1, 1));
        assertDoesNotThrow(() -> c.validateUserInput());
        c.setStart(LocalDate.of(2022, 1, 1));

        // Validate end-after-start
        c.setEnd(LocalDate.of(2022, 3, 1));
        assertDoesNotThrow(() -> c.validateUserInput());
        c.setEnd(LocalDate.of(2022, 1, 1)); // Single-day calendars are valid
        assertDoesNotThrow(() -> c.validateUserInput());
        c.setEnd(LocalDate.of(2021, 12, 31));
        assertThrows(InvalidData.class, () -> c.validateUserInput());
        c.setEnd(null);

        // Validate weekday checking
        c.setSaturday(false);
        c.setSunday(false);
        assertThrows(InvalidData.class, () -> c.validateUserInput());
    }
}
