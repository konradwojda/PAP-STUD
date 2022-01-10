package edu.iipw.pap.controller;

import java.util.ArrayList;
import java.util.List;

import edu.iipw.pap.DepartureTimeConverter;
import edu.iipw.pap.db.Database;
import edu.iipw.pap.db.model.Calendar;
import edu.iipw.pap.db.model.Stop;
import edu.iipw.pap.db.model.StopTime;
import edu.iipw.pap.db.model.WheelchairAccessibility;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * ViewStopTimetableController is responsible for displaying timetable of given
 * stop
 */
public class ViewStopTimetableController {

    /**
     * ChoiceBox to choose calendar
     */
    @FXML
    private ChoiceBox<Calendar> choiceCalendar;

    /**
     * ChoiceBox to choose stop
     */
    @FXML
    private ChoiceBox<Stop> choiceStop;

    /**
     * TableColumn for departure
     */
    @FXML
    private TableColumn<StopTime, String> colDeparture;

    /**
     * TableColumn for headsign
     */
    @FXML
    private TableColumn<StopTime, String> colHeadsign;

    /**
     * TableColumn for line
     */
    @FXML
    private TableColumn<StopTime, String> colLine;

    /**
     * TableColumn for wheelchair accessibility
     */
    @FXML
    private TableColumn<StopTime, String> colWheelchairAccessibility;

    /**
     * TableView to display timetable
     */
    @FXML
    private TableView<StopTime> tblStopTimetable;

    /**
     * If calendar is chosen - refresh timetable
     *
     * @param event
     */
    @FXML
    void onChoiceCalendar(ActionEvent event) {
        refreshStopTimetableTable();
    }

    /**
     * If stop is chosen - refresh timetable
     *
     * @param event
     */
    @FXML
    void onChoiceStop(ActionEvent event) {
        refreshStopTimetableTable();
    }

    /**
     * Convert wheelchair accessibility to unicode character
     *
     * @param wa - WheelchairAccessibility object
     * @return unicode character as String object
     */
    private String wheelchairAccessibilityToString(WheelchairAccessibility wa) {
        if (wa.equals(WheelchairAccessibility.ACCESSIBLE))
            return "\u2713";
        else if (wa.equals(WheelchairAccessibility.INACCESSIBLE))
            return "\u2715";
        else
            return "?";
    }

    /**
     * Refresh timetable - set objects to table view
     */
    private void refreshStopTimetableTable() {
        Calendar chosenCalendar = choiceCalendar.getValue();
        Stop chosenStop = choiceStop.getValue();
        if (chosenCalendar == null || chosenStop == null)
            return;
        List<StopTime> stopTimes = new ArrayList<StopTime>();
        for (var stopTime : chosenStop.getStopTimes().filter(s -> s.getTrip().getCalendar() == chosenCalendar)
                .toArray()) {
            stopTimes.add((StopTime) stopTime);
        }
        this.tblStopTimetable.getItems().setAll(stopTimes);
    }

    /**
     * Initialize stop timetable - set value factories
     */
    public void InitializeStopTimetableTable() {
        colWheelchairAccessibility.setText("\u267F");
        choiceCalendar.getItems().setAll(Database.INSTANCE.listAll(Calendar.class));
        choiceStop.getItems().setAll(Database.INSTANCE.listAll(Stop.class));
        colWheelchairAccessibility.setCellValueFactory(cellData -> new SimpleStringProperty(
                wheelchairAccessibilityToString(cellData.getValue().getTrip().getWheelchairAccessible())));
        colDeparture.setCellValueFactory(cellData -> new SimpleStringProperty(
                DepartureTimeConverter.INSTANCE.toString(cellData.getValue().getDepartureTime())));
        colHeadsign.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getTrip().getPattern().getHeadsign()));
        colLine.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getTrip().getPattern().getLine().getCode()));
        refreshStopTimetableTable();
    }

}
