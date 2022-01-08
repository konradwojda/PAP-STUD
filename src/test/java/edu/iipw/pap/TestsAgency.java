package edu.iipw.pap;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.iipw.pap.db.model.Agency;

public class TestsAgency {
    @Test
    void checkCounts() {
        assertEquals(1, Dataset.INSTANCE.listAll(Agency.class).size());
        assertEquals(2, Dataset.INSTANCE.agency.linesProperty().size());
    }
}
