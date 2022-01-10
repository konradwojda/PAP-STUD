package edu.iipw.pap;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import edu.iipw.pap.db.model.Stop;
import edu.iipw.pap.db.model.StopTime;
import edu.iipw.pap.db.model.WheelchairAccessibility;
import edu.iipw.pap.exceptions.InvalidData;

public class TestsStop {
    @Test
    void checkCounts() {
        assertEquals(5, Dataset.INSTANCE.listAll(Stop.class).size());
        assertEquals(2, Dataset.INSTANCE.stopKabaty.patternStopsProperty().size());
        assertEquals(4, Dataset.INSTANCE.stopSwietokrzyska.patternStopsProperty().size());
        assertEquals(2, Dataset.INSTANCE.stopMlociny.patternStopsProperty().size());
        assertEquals(2, Dataset.INSTANCE.stopKsieciaJanusza.patternStopsProperty().size());
        assertEquals(2, Dataset.INSTANCE.stopTrocka.patternStopsProperty().size());
    }

    @Test
    void checkValidateUserInput() {
        Stop s = new Stop();
        s.setName("Kabaty");
        s.setCode("A1");
        s.setLat(52.132);
        s.setLon(21.065);
        s.setWheelchairAccessible(WheelchairAccessibility.ACCESSIBLE);

        // Check against a valid stop
        assertDoesNotThrow(() -> s.validateUserInput());

        // Validate name checking
        s.setName(null);
        assertThrows(InvalidData.class, () -> s.validateUserInput());
        s.setName("");
        assertThrows(InvalidData.class, () -> s.validateUserInput());
        s.setName("Kabaty");

        // Validate latitude checking
        s.setLat(-100.0);
        assertThrows(InvalidData.class, () -> s.validateUserInput());
        s.setLat(100.0);
        assertThrows(InvalidData.class, () -> s.validateUserInput());
        s.setLat(52.132);

        // Validate longitude checking
        s.setLon(-190.0);
        assertThrows(InvalidData.class, () -> s.validateUserInput());
        s.setLon(190.0);
        assertThrows(InvalidData.class, () -> s.validateUserInput());
        s.setLon(-120.0);
        assertDoesNotThrow(() -> s.validateUserInput());
        s.setLon(120.0);
        assertDoesNotThrow(() -> s.validateUserInput());
        s.setLon(21.065);

        // Validate wheelchair-accessibility checking
        s.setWheelchairAccessible(null);
        assertThrows(InvalidData.class, () -> s.validateUserInput());
    }

    @Test
    void checkStopTimes() {
        // Test Świętokrzyska - both M1 and M2 trips
        List<StopTime> s = Dataset.INSTANCE.stopSwietokrzyska.getStopTimes().collect(Collectors.toList());
        assertEquals(40, s.size());
        for (StopTime st : s)
            assertEquals(st.getStop(), Dataset.INSTANCE.stopSwietokrzyska);

        // Test Trocka - only M2 trips
        s = Dataset.INSTANCE.stopTrocka.getStopTimes().collect(Collectors.toList());
        assertEquals(20, s.size());
        for (StopTime st : s) {
            assertEquals(st.getStop(), Dataset.INSTANCE.stopTrocka);
            assertEquals(st.getTrip().getPattern().getLine(), Dataset.INSTANCE.lineM2);
        }
    }
}
