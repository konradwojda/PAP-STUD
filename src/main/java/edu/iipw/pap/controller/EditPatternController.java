package edu.iipw.pap.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;

import edu.iipw.pap.db.Database;
import edu.iipw.pap.db.model.Pattern;
import edu.iipw.pap.db.model.PatternDirection;
import edu.iipw.pap.db.model.PatternStop;
import edu.iipw.pap.db.model.Trip;
import edu.iipw.pap.exceptions.InvalidData;
import edu.iipw.pap.exceptions.InvalidObject;
import edu.iipw.pap.interfaces.IController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * EditPatternController is responsible for controlling window while user is
 * adding or editing a line's pattern. It implements IController to set object
 * that is
 * being edited.
 */
public class EditPatternController implements Initializable, IController {
    /**
     * Button for confirming changes
     */
    @FXML
    private Button btnPatternOk;

    /**
     * ChoiceBox for choosing direction
     */
    @FXML
    private ChoiceBox<PatternDirection> choiceDirection;

    /**
     * ListView to display pattern's stops
     */
    @FXML
    private ListView<PatternStop> listPatternStop;

    /**
     * ListView to display pattern's trips
     */
    @FXML
    private ListView<Trip> listTrip;

    /**
     * TextField to enter pattern's headsign
     */
    @FXML
    private TextField txtLineHeadsign;

    /**
     * Text to display error if occurs
     */
    @FXML
    private Text txtPatternError;

    /**
     * Instance of object that is being edited
     */
    private Pattern pattern_;

    /**
     * Set object to edit and bind properties
     */
    @Override
    public <T> void setObject(T obj) throws InvalidObject {
        if (Pattern.class.isInstance(obj)) {
            this.pattern_ = (Pattern) obj;
            this.txtLineHeadsign.textProperty().bindBidirectional(this.pattern_.headsignProperty());
            this.choiceDirection.valueProperty().bindBidirectional(this.pattern_.directionProperty());
            this.listPatternStop.itemsProperty().bindBidirectional(this.pattern_.patternStopsProperty());
            this.listTrip.getItems().setAll(new ArrayList<Trip>(this.pattern_.tripsProperty()));
            if (pattern_.patternStopsProperty().get() == null) {
                pattern_.setPatternStops(FXCollections.observableArrayList());
            }
        } else {
            throw new InvalidObject("Niepoprawny obiekt");
        }
    }

    /**
     * After clicking on new pattern stop, create it and add to list
     *
     * @param event
     */
    @FXML
    void onNewPatternStop(ActionEvent event) {
        PatternStop patternStop = new PatternStop();
        patternStop.setPattern(this.pattern_);
        listPatternStop.getItems().add(patternStop);
        Database.INSTANCE.markToSave(patternStop);
        this.pattern_.refreshIndices();
    }

    /**
     * After clicking on new pattern trip, create it and add to list
     *
     * @param event
     */
    @FXML
    void onNewTrip(ActionEvent event) {
        Trip trip = new Trip();
        trip.setPattern(this.pattern_);
        if (this.pattern_.tripsProperty().get() == null)
            this.pattern_.setTrips(new HashSet<>());
        this.pattern_.tripsProperty().add(trip);
        listTrip.getItems().add(trip);
        Database.INSTANCE.markToSave(trip);
    }

    /**
     * After clicking ok - validate input and close stage
     *
     * @param event
     * @throws Exception
     */
    @FXML
    void onPatternOk(ActionEvent event) throws Exception {
        try {
            pattern_.validateUserInput();
        } catch (InvalidData e) {
            txtPatternError.setText(e.toString());
            return;
        }
        Stage stage = (Stage) btnPatternOk.getScene().getWindow();
        stage.close();
    }

    /**
     * Initialize choice boxes and lists
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceDirection.getItems().setAll(PatternDirection.values());
        listPatternStop.setCellFactory(param -> new PatternStopCellController());
        listTrip.setCellFactory(param -> new TripCellController());
    }
}
