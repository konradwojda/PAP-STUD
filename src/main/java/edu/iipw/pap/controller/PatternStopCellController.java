package edu.iipw.pap.controller;

import java.io.IOException;
import java.util.Collections;

import edu.iipw.pap.TravelTimeConverter;
import edu.iipw.pap.db.Database;
import edu.iipw.pap.db.model.PatternStop;
import edu.iipw.pap.db.model.Stop;
import edu.iipw.pap.exceptions.InvalidObject;
import edu.iipw.pap.interfaces.IController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * PatternStopCell represents single cell in pattern stop list - which maps to
 * one PatternStop object
 */
public class PatternStopCellController extends HBox implements IController {

    /**
     * Button to move stop down
     */
    @FXML
    private Button btnDown;

    /**
     * Button to remove pattern stop
     */
    @FXML
    private Button btnRemovePatternStop;

    /**
     * Button to move stop up
     */
    @FXML
    private Button btnUp;

    /**
     * ChoiceBox to choose which stop is represented by this PatternStop
     */
    @FXML
    private ChoiceBox<Stop> choiceStop;

    /**
     * Reference to pattern stop cell root
     */
    @FXML
    private HBox hboxRoot;

    /**
     * Text to display index
     */
    @FXML
    private Text txtIndex;

    /**
     * Text to enter travel time from first PatternStop in this pattern
     */
    @FXML
    private TextField txtTravelTime;

    /**
     * PatternStop that is being represented by certain cell
     */
    private PatternStop patternStop_;

    /**
     * Reference to list of pattern stops
     */
    private ListView<PatternStop> listPatternStop_;

    /**
     * Move stop down
     *
     * @param event
     */
    @FXML
    void onDown(ActionEvent event) {
        int idx = this.patternStop_.getIndex();
        var pattern = this.patternStop_.getPattern();
        var allPatternStops = pattern.patternStopsProperty();

        if (allPatternStops.size() - 1 != idx) {
            Collections.swap(allPatternStops, idx, idx + 1);
            Database.INSTANCE.markToSave(allPatternStops.get(idx));
            Database.INSTANCE.markToSave(allPatternStops.get(idx + 1));
            pattern.refreshIndices();
            this.listPatternStop_.refresh();
        }
    }

    /**
     * Remove selected pattern stop
     *
     * @param event
     */
    @FXML
    void onRemove(ActionEvent event) {
        patternStop_.getPattern().patternStopsProperty().remove(this.patternStop_);
        patternStop_.getPattern().refreshIndices();
        Database.INSTANCE.markToDelete(this.patternStop_);
        this.patternStop_.setPattern(null);
        this.listPatternStop_.refresh();
    }

    /**
     * Move stop up
     *
     * @param event
     */
    @FXML
    void onUp(ActionEvent event) {
        int idx = this.patternStop_.getIndex();
        var pattern = this.patternStop_.getPattern();
        var allPatternStops = pattern.patternStopsProperty();
        if (idx > 0) {
            Collections.swap(allPatternStops, idx, idx - 1);
            Database.INSTANCE.markToSave(allPatternStops.get(idx));
            Database.INSTANCE.markToSave(allPatternStops.get(idx - 1));
            pattern.refreshIndices();
            this.listPatternStop_.refresh();
        }
    }

    HBox getHboxRoot() {
        return hboxRoot;
    }

    /**
     * Load fxml and initialize choice box
     */
    public PatternStopCellController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/elemPatternStop.fxml"));
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        choiceStop.getItems().setAll(Database.INSTANCE.listAll(Stop.class));
    }

    /**
     * Set index in table
     *
     * @param text
     */
    void setIndex(String text) {
        txtIndex.setText(text);
    }

    /**
     * Set object that is being represented by this cell
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> void setObject(T obj) throws InvalidObject {
        if (PatternStop.class.isInstance(obj)) {
            this.patternStop_ = (PatternStop) obj;

            StrIntMux sim = new StrIntMux();
            sim.iProperty().set(this.patternStop_.getIndex());

            this.patternStop_.indexProperty().bindBidirectional(sim.iProperty());
            this.txtIndex.textProperty().bindBidirectional(sim.sProperty());
            this.choiceStop.valueProperty().bindBidirectional(this.patternStop_.stopProperty());

            TextFormatter<Integer> travelTimeTextFormatter = new TextFormatter<>(TravelTimeConverter.INSTANCE);
            this.txtTravelTime.textFormatterProperty().set(travelTimeTextFormatter);
            travelTimeTextFormatter.valueProperty()
                    .bindBidirectional(this.patternStop_.travelTimeProperty().asObject());
        } else if (ListView.class.isInstance(obj)) {
            this.listPatternStop_ = (ListView<PatternStop>) obj;
        } else {
            throw new InvalidObject("Niewłaściwy obiekt");
        }
    }
}
