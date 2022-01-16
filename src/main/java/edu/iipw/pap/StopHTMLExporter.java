package edu.iipw.pap;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;

import edu.iipw.pap.db.model.Calendar;
import edu.iipw.pap.db.model.Stop;
import edu.iipw.pap.db.model.StopTime;

public class StopHTMLExporter {

    /**
     * Escapes a string to be safely included within XML.
     * Replacements taken from CPython,
     * https://github.com/python/cpython/blob/3.10/Lib/html/__init__.py.
     */
    public static String escape(String s) {
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#x27;");
    }

    /**
     * Exports a stop timetable to a HTML file.
     * @param path Where to save the HTML
     * @param stop Stop for which to generate timetable
     * @param calendar Calendar for which to generate timetable
     * @throws IOException
     */
    public static void exportToHTML(String path, Stop stop, Calendar calendar) throws IOException {
        var stopTimes = stop.getStopTimes().filter(s -> s.getTrip().getCalendar() == calendar)
                .sorted(Comparator.comparingInt(s -> s.getDepartureTime())).toArray();
        var writer = Files.newBufferedWriter(Path.of(path), StandardCharsets.UTF_8, StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
        try {
            writer.write(
                "<html>\n" +
                "  <head>\n" +
                "    <style>\n" +
                "      table,td,th {\n" +
                "        border-style: solid;\n" +
                "        border-collapse:collapse;\n" +
                "      }\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <table>\n" +
                "      <thead>\n" +
                "        <tr><th colspan=3>" + escape(stop.getName()) + "</th></tr>\n" +
                "        <tr><th>Time</th><th>Line</th><th>Headsign</th></tr>\n" +
                "      </thead>\n");

            for (var stopTime : stopTimes) {
                assert StopTime.class.isAssignableFrom(stopTime.getClass());
                StopTime st = (StopTime) stopTime;
                String time = DepartureTimeConverter.INSTANCE.toString(st.getDepartureTime());
                String line = st.getTrip().getPattern().getLine().getCode();
                String headsign = st.getTrip().getPattern().getHeadsign();

                writer.write(
                    "      <tr>\n" +
                    "        <td>" + escape(time) + "</td>\n" +
                    "        <td>" + escape(line) + "</td>\n" +
                    "        <td>" + escape(headsign) + "</td>\n" +
                    "      </tr>\n");
            }
            writer.write(
                "    </table>\n" +
                "  </body>\n" +
                "</html>");
        } finally {
            writer.close();
        }
    }
}
