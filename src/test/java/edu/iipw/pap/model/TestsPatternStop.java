package edu.iipw.pap.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import edu.iipw.pap.db.model.Pattern;
import edu.iipw.pap.db.model.PatternStop;
import edu.iipw.pap.db.model.Stop;
import edu.iipw.pap.exceptions.InvalidData;

public class TestsPatternStop {
   @Test
   void checkValidateUserInput() {
        Pattern p = new Pattern();
        PatternStop ps = new PatternStop();
        ps.setPattern(p);
        ps.setStop(Dataset.INSTANCE.stopKabaty);

        // Check against a valid PatternStop
        assertDoesNotThrow(() -> ps.validateUserInput());

        // Validate stop checking
        ps.setStop(null);
        assertThrows(InvalidData.class, () -> ps.validateUserInput());
        ps.setStop(Dataset.INSTANCE.stopKabaty);

        // Validate pattern checking
        ps.setPattern(null);
        assertThrows(InvalidData.class, () -> ps.validateUserInput());
        ps.setPattern(p);

        // Validate index checking
        ps.setIndex(-1);
        assertThrows(InvalidData.class, () -> ps.validateUserInput());
        ps.setIndex(0);

        // Validate travel time checking
        ps.setTravelTime(-1);
        assertThrows(InvalidData.class, () -> ps.validateUserInput());
   }

   @Test
   void checkStopTimes() {
      // Every pattern stop should have 10 stop times
      for (Stop s : Dataset.INSTANCE.listAll(Stop.class)) {
         for (PatternStop ps : s.patternStopsProperty())
            assertEquals(10, ps.getStopTimes().count());
      }
   }
}
