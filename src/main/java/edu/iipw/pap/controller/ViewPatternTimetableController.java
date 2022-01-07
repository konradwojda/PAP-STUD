package edu.iipw.pap.controller;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import javafx.scene.layout.VBox;

public class ViewPatternTimetableController {

    @FXML
    private ChoiceBox<Calendar> choiceCalendar;

    @FXML
    private ChoiceBox<Line> choiceLine;

    @FXML
    private ChoiceBox<Pattern> choicePattern;

    @FXML
    private VBox listLine;

    @FXML
    private TableView<Trip> tblLine;

    @FXML
    void onChoiceLine(ActionEvent event) {
        choicePattern.getItems().setAll(choiceLine.getSelectionModel().getSelectedItem().patternsProperty());
        tblLine.getItems().clear();
        choicePattern.valueProperty().set(null);
        choiceCalendar.valueProperty().set(null);
    }

    @FXML
    void onChoicePattern(ActionEvent event) {
        refreshPatternTimetableTable();
    }

    @FXML
    void onChoiceCalendar(ActionEvent event) {
        refreshPatternTimetableTable();
    }

    private HHMMSSToInt depTimeFormatter = new HHMMSSToInt();

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

    private void refreshPatternTimetableTable() {
        Pattern pattern = choicePattern.getValue();
        Calendar calendar = choiceCalendar.getValue();
        if (pattern == null || calendar == null)
            return;

        var allColumns = tblLine.getColumns();
        allColumns.clear();

        Map<Trip, List<StopTime>> cachedStopTimes = cacheStopTimes(pattern, calendar);

        int idx = 0;
        for (PatternStop ps : pattern.patternStopsProperty()) {
            final int idxCopy = idx++;

            TableColumn<Trip, String> column = new TableColumn<>(ps.getStop().getName());
            column.setCellValueFactory(c -> {
                var st = cachedStopTimes.get(c.getValue()).get(idxCopy);
                var time = depTimeFormatter.toString(st.getDepartureTime());
                return new ReadOnlyStringWrapper(time);
            });

            allColumns.add(column);
        }

        tblLine.getItems().setAll(cachedStopTimes.keySet());
    }

    public void InitializePatternTimetableTable() {
        // TODO: fill
        choiceCalendar.getItems().setAll(Database.INSTANCE.listAll(Calendar.class));
        choiceLine.getItems().setAll(Database.INSTANCE.listAll(Line.class));
        refreshPatternTimetableTable();
    }
}
