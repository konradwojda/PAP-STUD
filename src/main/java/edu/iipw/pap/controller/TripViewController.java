package edu.iipw.pap.controller;

import java.io.IOException;

import edu.iipw.pap.db.Database;
import edu.iipw.pap.db.model.Calendar;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class TripViewController extends HBox{

    @FXML
    private Button btnRemoveTrip;

    @FXML
    private CheckBox checkWheelchairAccessibility;

    @FXML
    private ChoiceBox<Calendar> choiceCalendar;

    @FXML
    private HBox hboxRoot;

    @FXML
    private TextField txtHour;

    @FXML
    private TextField txtMinutes;

    HBox getHboxRoot() {
        return hboxRoot;
    }

    void setRemovePatternStopButton(EventHandler<ActionEvent> event) {
        btnRemoveTrip.setOnAction(event);
    }

    public TripViewController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/elemTrip.fxml"));
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        choiceCalendar.getItems().setAll(Database.INSTANCE.listAll(Calendar.class));
    }

    void setHour(String text) {
        txtHour.setText(text);
    }

    void setMinutes(String text) {
        txtMinutes.setText(text);
    }
}
