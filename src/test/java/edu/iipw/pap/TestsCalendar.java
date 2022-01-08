package edu.iipw.pap;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.iipw.pap.db.model.Calendar;

public class TestsCalendar {
    @Test
    void checkCounts() {
        assertEquals(2, Dataset.INSTANCE.listAll(Calendar.class).size());
        assertEquals(24, Dataset.INSTANCE.calendarWeekday.tripsProperty().size());
        assertEquals(16, Dataset.INSTANCE.calendarWeekend.tripsProperty().size());
    }
}
