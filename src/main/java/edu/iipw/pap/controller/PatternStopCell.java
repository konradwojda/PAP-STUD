package edu.iipw.pap.controller;

import java.io.IOException;

import edu.iipw.pap.db.Database;
import edu.iipw.pap.db.model.PatternStop;
import edu.iipw.pap.db.model.Stop;
import edu.iipw.pap.interfaces.IController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class PatternStopCell extends HBox implements IController{
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

    @FXML
    void onDown(ActionEvent event) {

    }

    @FXML
    void onRemove(ActionEvent event) {

    }

    @FXML
    void onUp(ActionEvent event) {

    }

    HBox getHboxRoot() {
        return hboxRoot;
    }

    ChoiceBox<Stop> getChoiceStop()
    {
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

    private PatternStop patternStop_;

    @Override
    public <T> void setObject(T obj) throws Exception {
        if(PatternStop.class.isInstance(obj)) {

        }

    }
}
