package edu.iipw.pap.controller;

import java.util.ArrayList;
import java.util.List;

import edu.iipw.pap.db.Database;
import edu.iipw.pap.db.model.Calendar;
import edu.iipw.pap.db.model.Line;
import edu.iipw.pap.db.model.Pattern;
import edu.iipw.pap.db.model.PatternStop;
import edu.iipw.pap.db.model.Stop;
import edu.iipw.pap.db.model.StopTime;
import edu.iipw.pap.db.model.Trip;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class ViewPatternTimetableController {

    @FXML
    private ChoiceBox<Calendar> choiceCalendar;

    @FXML
    private ChoiceBox<Line> choiceLine;

    @FXML
    private ChoiceBox<Pattern> choicePattern;

    @FXML
    private TableColumn<StopTime, String> colStop;

    @FXML
    private TableColumn<StopTime, String> colTrips;

    @FXML
    private VBox listLine;

    @FXML
    private TableView<StopTime> tblLine;

    @FXML
    void onChoiceLine(ActionEvent event) {
        System.out.println("Line");
        choicePattern.getItems().setAll(choiceLine.getSelectionModel().getSelectedItem().patternsProperty());
        colStop.getColumns().clear();
        colTrips.getColumns().clear();
        tblLine.getItems().clear();
        choicePattern.valueProperty().set(null);
        choiceCalendar.valueProperty().set(null);
    }

    @FXML
    void onChoicePattern(ActionEvent event) {
        System.out.println("Pattern");
        refreshPatternTimetableTable();
    }

    @FXML
    void onChoiceCalendar(ActionEvent event) {
        System.out.println("Calendar");
        refreshPatternTimetableTable();
    }

    private HHMMSSToInt depTimeFormatter = new HHMMSSToInt();

    private void refreshPatternTimetableTable() {
        try {
            // TODO: fill
            Calendar chosenCalendar = choiceCalendar.getValue();
            Pattern chosenPattern = choicePattern.getValue();

            List<StopTime> stopTimes = new ArrayList<StopTime>();
            for (var stopTime : chosenPattern.getStopTimes().filter(s -> s.getTrip().getCalendar() == chosenCalendar)
                    .toArray()) {

                stopTimes.add((StopTime) stopTime);
            }

            this.tblLine.getItems().setAll(stopTimes);

            colTrips.getColumns().clear();

            colStop.getColumns().clear();

            // for (var trip : chosenPattern.tripsProperty()) {
            //     TableColumn<StopTime, String> depColumn = new TableColumn<>(String.valueOf(trip.getTripId()));

            //     depColumn.setCellValueFactory(cellData -> new SimpleStringProperty(depTimeFormatter.toString(cellData.getValue().getDepartureTime())));
            //     colTrips.getColumns().add(depColumn);
            // }

            colTrips.setCellValueFactory(cellData -> new SimpleStringProperty(depTimeFormatter.toString(cellData.getValue().getDepartureTime())));

        } catch (NullPointerException e) {
            // FIXME: co z tym wyjatkiem?
        }
    }

    public void InitializePatternTimetableTable() {
        // TODO: fill
        choiceCalendar.getItems().setAll(Database.INSTANCE.listAll(Calendar.class));
        choiceLine.getItems().setAll(Database.INSTANCE.listAll(Line.class));

        colStop.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStop().getName()));

        // colTrips.getColumns().add(new TableColumn<>("ID KURSU"));
        // colTrips.getColumns().add(new TableColumn<>("ID KURSU"));

        refreshPatternTimetableTable();
    }
}
