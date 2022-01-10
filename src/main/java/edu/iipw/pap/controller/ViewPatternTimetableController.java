package edu.iipw.pap.controller;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import edu.iipw.pap.DepartureTimeConverter;
import edu.iipw.pap.db.Database;
import edu.iipw.pap.db.model.Calendar;
import edu.iipw.pap.db.model.Line;
import edu.iipw.pap.db.model.Pattern;
import edu.iipw.pap.db.model.PatternStop;
import edu.iipw.pap.db.model.StopTime;
import edu.iipw.pap.db.model.Trip;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * ViewPatternTimetableController is responsible for displaying timetable of
 * given pattern
 */
public class ViewPatternTimetableController {

    /**
     * ChoiceBox to choose calendar
     */
    @FXML
    private ChoiceBox<Calendar> choiceCalendar;

    /**
     * ChoiceBox to choose line
     */
    @FXML
    private ChoiceBox<Line> choiceLine;

    /**
     * ChoiceBox to choose pattern
     */
    @FXML
    private ChoiceBox<Pattern> choicePattern;

    /**
     * TableView to display timetable
     */
    @FXML
    private TableView<Trip> tblLine;

    /**
     * If line is chosen - clear timetable and change patterns to choose
     *
     * @param event
     */
    @FXML
    void onChoiceLine(ActionEvent event) {
        choicePattern.getItems().setAll(choiceLine.getSelectionModel().getSelectedItem().patternsProperty());
        tblLine.getItems().clear();
        choicePattern.valueProperty().set(null);
        choiceCalendar.valueProperty().set(null);
    }

    /**
     * If pattern is chosen - refresh timetable
     *
     * @param event
     */
    @FXML
    void onChoicePattern(ActionEvent event) {
        refreshPatternTimetableTable();
    }

    /**
     * If calendar is chosen - refresh timetable
     *
     * @param event
     */
    @FXML
    void onChoiceCalendar(ActionEvent event) {
        refreshPatternTimetableTable();
    }

    /**
     * Creates a cache for all StopTimes of all trips of the provided
     * pattern (and attached to a given calendar).
     */
    private Map<Trip, List<StopTime>> cacheStopTimes(Pattern pattern, Calendar calendar) {
        Map<Trip, List<StopTime>> cachedStopTimes = new HashMap<>();

        for (Trip t : pattern.tripsProperty()) {
            if (!t.getCalendar().equals(calendar))
                continue;

            List<StopTime> stopTimes = t.getStopTimes().collect(Collectors.toList());
            stopTimes.sort(Comparator.comparingInt(st -> st.getIndex()));
            cachedStopTimes.put(t, stopTimes);
        }

        return cachedStopTimes;
    }

    /**
     * Refreshes the main view.
     */
    private void refreshPatternTimetableTable() {
        // First, get the selected pattern and calendar
        Pattern pattern = choicePattern.getValue();
        Calendar calendar = choiceCalendar.getValue();

        // Don't do anything with a non-selected pattern and calendar
        if (pattern == null || calendar == null)
            return;

        // Clear the columns
        var allColumns = tblLine.getColumns();
        allColumns.clear();

        // Get the cached trips
        Map<Trip, List<StopTime>> cachedStopTimes = cacheStopTimes(pattern, calendar);

        // Create columns per-stop
        int idx = 0;
        for (PatternStop ps : pattern.patternStopsProperty()) {
            final int idxCopy = idx++;

            TableColumn<Trip, String> column = new TableColumn<>(ps.getStop().getName());
            column.setCellValueFactory(c -> {
                var st = cachedStopTimes.get(c.getValue()).get(idxCopy);
                var time = DepartureTimeConverter.INSTANCE.toString(st.getDepartureTime());
                return new ReadOnlyStringWrapper(time);
            });

            allColumns.add(column);
        }

        tblLine.getItems().setAll(cachedStopTimes.keySet());
    }

    /**
     * Initialize timetable - set values in choice boxes
     */
    public void InitializePatternTimetableTable() {
        choiceCalendar.valueProperty().set(null);
        choiceLine.valueProperty().set(null);
        choicePattern.valueProperty().set(null);
        choiceCalendar.getItems().setAll(Database.INSTANCE.listAll(Calendar.class));
        choiceLine.getItems().setAll(Database.INSTANCE.listAll(Line.class));
        refreshPatternTimetableTable();
    }
}
