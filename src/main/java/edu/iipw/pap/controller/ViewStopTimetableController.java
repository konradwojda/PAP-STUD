package edu.iipw.pap.controller;

import java.util.List;

import edu.iipw.pap.db.Database;
import edu.iipw.pap.db.model.Calendar;
import edu.iipw.pap.db.model.Line;
import edu.iipw.pap.db.model.Stop;
import edu.iipw.pap.db.model.StopTime;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class ViewStopTimetableController {
    @FXML
    private ChoiceBox<Calendar> choiceCalendar;

    @FXML
    private ChoiceBox<Stop> choiceStop;

    @FXML
    private TableColumn<StopTime, Integer> colDeparture;

    @FXML
    private TableColumn<StopTime, String> colHeadsign;

    @FXML
    private TableColumn<StopTime, Line> colLine;

    @FXML
    private VBox listLine;

    @FXML
    private TableView<StopTime> tblStopTimetable;

    @FXML
    void onChoiceCalendar(ActionEvent event) {
        System.out.println("Calendar");
    }

    @FXML
    void onChoiceStop(ActionEvent event) {
        System.out.println("Stop");
    }


    private void refreshStopTimetableTable() {
        try {
            Calendar chosenCalendar = choiceCalendar.getValue();
            Stop chosenStop = choiceStop.getValue();
            this.tblStopTimetable.getItems().setAll((StopTime[])chosenStop.getStopTimes().filter(s -> s.getTrip().getCalendar() == chosenCalendar).toArray());
        }
        catch (Exception e)
        {
        }
    }

    public void InitializeStopTimetableTable() {
        choiceCalendar.getItems().setAll(Database.INSTANCE.listAll(Calendar.class));
        choiceStop.getItems().setAll(Database.INSTANCE.listAll(Stop.class));

        colDeparture.setCellValueFactory(new PropertyValueFactory<StopTime, Integer>("departureTime"));
        colLine.setCellValueFactory(new PropertyValueFactory<StopTime, Line>("trip.getPattern().getLine()"));
        colHeadsign.setCellValueFactory(new PropertyValueFactory<StopTime, String>("trip.getPattern().getHeadsign()"));

        refreshStopTimetableTable();
    }

}
