package edu.iipw.pap.controller;

import java.io.IOException;

import edu.iipw.pap.DepartureTimeConverter;
import edu.iipw.pap.db.Database;
import edu.iipw.pap.db.model.Calendar;
import edu.iipw.pap.db.model.Trip;
import edu.iipw.pap.exceptions.InvalidObject;
import edu.iipw.pap.interfaces.IController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;

/**
 * TripCell represents single cell in trip list - which maps to
 * one Trip object
 */
public class TripCellController extends HBox implements IController {

    /**
     * Button to remove trip
     */
    @FXML
    private Button btnRemoveTrip;

    /**
     * CheckBox to set whether trip is wheelchair accessible
     */
    @FXML
    private CheckBox checkWheelchairAccessibility;

    /**
     * ChoiceBox to choose calendar for trip
     */
    @FXML
    private ChoiceBox<Calendar> choiceCalendar;

    /**
     * Reference to trip cell root
     */
    @FXML
    private HBox hboxRoot;

    /**
     * TextField to enter trip's departure time
     */
    @FXML
    private TextField txtDeparture;

    /**
     * Trip instance that is being edited
     */
    private Trip trip_;

    /**
     * Reference to list of trips in certain pattern
     */
    private ListView<Trip> listTrip_;

    /**
     * Remove chosen trip
     *
     * @param event
     */
    @FXML
    void onRemove(ActionEvent event) {
        trip_.getPattern().tripsProperty().remove(this.trip_);
        this.listTrip_.getItems().remove(this.trip_);
        Database.INSTANCE.markToDelete(this.trip_);
        this.trip_.setPattern(null);
        this.listTrip_.refresh();
    }

    HBox getHboxRoot() {
        return hboxRoot;
    }

    /**
     * Load fxml and load choice box
     */
    public TripCellController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/elemTrip.fxml"));
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        choiceCalendar.getItems().setAll(Database.INSTANCE.listAll(Calendar.class));
    }

    /**
     * Set object to be represented in this cell
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> void setObject(T obj) throws InvalidObject {
        if (Trip.class.isInstance(obj)) {
            this.trip_ = (Trip) obj;
            this.choiceCalendar.valueProperty().bindBidirectional(this.trip_.calendarProperty());

            WheelchairAccessibilityMux wam = new WheelchairAccessibilityMux();
            wam.accessibleProperty().set(this.trip_.getWheelchairAccessible());
            this.trip_.wheelchairAccessibleProperty().bindBidirectional(wam.accessibleProperty());
            this.checkWheelchairAccessibility.selectedProperty().bindBidirectional(wam.checkedProperty());
            this.checkWheelchairAccessibility.indeterminateProperty().bindBidirectional(wam.indeterminateProperty());

            TextFormatter<Integer> travelTimeTextFormatter = new TextFormatter<>(DepartureTimeConverter.INSTANCE);
            this.txtDeparture.textFormatterProperty().set(travelTimeTextFormatter);
            travelTimeTextFormatter.valueProperty().bindBidirectional(this.trip_.departureProperty().asObject());

        } else if (ListView.class.isInstance(obj)) {
            this.listTrip_ = (ListView<Trip>) obj;
        } else {
            throw new InvalidObject("Niepoprawny obiekt");
        }

    }
}
