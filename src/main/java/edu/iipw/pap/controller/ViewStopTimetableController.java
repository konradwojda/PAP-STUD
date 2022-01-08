package edu.iipw.pap.controller;

import java.util.ArrayList;
import java.util.List;

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
import javafx.scene.layout.VBox;

public class ViewStopTimetableController {
    @FXML
    private ChoiceBox<Calendar> choiceCalendar;

    @FXML
    private ChoiceBox<Stop> choiceStop;

    @FXML
    private TableColumn<StopTime, String> colDeparture;

    @FXML
    private TableColumn<StopTime, String> colHeadsign;

    @FXML
    private TableColumn<StopTime, String> colLine;

    @FXML
    private TableColumn<StopTime, String> colWheelchairAccessibility;

    @FXML
    private VBox listLine;

    @FXML
    private TableView<StopTime> tblStopTimetable;

    @FXML
    void onChoiceCalendar(ActionEvent event) {
        refreshStopTimetableTable();
    }

    @FXML
    void onChoiceStop(ActionEvent event) {
        refreshStopTimetableTable();
    }

    private String wheelchairAccessibilityToString(WheelchairAccessibility wa) {
        if (wa.equals(WheelchairAccessibility.ACCESSIBLE))
            return "\u2713";
        else if (wa.equals(WheelchairAccessibility.INACCESSIBLE))
            return "\u2715";
        else
            return "?";
    }

    final HHMMSSToInt depTimeFormatter = new HHMMSSToInt();

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

    public void InitializeStopTimetableTable() {
        colWheelchairAccessibility.setText("\u267F");
        choiceCalendar.getItems().setAll(Database.INSTANCE.listAll(Calendar.class));
        choiceStop.getItems().setAll(Database.INSTANCE.listAll(Stop.class));
        colWheelchairAccessibility.setCellValueFactory(cellData -> new SimpleStringProperty(
                wheelchairAccessibilityToString(cellData.getValue().getTrip().getWheelchairAccessible())));
        colDeparture.setCellValueFactory(cellData -> new SimpleStringProperty(
                depTimeFormatter.toString(cellData.getValue().getDepartureTime())));
        colHeadsign.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getTrip().getPattern().getHeadsign()));
        colLine.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getTrip().getPattern().getLine().getCode()));
        refreshStopTimetableTable();
    }

}
