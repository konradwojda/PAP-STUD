package edu.iipw.pap.controller;

import java.io.IOException;

import java.util.Collections;

import edu.iipw.pap.exceptions.InvalidObject;
import edu.iipw.pap.TravelTimeConverter;
import edu.iipw.pap.db.Database;
import edu.iipw.pap.db.model.PatternStop;
import edu.iipw.pap.db.model.Stop;
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

public class PatternStopCell extends HBox implements IController {
    @FXML
    private Button btnDown;

    @FXML
    private Button btnRemovePatternStop;

    @FXML
    private Button btnUp;

    @FXML
    private ChoiceBox<Stop> choiceStop;

    @FXML
    private HBox hboxRoot;

    @FXML
    private Text txtIndex;

    @FXML
    private TextField txtTravelTime;

    private PatternStop patternStop_;
    private ListView<PatternStop> listPatternStop_;

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

    @FXML
    void onRemove(ActionEvent event) {
        patternStop_.getPattern().patternStopsProperty().remove(this.patternStop_);
        patternStop_.getPattern().refreshIndices();
        Database.INSTANCE.markToDelete(this.patternStop_);
        this.patternStop_.setPattern(null);
        this.listPatternStop_.refresh();
    }

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

    ChoiceBox<Stop> getChoiceStop() {
        return this.choiceStop;
    }

    public PatternStopCell() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/elemPatternStop.fxml"));
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        choiceStop.getItems().setAll(Database.INSTANCE.listAll(Stop.class));
    }

    void setIndex(String text) {
        txtIndex.setText(text);
    }

    @Override
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
