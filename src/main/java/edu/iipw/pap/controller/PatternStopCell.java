package edu.iipw.pap.controller;

import java.io.IOException;

import org.hibernate.sql.Delete;

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
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

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

    @FXML
    void onDown(ActionEvent event) {
        System.out.println("Dupa downs ");
    }

    @FXML
    void onRemove(ActionEvent event) {
        System.out.println("Dupa remove ");
        // patternStop_.getPattern().patternStopsProperty().remove(this.patternStop_);
    }

    @FXML
    void onUp(ActionEvent event) {
        System.out.println("Dupa up ");
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

    private class MMSSToInt extends StringConverter<Integer> {

        @Override
        public String toString(Integer object) {

            if (object == null)
                return null;

            int minutes = object.intValue() / 60;

            int seconds = object.intValue() % 60;

            return String.format("%02d:%02d", minutes, seconds);
        }

        @Override
        public Integer fromString(String string) {
            if (string == null)
                return null;
            String[] splitted = string.split(":", 2);

            int minutes = 0;

            int seconds = 0;
            if(splitted.length == 2) {
                minutes = Integer.parseInt(splitted[0]);
                seconds = Integer.parseInt(splitted[1]);
            }
            else
            {
                seconds = Integer.parseInt(splitted[0]);
            }

            return minutes * 60 + seconds;
        }

    }

    @Override
    public <T> void setObject(T obj) throws Exception {
        if(PatternStop.class.isInstance(obj)) {
            this.patternStop_ = (PatternStop) obj;

            //FIXME: nie dziala dla 0

            StrIntMux sim = new StrIntMux();

            sim.iProperty().set(this.patternStop_.getIndex());

            this.patternStop_.indexProperty().bindBidirectional(sim.iProperty());

            this.txtIndex.textProperty().bindBidirectional(sim.sProperty());

            this.choiceStop.valueProperty().bindBidirectional(this.patternStop_.stopProperty());

            TextFormatter<Integer> travelTimeTextFormatter = new TextFormatter<>(new MMSSToInt());

            this.txtTravelTime.textFormatterProperty().set(travelTimeTextFormatter);

            travelTimeTextFormatter.valueProperty().bindBidirectional(this.patternStop_.travelTimeProperty().asObject());
        }
        else {
            // FIXME: wlasny wyjatek
            throw new Exception("blad");
        }

    }
}