package edu.iipw.pap.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import edu.iipw.pap.db.model.Line;
import edu.iipw.pap.db.model.Pattern;
import edu.iipw.pap.db.model.Trip;
import edu.iipw.pap.exceptions.InvalidData;

public class TestsTrip {
    @Test
    void checkValidateUserInput() {
        Pattern p = new Pattern();
        Trip t = new Trip();
        t.setDeparture(21600);
        t.setPattern(p);
        t.setCalendar(Dataset.INSTANCE.calendarWeekday);

        // Check against a valid Trip
        assertDoesNotThrow(() -> t.validateUserInput());

        // Validate departure checking
        t.setDeparture(-1);
        assertThrows(InvalidData.class, () -> t.validateUserInput());
        t.setDeparture(21600);

        // Validate pattern checking
        t.setPattern(null);
        assertThrows(InvalidData.class, () -> t.validateUserInput());
        t.setPattern(p);

        // Validate calendar checking
        t.setCalendar(null);
        assertThrows(InvalidData.class, () -> t.validateUserInput());
        t.setCalendar(Dataset.INSTANCE.calendarWeekday);

        // Validate wheelchair-accessibility checking
        t.setWheelchairAccessible(null);
        assertThrows(InvalidData.class, () -> t.validateUserInput());
    }

    @Test
    void checkStopTimes() {
        // Every trip should have precisely 3 stop times
        for (Line l : Dataset.INSTANCE.listAll(Line.class)) {
            for (Pattern p : l.patternsProperty()) {
                for (Trip t : p.tripsProperty()) {
                    assertEquals(3, t.getStopTimes().count());
                }
            }
        }
    }
}
