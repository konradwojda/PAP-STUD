package edu.iipw.pap.controller;

import java.io.IOException;

import edu.iipw.pap.db.model.Stop;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class PatternViewController extends HBox{
    @FXML
    private Button btnRemovePatternStop;

    @FXML
    private ChoiceBox<Stop> choiceStop;

    @FXML
    private TextField txtHour;

    @FXML
    private Text txtIndex;

    @FXML
    private TextField txtMinutes;

    @FXML
    void onRemovePatternStop(ActionEvent event) {

    }

    public PatternViewController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/gui.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try{
            fxmlLoader.load();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
