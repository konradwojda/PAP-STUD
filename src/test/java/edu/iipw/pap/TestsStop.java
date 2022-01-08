package edu.iipw.pap;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.iipw.pap.db.model.Stop;

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
}
