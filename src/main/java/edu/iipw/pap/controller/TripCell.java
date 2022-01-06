package edu.iipw.pap.controller;

import java.io.IOException;

import edu.iipw.pap.db.Database;
import edu.iipw.pap.db.model.Calendar;
import edu.iipw.pap.db.model.Trip;
import edu.iipw.pap.interfaces.IController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;

public class TripCell extends HBox implements IController {

    @FXML
    private Button btnRemoveTrip;

    @FXML
    private CheckBox checkWheelchairAccessibility;

    @FXML
    private ChoiceBox<Calendar> choiceCalendar;

    @FXML
    private HBox hboxRoot;

    @FXML
    private TextField txtDeparture;

    private Trip trip_;

    private ListView<Trip> listTrip_;

    @FXML
    void onRemove(ActionEvent event) {
        trip_.getPattern().tripsProperty().remove(this.trip_);
        this.listTrip_.getItems().remove(this.trip_);
        this.trip_.setPattern(null);
        this.listTrip_.refresh();
    }

    HBox getHboxRoot() {
        return hboxRoot;
    }

    public TripCell() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/elemTrip.fxml"));
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        choiceCalendar.getItems().setAll(Database.INSTANCE.listAll(Calendar.class));
    }

    private class HHMMSSToInt extends StringConverter<Integer> {

        @Override
        public String toString(Integer object) {

            if (object == null)
                return null;

            int minutes = object.intValue() / 60;

            int seconds = object.intValue() % 60;

            int hours = minutes / 60;

            minutes = minutes % 60;

            return seconds == 0 ? String.format("%02d:%02d", hours, minutes)
                    : String.format("%02d:%02d:%02d", hours, minutes, seconds);
        }

        @Override
        public Integer fromString(String string) {
            if (string == null)
                return null;
            String[] splitted = string.split(":", 3);

            int minutes = 0;

            int seconds = 0;

            int hours = 0;
            if (splitted.length == 3) {
                hours = Integer.parseInt(splitted[0]);
                minutes = Integer.parseInt(splitted[1]);
                seconds = Integer.parseInt(splitted[2]);
            } else if (splitted.length == 2) {
                hours = Integer.parseInt(splitted[0]);
                minutes = Integer.parseInt(splitted[1]);
            } else {
                // incorrect value
                return -1;
            }

            return hours * 3600 + minutes * 60 + seconds;
        }

    }

    @Override
    public <T> void setObject(T obj) throws Exception {
        if (Trip.class.isInstance(obj)) {
            this.trip_ = (Trip) obj;

            this.choiceCalendar.valueProperty().bindBidirectional(this.trip_.calendarProperty());

            WheelchairAccessibilityMux wam = new WheelchairAccessibilityMux();

            wam.accessibleProperty().set(this.trip_.getWheelchairAccessible());

            this.trip_.wheelchairAccessibleProperty().bindBidirectional(wam.accessibleProperty());

            this.checkWheelchairAccessibility.selectedProperty().bindBidirectional(wam.checkedProperty());

            this.checkWheelchairAccessibility.indeterminateProperty().bindBidirectional(wam.indeterminateProperty());

            TextFormatter<Integer> travelTimeTextFormatter = new TextFormatter<>(new HHMMSSToInt());

            this.txtDeparture.textFormatterProperty().set(travelTimeTextFormatter);

            travelTimeTextFormatter.valueProperty().bindBidirectional(this.trip_.departureProperty().asObject());

        }
        else if(ListView.class.isInstance(obj)) {
            this.listTrip_ = (ListView<Trip>) obj;
        } else {
            // FIXME: custom exception
            throw new Exception("blad");
        }

    }
}
