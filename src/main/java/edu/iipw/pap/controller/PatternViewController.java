package edu.iipw.pap.controller;

import java.io.IOException;

import edu.iipw.pap.db.model.Stop;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class PatternViewController extends HBox {
    @FXML
    private Button btnDown;

    @FXML
    private Button btnRemovePatternStop;

    @FXML
    private Button btnUp;

    @FXML
    private ChoiceBox<Stop> choiceStop;

    @FXML
    private TextField txtHour;

    @FXML
    private HBox hboxRoot;

    @FXML
    private Text txtIndex;

    @FXML
    private TextField txtMinutes;

    HBox getHboxRoot() {
        return hboxRoot;
    }

    void setDownButton(EventHandler event) {
        btnDown.setOnAction(event);
    }

    void setRemovePatternStopButton(EventHandler event) {
        btnRemovePatternStop.setOnAction(event);
    }

    void setUpButton(EventHandler event) {
        btnUp.setOnAction(event);
    }

    public PatternViewController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/listPatternStop.fxml"));
        // FIXME: NWM PO CO TU TO BYŁO
        // fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void setIndex(String text) {
        txtIndex.setText(text);
    }

    void setHour(String text) {
        txtHour.setText(text);
    }

    void setMinutes(String text) {
        txtMinutes.setText(text);
    }
}
