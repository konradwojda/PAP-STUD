package edu.iipw.pap.controller;

import java.io.IOException;

import edu.iipw.pap.db.Database;
import edu.iipw.pap.db.model.Stop;
import javafx.event.ActionEvent;
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
    private HBox hboxRoot;

    @FXML
    private Text txtIndex;

    @FXML
    private TextField txtTravelTime;

    HBox getHboxRoot() {
        return hboxRoot;
    }

    ChoiceBox<Stop> getChoiceStop()
    {
        return this.choiceStop;
    }

    void setDownButton(EventHandler<ActionEvent> event) {
        btnDown.setOnAction(event);
    }

    void setRemovePatternStopButton(EventHandler<ActionEvent> event) {
        btnRemovePatternStop.setOnAction(event);
    }

    void setUpButton(EventHandler<ActionEvent> event) {
        btnUp.setOnAction(event);
    }

    public PatternViewController() {
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
}
