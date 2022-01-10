package edu.iipw.pap;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import edu.iipw.pap.db.model.Agency;
import edu.iipw.pap.exceptions.InvalidData;

public class TestsAgency {
    @Test
    void checkCounts() {
        assertEquals(1, Dataset.INSTANCE.listAll(Agency.class).size());
        assertEquals(2, Dataset.INSTANCE.agency.linesProperty().size());
    }

    @Test
    void checkValidateUserInput() {
        Agency a = new Agency();
        a.setName("Metro Warszawskie");
        a.setWebsite("https://metro.waw.pl");
        a.setTimezone("Europe/Warsaw");
        a.setTelephone("19 115");

        // Check a valid Agency
        assertDoesNotThrow(() -> a.validateUserInput());

        // Validate name-checking
        a.setName(null);
        assertThrows(InvalidData.class, () -> a.validateUserInput());
        a.setName("");
        assertThrows(InvalidData.class, () -> a.validateUserInput());
        a.setName("Metro Warszawskie");

        // Validate website-checking
        a.setWebsite(null);
        assertThrows(InvalidData.class, () -> a.validateUserInput());
        a.setWebsite("");
        assertThrows(InvalidData.class, () -> a.validateUserInput());
        a.setWebsite("ftp://example.com");
        assertThrows(InvalidData.class, () -> a.validateUserInput());
        a.setWebsite("https://metro.waw.pl");

        // Validate timezone-checking
        a.setTimezone(null);
        assertThrows(InvalidData.class, () -> a.validateUserInput());
        a.setTimezone("");
        assertThrows(InvalidData.class, () -> a.validateUserInput());
        a.setTimezone("America/SanEscobar");
        assertThrows(InvalidData.class, () -> a.validateUserInput());
        a.setTimezone("UTC");
        assertDoesNotThrow(() -> a.validateUserInput());
        a.setTimezone("Europe/Warsaw");
    }
}
