package edu.iipw.pap.model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

import edu.iipw.pap.db.ObjectPool;
import edu.iipw.pap.db.model.Agency;
import edu.iipw.pap.db.model.Calendar;
import edu.iipw.pap.db.model.Stop;
import edu.iipw.pap.db.model.Trip;
import edu.iipw.pap.db.model.WheelchairAccessibility;
import edu.iipw.pap.db.model.Line;
import edu.iipw.pap.db.model.LineType;
import edu.iipw.pap.db.model.Pattern;
import edu.iipw.pap.db.model.PatternDirection;
import edu.iipw.pap.db.model.PatternStop;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

// cSpell: words swietokrzyska mlociny ksiecia

public final class Dataset extends ObjectPool {
    private ObservableSet<Agency> agencies;
    private ObservableSet<Calendar> calendars;
    private ObservableSet<Stop> stops;
    private ObservableSet<Line> lines;

    public Agency agency;
    public Calendar calendarWeekday;
    public Calendar calendarWeekend;
    public Stop stopKabaty;
    public Stop stopSwietokrzyska;
    public Stop stopMlociny;
    public Stop stopKsieciaJanusza;
    public Stop stopTrocka;
    public Line lineM1;
    public Line lineM2;

    final public static Dataset INSTANCE = new Dataset();
    private int tripIdEnum = 1;

    private Dataset() {
        this.setAgencies();
        this.setCalendars();
        this.setStops();
        this.setLines();
    }

    private void setAgencies() {
        this.agency = new Agency();
        this.agency.setAgencyId(1);
        this.agency.setName("Metro Warszawskie");
        this.agency.setWebsite("https://metro.waw.pl");
        this.agency.setTimezone("Europe/Warsaw");

        this.agencies = FXCollections.observableSet(this.agency);
    }

    private void setCalendars() {
        this.calendarWeekday = new Calendar();
        this.calendarWeekday.setCalendarId(1);
        this.calendarWeekday.setStart(LocalDate.of(2022, 1, 1));
        this.calendarWeekday.setName("Robocze");
        this.calendarWeekday.setMonday(true);
        this.calendarWeekday.setTuesday(true);
        this.calendarWeekday.setWednesday(true);
        this.calendarWeekday.setThursday(true);
        this.calendarWeekday.setFriday(true);
        this.calendarWeekday.setTrips(FXCollections.observableSet());

        this.calendarWeekend = new Calendar();
        this.calendarWeekend.setCalendarId(2);
        this.calendarWeekend.setStart(LocalDate.of(2022, 1, 1));
        this.calendarWeekend.setName("Soboty i Niedziele");
        this.calendarWeekend.setSaturday(true);
        this.calendarWeekend.setSunday(true);
        this.calendarWeekend.setTrips(FXCollections.observableSet());

        this.calendars = FXCollections.observableSet(this.calendarWeekday, this.calendarWeekend);
    }

    private void setStops() {
        this.stopKabaty = new Stop();
        this.stopKabaty.setStopId(1);
        this.stopKabaty.setName("Kabaty");
        this.stopKabaty.setCode("A1");
        this.stopKabaty.setLat(52.132);
        this.stopKabaty.setLon(21.065);
        this.stopKabaty.setWheelchairAccessible(WheelchairAccessibility.ACCESSIBLE);
        this.stopKabaty.setPatternStops(FXCollections.observableSet());

        this.stopSwietokrzyska = new Stop();
        this.stopSwietokrzyska.setStopId(2);
        this.stopSwietokrzyska.setName("Świętokrzyska");
        this.stopSwietokrzyska.setCode("A14/C11");
        this.stopSwietokrzyska.setLat(52.235);
        this.stopSwietokrzyska.setLon(21.008);
        this.stopSwietokrzyska.setWheelchairAccessible(WheelchairAccessibility.UNKNOWN);
        this.stopSwietokrzyska.setPatternStops(FXCollections.observableSet());

        this.stopMlociny = new Stop();
        this.stopMlociny.setStopId(3);
        this.stopMlociny.setName("Młociny");
        this.stopMlociny.setCode("A23");
        this.stopMlociny.setLat(52.291);
        this.stopMlociny.setLon(20.93);
        this.stopMlociny.setWheelchairAccessible(WheelchairAccessibility.ACCESSIBLE);
        this.stopMlociny.setPatternStops(FXCollections.observableSet());

        this.stopTrocka = new Stop();
        this.stopTrocka.setStopId(4);
        this.stopTrocka.setName("Trocka");
        this.stopTrocka.setCode("C18");
        this.stopTrocka.setLat(52.2754);
        this.stopTrocka.setLon(21.0554);
        this.stopTrocka.setWheelchairAccessible(WheelchairAccessibility.INACCESSIBLE);
        this.stopTrocka.setPatternStops(FXCollections.observableSet());

        this.stopKsieciaJanusza = new Stop();
        this.stopKsieciaJanusza.setStopId(5);
        this.stopKsieciaJanusza.setName("Księcia Janusza");
        this.stopKsieciaJanusza.setCode("C6");
        this.stopKsieciaJanusza.setLat(52.2391);
        this.stopKsieciaJanusza.setLon(20.9441);
        this.stopKsieciaJanusza.setWheelchairAccessible(WheelchairAccessibility.INACCESSIBLE);
        this.stopKsieciaJanusza.setPatternStops(FXCollections.observableSet());

        this.stops = FXCollections.observableSet(
                this.stopKabaty,
                this.stopSwietokrzyska,
                this.stopMlociny,
                this.stopTrocka,
                this.stopKsieciaJanusza);
    }

    private void setLines() {
        this.lineM1 = new Line();
        this.lineM1.setLineId(1);
        this.lineM1.setCode("M1");
        this.lineM1.setDescription("Kabaty - Młociny");
        this.lineM1.setType(LineType.METRO);
        this.lineM1.setAgency(this.agency);
        this.setM1Patterns();

        this.lineM2 = new Line();
        this.lineM2.setLineId(1);
        this.lineM2.setCode("M2");
        this.lineM2.setDescription("Księcia Janusza - Trocka");
        this.lineM2.setType(LineType.METRO);
        this.lineM2.setAgency(this.agency);
        this.setM2Patterns();

        this.agency.setLines(FXCollections.observableSet(this.lineM1, this.lineM2));
        this.lines = FXCollections.observableSet(this.lineM1, this.lineM2);
    }

    private void setM1Patterns() {
        // Outbound
        Pattern p1 = new Pattern();
        p1.setPatternId(1);
        p1.setHeadsign("Młociny");
        p1.setDirection(PatternDirection.OUTBOUND);
        p1.setLine(this.lineM1);

        PatternStop[] ps1 = { new PatternStop(), new PatternStop(), new PatternStop() };
        ps1[0].setPatternStopId(1);
        ps1[0].setPattern(p1);
        ps1[0].setStop(this.stopKabaty);
        ps1[0].setIndex(0);
        ps1[0].setTravelTime(0);

        ps1[1].setPatternStopId(2);
        ps1[1].setPattern(p1);
        ps1[1].setStop(this.stopSwietokrzyska);
        ps1[1].setIndex(1);
        ps1[1].setTravelTime(1320);

        ps1[2].setPatternStopId(2);
        ps1[2].setPattern(p1);
        ps1[2].setStop(this.stopMlociny);
        ps1[2].setIndex(2);
        ps1[2].setTravelTime(2340);

        p1.setPatternStops(Arrays.asList(ps1));
        this.stopKabaty.patternStopsProperty().add(ps1[0]);
        this.stopSwietokrzyska.patternStopsProperty().add(ps1[1]);
        this.stopMlociny.patternStopsProperty().add(ps1[2]);

        // Inbound
        Pattern p2 = new Pattern();
        p2.setPatternId(2);
        p2.setHeadsign("Kabaty");
        p2.setDirection(PatternDirection.INBOUND);
        p2.setLine(this.lineM1);

        PatternStop[] ps2 = { new PatternStop(), new PatternStop(), new PatternStop() };
        ps2[0].setPatternStopId(3);
        ps2[0].setPattern(p2);
        ps2[0].setStop(this.stopMlociny);
        ps2[0].setIndex(0);
        ps2[0].setTravelTime(0);

        ps2[1].setPatternStopId(4);
        ps2[1].setPattern(p2);
        ps2[1].setStop(this.stopSwietokrzyska);
        ps2[1].setIndex(1);
        ps2[1].setTravelTime(1020);

        ps2[2].setPatternStopId(5);
        ps2[2].setPattern(p2);
        ps2[2].setStop(this.stopKabaty);
        ps2[2].setIndex(2);
        ps2[2].setTravelTime(2340);

        p2.setPatternStops(Arrays.asList(ps2));
        this.stopMlociny.patternStopsProperty().add(ps2[0]);
        this.stopSwietokrzyska.patternStopsProperty().add(ps2[1]);
        this.stopKabaty.patternStopsProperty().add(ps2[2]);

        this.addTrips(p1);
        this.addTrips(p2);
        this.lineM1.setPatterns(FXCollections.observableSet(p1, p2));
    }

    private void setM2Patterns() {
        // Outbound
        Pattern p1 = new Pattern();
        p1.setPatternId(3);
        p1.setHeadsign("Trocka");
        p1.setDirection(PatternDirection.OUTBOUND);
        p1.setLine(this.lineM2);

        PatternStop[] ps1 = { new PatternStop(), new PatternStop(), new PatternStop() };
        ps1[0].setPatternStopId(6);
        ps1[0].setPattern(p1);
        ps1[0].setStop(this.stopKsieciaJanusza);
        ps1[0].setIndex(0);
        ps1[0].setTravelTime(0);

        ps1[1].setPatternStopId(7);
        ps1[1].setPattern(p1);
        ps1[1].setStop(this.stopSwietokrzyska);
        ps1[1].setIndex(1);
        ps1[1].setTravelTime(540);

        ps1[2].setPatternStopId(8);
        ps1[2].setPattern(p1);
        ps1[2].setStop(this.stopTrocka);
        ps1[2].setIndex(2);
        ps1[2].setTravelTime(1380);

        p1.setPatternStops(Arrays.asList(ps1));
        this.stopKsieciaJanusza.patternStopsProperty().add(ps1[0]);
        this.stopSwietokrzyska.patternStopsProperty().add(ps1[1]);
        this.stopTrocka.patternStopsProperty().add(ps1[2]);

        // Inbound
        Pattern p2 = new Pattern();
        p2.setPatternId(4);
        p2.setHeadsign("Księcia Janusza");
        p2.setDirection(PatternDirection.INBOUND);
        p2.setLine(this.lineM2);

        PatternStop[] ps2 = { new PatternStop(), new PatternStop(), new PatternStop() };
        ps2[0].setPatternStopId(9);
        ps2[0].setPattern(p2);
        ps2[0].setStop(this.stopTrocka);
        ps2[0].setIndex(0);
        ps2[0].setTravelTime(0);

        ps2[1].setPatternStopId(10);
        ps2[1].setPattern(p2);
        ps2[1].setStop(this.stopSwietokrzyska);
        ps2[1].setIndex(1);
        ps2[1].setTravelTime(840);

        ps2[2].setPatternStopId(11);
        ps2[2].setPattern(p2);
        ps2[2].setStop(this.stopKsieciaJanusza);
        ps2[2].setIndex(2);
        ps2[2].setTravelTime(1380);

        p2.setPatternStops(Arrays.asList(ps2));
        this.stopTrocka.patternStopsProperty().add(ps2[0]);
        this.stopSwietokrzyska.patternStopsProperty().add(ps2[1]);
        this.stopKsieciaJanusza.patternStopsProperty().add(ps2[2]);

        this.addTrips(p1);
        this.addTrips(p2);
        this.lineM2.setPatterns(FXCollections.observableSet(p1, p2));
    }

    private void addTrips(Pattern p) {
        p.setTrips(new HashSet<>());

        final int[] weekdayDepartures = { 21600, 28800, 36000, 45000, 57600, 64800 };
        for (int dep : weekdayDepartures) {
            Trip t = new Trip();
            t.setTripId(this.tripIdEnum++);
            t.setDeparture(dep);
            t.setPattern(p);
            t.setCalendar(this.calendarWeekday);
            t.setWheelchairAccessible(
                    dep != 36000 ? WheelchairAccessibility.ACCESSIBLE : WheelchairAccessibility.UNKNOWN);

            p.tripsProperty().add(t);
            this.calendarWeekday.tripsProperty().add(t);
        }

        final int[] weekendDepartures = { 25200, 36000, 46800, 57600 };
        for (int dep : weekendDepartures) {
            Trip t = new Trip();
            t.setTripId(this.tripIdEnum++);
            t.setDeparture(dep);
            t.setPattern(p);
            t.setCalendar(this.calendarWeekend);
            t.setWheelchairAccessible(
                    dep != 36000 ? WheelchairAccessibility.INACCESSIBLE : WheelchairAccessibility.UNKNOWN);

            p.tripsProperty().add(t);
            this.calendarWeekend.tripsProperty().add(t);
        }
    }

    @Override
    public <T> void delete(T obj) {
        throw new RuntimeException("Tests can't change the state of the ObjectPool");
    }

    @Override
    public <T> void save(T obj) {
        throw new RuntimeException("Tests can't change the state of the ObjectPool");
    }

    @Override
    public void batchSave(Object... objs) {
        throw new RuntimeException("Tests can't change the state of the ObjectPool");
    }

    @Override
    public void batchDelete(Object... objs) {
        throw new RuntimeException("Tests can't change the state of the ObjectPool");
    }

    @Override
    public void batchSaveAndDelete(Object[] toSave, Object[] toDelete) {
        throw new RuntimeException("Tests can't change the state of the ObjectPool");
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> ObservableSet<T> listAll(Class<T> cls) {
        if (cls.equals(Agency.class)) {
            return (ObservableSet<T>) this.agencies;
        } else if (Calendar.class.equals(cls)) {
            return (ObservableSet<T>) this.calendars;
        } else if (Stop.class.equals(cls)) {
            return (ObservableSet<T>) this.stops;
        } else if (Line.class.equals(cls)) {
            return (ObservableSet<T>) this.lines;
        } else {
            throw new RuntimeException("Only top-level types can be listed (Agency/Calendar/Line/Stop)");
        }
    }

}
