package edu.iipw.pap;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import edu.iipw.pap.controller.HHMMSSToInt;
import edu.iipw.pap.db.ObjectPool;
import edu.iipw.pap.db.model.Agency;
import edu.iipw.pap.db.model.Calendar;
import edu.iipw.pap.db.model.Line;
import edu.iipw.pap.db.model.LineType;
import edu.iipw.pap.db.model.PatternDirection;
import edu.iipw.pap.db.model.Stop;
import edu.iipw.pap.db.model.StopTime;
import edu.iipw.pap.db.model.Trip;

/**
 * GTFSExporter is a class responsible for exporting an ObjectPool
 * into the GTFS format
 */
public class GTFSExporter implements AutoCloseable {
    // Helpers

    private static final Pattern cellNeedsEscape = Pattern.compile("\"|,|\r|\n");
    private static final HHMMSSToInt timeConverter = new HHMMSSToInt();

    /**
     * Coalesces an object as a String - by calling toString(),
     * unless the argument is null ("" is returned in this case).
     */
    public static String coalesceString(Object o) {
        return o == null ? "" : o.toString();
    }

    /**
     * Escapes a CSV cell - if it contains characters that need escaping.
     */
    public static String escapeCell(String cell) {
        return cellNeedsEscape.matcher(cell).find() ? "\"" + cell.replaceAll("\"", "\"\"") + "\"" : cell;
    }

    /**
     * Serializes multiple CSV cells into a single valid CSV row.
     */
    public static String serializeRow(Object... objs) {
        return serializeRow(Arrays.asList(objs));
    }

    /**
     * Serializes a list of to-be cells into a valid CSV row.
     */
    public static String serializeRow(List<Object> objs) {
        Stream<String> cellsStream = objs.stream()
                .map(o -> coalesceString(o))
                .map(s -> escapeCell(s));
        return String.join(",", (Iterable<String>) cellsStream::iterator) + "\r\n";
    }

    /**
     * Converts a LineType into the corresponding GTFS LineType.
     */
    public static String toGTFSRouteType(LineType t) {
        switch (t) {
            case TRAM:
                return "0";
            case METRO:
                return "1";
            case RAIL:
                return "2";
            case BUS:
                return "3";
            default:
                throw new RuntimeException("unhandled LineType in toGTFSRouteType");
        }
    }

    // Actual output

    private Writer writerAgency;
    private Writer writerStops;
    private Writer writerRoutes;
    private Writer writerTrips;
    private Writer writerStopTimes;
    private Writer writerCalendar;

    private Set<Stop> exportedStops = new HashSet<>();
    private Set<Calendar> exportedCalendars = new HashSet<>();

    public GTFSExporter(Writer writerAgency_, Writer writerStops_, Writer writerRoutes_, Writer writerTrips_,
            Writer writerStopTimes_, Writer writerCalendar_) {
        this.writerAgency = writerAgency_;
        this.writerStops = writerStops_;
        this.writerRoutes = writerRoutes_;
        this.writerTrips = writerTrips_;
        this.writerStopTimes = writerStopTimes_;
        this.writerCalendar = writerCalendar_;
    }

    /**
     * Closes the provided streams.
     */
    @Override
    public void close() throws IOException {
        this.writerAgency.close();
        this.writerStops.close();
        this.writerRoutes.close();
        this.writerTrips.close();
        this.writerStopTimes.close();
        this.writerCalendar.close();
    }

    /**
     * Writes CSV headers to provided streams.
     * Must be called before any `export` method,
     * cannot be called more then once per-exporter.
     *
     * @throws IOException
     */
    public void writeHeaders() throws IOException {
        this.writerAgency
                .write(serializeRow("agency_id", "agency_name", "agency_url", "agency_timezone", "agency_phone"));
        this.writerStops.write(serializeRow("stop_id", "stop_code", "stop_name", "stop_lat", "stop_lon"));
        this.writerRoutes
                .write(serializeRow("route_id", "agency_id", "route_short_name", "route_long_name", "route_type"));
        this.writerTrips.write(serializeRow("route_id", "service_id", "trip_id", "trip_headsign", "direction_id"));
        this.writerStopTimes
                .write(serializeRow("trip_id", "arrival_time", "departure_time", "stop_id", "stop_sequence"));
        this.writerCalendar.write(serializeRow("service_id", "monday", "tuesday", "wednesday", "thursday", "friday",
                "saturday", "sunday", "start_date", "end_date"));
    }

    /**
     * Blindly saves an Agency into the corresponding stream.
     *
     * @throws IOException
     */
    public void serializeAgency(Agency a) throws IOException {
        this.writerAgency.write(serializeRow(
                a.getAgencyId(),
                a.getName(),
                a.getWebsite(),
                a.getTimezone(),
                a.getTelephone()));
    }

    /**
     * Blindly saves a Stop into the corresponding stream.
     *
     * @throws IOException
     */
    public void serializeStop(Stop s) throws IOException {
        this.writerStops.write(serializeRow(
                s.getStopId(),
                s.getCode(),
                s.getName(),
                s.getLat(),
                s.getLon()));
    }

    /**
     * Blindly saves a Line into the corresponding stream.
     *
     * @throws IOException
     */
    public void serializeLine(Line l) throws IOException {
        this.writerRoutes.write(serializeRow(
                l.getLineId(),
                l.getAgency().getAgencyId(),
                l.getCode(),
                l.getDescription(),
                toGTFSRouteType(l.getType())));
    }

    /**
     * Blindly saves a Trip into the corresponding stream.
     *
     * @throws IOException
     */
    public void serializeTrip(Trip t) throws IOException {
        this.writerTrips.write(serializeRow(
                t.getPattern().getLine().getLineId(),
                t.getCalendar().getCalendarId(),
                t.getTripId(),
                t.getPattern().getHeadsign(),
                t.getPattern().getDirection() == PatternDirection.OUTBOUND ? "0" : "1"));
    }

    /**
     * Blindly saves a StopTime into the corresponding stream.
     *
     * @throws IOException
     */
    public void serializeStopTime(StopTime st) throws IOException {
        String time = timeConverter.toString(st.getDepartureTime(), true);

        this.writerStopTimes.write(serializeRow(
                st.getTrip().getTripId(),
                time,
                time,
                st.getStop().getStopId(),
                st.getIndex()));
    }

    /**
     * Blindly saves a Calendar into the corresponding stream.
     *
     * @throws IOException
     */
    public void serializeCalendar(Calendar c) throws IOException {
        String start_date = DateTimeFormatter.BASIC_ISO_DATE.format(c.getStart());
        String end_date = c.getEnd() == null ? "" : DateTimeFormatter.BASIC_ISO_DATE.format(c.getEnd());

        this.writerCalendar.write(serializeRow(
                c.getCalendarId(),
                c.getMonday() ? "1" : "0",
                c.getTuesday() ? "1" : "0",
                c.getWednesday() ? "1" : "0",
                c.getThursday() ? "1" : "0",
                c.getFriday() ? "1" : "0",
                c.getSaturday() ? "1" : "0",
                c.getSunday() ? "1" : "0",
                start_date,
                end_date));
    }

    /**
     * Exports an Agency - and recursively saves all Lines attached to the Agency.
     *
     * @throws IOException
     */
    public void exportAgency(Agency a) throws IOException {
        this.serializeAgency(a);
        for (var l : a.linesProperty())
            this.exportLine(l);
    }

    /**
     * Exports a Stop if it wasn't exported before.
     *
     * @throws IOException
     */
    public void exportStop(Stop s) throws IOException {
        if (this.exportedStops.add(s))
            this.serializeStop(s);
    }

    /**
     * Exports a Line - and recursively saves all Trips attached to the Line.
     *
     * @throws IOException
     */
    public void exportLine(Line l) throws IOException {
        this.serializeLine(l);
        for (var p : l.patternsProperty()) {
            for (var t : p.tripsProperty()) {
                this.exportTrip(t);
            }
        }
    }

    /**
     * Exports a Trip - and recursively saves all StopTimes attached to the Trip,
     * while also exporting its Calendar (if that wasn't done before)
     *
     * @throws IOException
     */
    public void exportTrip(Trip t) throws IOException {
        this.serializeTrip(t);
        this.exportCalendar(t.getCalendar());
        for (StopTime st : t.getStopTimes().collect(Collectors.toList()))
            this.exportStopTime(st);
    }

    /**
     * Exports a StopTime - while also exporting its Stop (if that wasn't done
     * before)
     *
     * @throws IOException
     */
    public void exportStopTime(StopTime st) throws IOException {
        this.serializeStopTime(st);
        this.exportStop(st.getStop());
    }

    /**
     * Exports a Calendar if that wasn't done before.
     *
     * @throws IOException
     */
    public void exportCalendar(Calendar c) throws IOException {
        if (this.exportedCalendars.add(c))
            this.serializeCalendar(c);
    }

    /**
     * Exports (almost) every object from the provided ObjectPool.
     * Only elements that can be skipped are:
     * - Stops without any StopTimes,
     * - Calendars without any Trips.
     *
     * @throws IOException
     */
    public void exportPool(ObjectPool pool) throws IOException {
        for (var a : pool.listAll(Agency.class))
            this.exportAgency(a);
    }

    /**
     * Describes a function that might throw an IOException
     */
    @FunctionalInterface
    private interface FunctionThrowingIO<T, R> {
        R apply(T t) throws IOException;
    }

    /**
     * Exports the provided ObjectPool (see exportPool) into GTFS in a temporary
     * directory.
     *
     * @return Created temporary directory
     * @throws IOException
     */
    public static Path exportToTempDir(ObjectPool pool) throws IOException {
        Path tempDir = Files.createTempDirectory("rozkladonator4000-");
        FunctionThrowingIO<String, Writer> opener = fname -> Files.newBufferedWriter(tempDir.resolve("agency.txt"),
                StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);

        GTFSExporter exporter = new GTFSExporter(
                opener.apply("agency.txt"),
                opener.apply("stops.txt"),
                opener.apply("routes.txt"),
                opener.apply("trips.txt"),
                opener.apply("stop_times.txt"),
                opener.apply("calendar.txt"));
        try {
            exporter.writeHeaders();
            exporter.exportPool(pool);
        } finally {
            exporter.close();
        }

        return tempDir;
    }

    /**
     * Exports the provided ObjectPool (see exportPool) into a ZIP archive
     * at the specified Path.
     *
     * @throws IOException
     */
    public static void exportToZip(String target, ObjectPool pool) throws IOException {
        final String[] filesToZip = { "agency.txt", "stops.txt", "routes.txt", "trips.txt", "stop_times.txt",
                "calendar.txt" };
        byte[] buffer = new byte[4098];
        int count;

        // First, export to temp dir
        Path tempDir = exportToTempDir(pool);

        try (ZipOutputStream arch = new ZipOutputStream(new FileOutputStream(target))) {
            for (String file : filesToZip) {
                FileInputStream in = new FileInputStream(tempDir.resolve(file).toString());
                arch.putNextEntry(new ZipEntry(file));

                while ((count = in.read(buffer)) > 0) {
                    arch.write(buffer, 0, count);
                }

                in.close();
                Files.delete(tempDir.resolve(file));
            }
        }

        Files.delete(tempDir);
    }

}
