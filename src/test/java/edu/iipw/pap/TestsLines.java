package edu.iipw.pap;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.iipw.pap.db.model.Line;

public class TestsLines {
    @Test
    void checkCounts() {
        assertEquals(2, Dataset.INSTANCE.listAll(Line.class).size());
        assertEquals(2, Dataset.INSTANCE.lineM1.patternsProperty().size());
        assertEquals(2, Dataset.INSTANCE.lineM2.patternsProperty().size());
    }
}
