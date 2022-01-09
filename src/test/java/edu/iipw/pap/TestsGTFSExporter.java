package edu.iipw.pap;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringWriter;
import java.util.function.Function;
import java.util.function.IntPredicate;

import org.junit.jupiter.api.Test;

import edu.iipw.pap.model.Dataset;

public class TestsGTFSExporter {
    private static IntPredicate isNewline = c -> c == '\n';

    @Test
    void checkCoalesceString() {
        assertEquals("", GTFSExporter.coalesceString(null));
        assertEquals("Foo", GTFSExporter.coalesceString("Foo"));
        assertEquals("5", GTFSExporter.coalesceString(5));
        assertEquals("3.14", GTFSExporter.coalesceString(3.14));
    }

    @Test
    void checkEscapeCell() {
        assertEquals("", GTFSExporter.escapeCell(""));
        assertEquals("Foo", GTFSExporter.escapeCell("Foo"));
        assertEquals("5", GTFSExporter.escapeCell("5"));
        assertEquals("\"Hello, \"\"World\"\"\"", GTFSExporter.escapeCell("Hello, \"World\""));
    }

    @Test
    void checkExport() throws IOException {
        // Prepare buffers
        StringWriter writerAgency = new StringWriter();
        StringWriter writerStops = new StringWriter();
        StringWriter writerRoutes = new StringWriter();
        StringWriter writerTrips = new StringWriter();
        StringWriter writerStopTimes = new StringWriter();
        StringWriter writerCalendar = new StringWriter();

        // Export to GTFS
        GTFSExporter exporter = new GTFSExporter(
                writerAgency,
                writerStops,
                writerRoutes,
                writerTrips,
                writerStopTimes,
                writerCalendar);

        try {
            exporter.writeHeaders();
            exporter.exportPool(Dataset.INSTANCE);
        } finally {
            exporter.close();
        }

        Function<StringWriter, Long> countRows = sw -> sw.toString().chars().filter(isNewline).count() - 1;
        assertEquals(1, countRows.apply(writerAgency));
        assertEquals(5, countRows.apply(writerStops));
        assertEquals(2, countRows.apply(writerRoutes));
        assertEquals(40, countRows.apply(writerTrips));
        assertEquals(120, countRows.apply(writerStopTimes));
        assertEquals(2, countRows.apply(writerCalendar));
    }
}
