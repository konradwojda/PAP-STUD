package edu.iipw.pap.controller;

import edu.iipw.pap.db.model.Calendar;
import edu.iipw.pap.db.model.Line;
import edu.iipw.pap.db.model.Pattern;
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

    private void refreshPatternTimetableTable() {
        // TODO: fill
    }

    public void InitializePatternTimetableTable() {
        // TODO: fill
        refreshPatternTimetableTable();
    }
}
