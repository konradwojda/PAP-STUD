package edu.iipw.pap.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import edu.iipw.pap.db.model.Line;
import edu.iipw.pap.db.model.LineType;
import edu.iipw.pap.db.model.Pattern;
import edu.iipw.pap.exceptions.InvalidData;
import javafx.collections.FXCollections;

public class TestsLines {
    @Test
    void checkCounts() {
        assertEquals(2, Dataset.INSTANCE.listAll(Line.class).size());
        assertEquals(2, Dataset.INSTANCE.lineM1.patternsProperty().size());
        assertEquals(2, Dataset.INSTANCE.lineM2.patternsProperty().size());
    }

    @Test
    void checkValidateUserInput() {
        Line l = new Line();
        l.setCode("M1");
        l.setDescription("Kabaty - Młociny");
        l.setType(LineType.METRO);
        l.setAgency(Dataset.INSTANCE.agency);

        // Check against a valid Line
        assertDoesNotThrow(() -> l.validateUserInput());

        // Validate agency-checking
        l.setAgency(null);
        assertThrows(InvalidData.class, () -> l.validateUserInput());
        l.setAgency(Dataset.INSTANCE.agency);

        // Validate code-checking
        l.setCode(null);
        assertThrows(InvalidData.class, () -> l.validateUserInput());
        l.setCode("");
        assertThrows(InvalidData.class, () -> l.validateUserInput());
        l.setCode("M1");

        // Validate type-checking
        l.setType(null);
        assertThrows(InvalidData.class, () -> l.validateUserInput());
        l.setType(LineType.METRO);

        // Validate recursion into patterns
        l.setPatterns(FXCollections.observableSet(new Pattern()));
        assertThrows(InvalidData.class, () -> l.validateUserInput());
    }
}
