package edu.iipw.pap.controller;

import edu.iipw.pap.db.Database;
import edu.iipw.pap.db.model.Calendar;
import edu.iipw.pap.db.model.Line;
import edu.iipw.pap.db.model.Pattern;
import edu.iipw.pap.db.model.PatternStop;
import edu.iipw.pap.db.model.Stop;
import edu.iipw.pap.db.model.StopTime;
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
    private TableColumn<Stop, String> colStop;

    @FXML
    private TableColumn<?, ?> colTrips;

    @FXML
    private VBox listLine;

    @FXML
    private TableView<Line> tblLine;

    @FXML
    void onChoiceLine(ActionEvent event) {
        System.out.println("Line");
        choicePattern.getItems().setAll(choiceLine.getSelectionModel().getSelectedItem().patternsProperty());
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
        choiceCalendar.getItems().setAll(Database.INSTANCE.listAll(Calendar.class));
        choiceLine.getItems().setAll(Database.INSTANCE.listAll(Line.class));

        colTrips.getColumns().add(new TableColumn<>("ID KURSU"));
        colTrips.getColumns().add(new TableColumn<>("ID KURSU"));


        refreshPatternTimetableTable();
    }
}
