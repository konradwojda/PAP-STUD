package edu.iipw.pap.controller;

import edu.iipw.pap.db.model.Calendar;
import edu.iipw.pap.db.model.Line;
import edu.iipw.pap.db.model.Pattern;
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
    private TableColumn<?, ?> colTrip;

    @FXML
    private VBox listLine;

    @FXML
    private TableView<?> tblLine;

    @FXML
    void onChoiceLine(ActionEvent event) {
        System.out.println("Line");
    }

    @FXML
    void onChoicePattern(ActionEvent event) {
        System.out.println("Pattern");
    }

    @FXML
    void onChoiceCalendar(ActionEvent event) {
        System.out.println("Calendar");
    }

    private void refreshPatternTimetableTable() {
        // TODO: fill
    }

    public void InitializePatternTimetableTable() {
        // TODO: fill
        refreshPatternTimetableTable();
    }
}
