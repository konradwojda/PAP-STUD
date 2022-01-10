package edu.iipw.pap;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.iipw.pap.db.model.Pattern;
import edu.iipw.pap.db.model.PatternStop;
import edu.iipw.pap.db.model.StopTime;
import edu.iipw.pap.db.model.Trip;

public class TestsStopTime {
    @Test
    void checkTripAtPatternStop() {
        Pattern p = TestsPattern.preparePattern();
        PatternStop[] ps = TestsPattern.preparePatternStops(p);
        Trip t = new Trip();
        t.setDeparture(6 * 60 * 60);
        t.setPattern(p);
        t.setCalendar(Dataset.INSTANCE.calendarWeekday);

        StopTime[] st = {
            StopTime.tripAtPatternStop(t, ps[0]),
            StopTime.tripAtPatternStop(t, ps[1]),
            StopTime.tripAtPatternStop(t, ps[2]),
        };

        // Validate st[0]
        assertEquals(st[0].getTrip(), t);
        assertEquals(st[0].getStop(), Dataset.INSTANCE.stopKabaty);
        assertEquals(st[0].getIndex(), 0);
        assertEquals(st[0].getDepartureTime(), 6 * 60 * 60);

        // Validate st[1]
        assertEquals(st[1].getTrip(), t);
        assertEquals(st[1].getStop(), Dataset.INSTANCE.stopSwietokrzyska);
        assertEquals(st[1].getIndex(), 1);
        assertEquals(st[1].getDepartureTime(), 6 * 60 * 60 + 22 * 60);

        // Validate st[2]
        assertEquals(st[2].getTrip(), t);
        assertEquals(st[2].getStop(), Dataset.INSTANCE.stopMlociny);
        assertEquals(st[2].getIndex(), 2);
        assertEquals(st[2].getDepartureTime(), 6 * 60 * 60 + 39 * 60);
    }
}
