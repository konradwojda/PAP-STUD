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
    public static void exportToHTML(String path, Stop stop, Calendar calendar) throws IOException {
        var stopTimes = stop.getStopTimes().filter(s -> s.getTrip().getCalendar() == calendar)
                .sorted(Comparator.comparingInt(s -> s.getDepartureTime())).toArray();
        var writer = Files.newBufferedWriter(Path.of(path), StandardCharsets.UTF_8, StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
        try {
            String beginning = "<html>\n<head>\n<style>\ntable,td,th {\n border-style:solid;\nborder-collapse:collapse;\n}\n</style>\n</head>\n<body>\n<table>\n";
            writer.write(beginning);
            ;
            writer.write("<thead>\n<tr>\n<th colspan=\"3\">" + stop.getName()
                    + "</th>\n</tr><tr>\n<th>Time</th>\n<th>Line</th>\n<th>Headsign</th>\n</tr>\n</thead>\n");
            for (var stopTime : stopTimes) {
                assert StopTime.class.isAssignableFrom(stopTime.getClass());
                StopTime st = (StopTime) stopTime;
                writer.write(String.format("<tr>\n<td>%s</td>\n<td>%s</td>\n<td>%s</td>\n</tr>\n",
                        DepartureTimeConverter.INSTANCE.toString(st.getDepartureTime()),
                        st.getTrip().getPattern().getLine().getCode(), st.getTrip().getPattern().getHeadsign()));
            }
            writer.write("</table>\n</body>\n</html>");
        } finally {
            writer.close();
        }
    }
}
