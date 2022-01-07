package edu.iipw.pap.controller;

import java.io.IOException;

import edu.iipw.pap.db.Database;
import edu.iipw.pap.db.model.Calendar;
import edu.iipw.pap.db.model.Trip;
import edu.iipw.pap.exceptions.InvalidObject;
import edu.iipw.pap.interfaces.IController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;

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


    @Override
    public <T> void setObject(T obj) throws InvalidObject {
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
            throw new InvalidObject("Niepoprawny obiekt");
        }

    }
}
