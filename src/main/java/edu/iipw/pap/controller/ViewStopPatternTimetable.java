package edu.iipw.pap.controller;

import edu.iipw.pap.db.model.Calendar;
import edu.iipw.pap.db.model.Stop;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

public class ViewStopPatternTimetable {
    @FXML
    private ChoiceBox<Calendar> choiceCalendar;

    @FXML
    private ChoiceBox<Stop> choiceStop;

    @FXML
    private TableColumn<?, ?> colDeparture;

    @FXML
    private TableColumn<?, ?> colHeadsign;

    @FXML
    private TableColumn<?, ?> colLine;

    @FXML
    private VBox listLine;

    @FXML
    private TableView<?> tblStopTimetable;

    private void refreshStopTimetableTable() {
        // TODO: fill
    }

    public void InitializeStopTimetableTable() {
        // TODO: fill
        refreshStopTimetableTable();
    }

}
