package edu.iipw.pap.controller;

import java.net.URL;
import java.util.ResourceBundle;

import edu.iipw.pap.db.model.PatternStop;
import edu.iipw.pap.db.model.Trip;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class AddPatternController implements Initializable {

    @FXML
    private Button btnNewPatternStop;

    @FXML
    private Button btnNewTrip;

    @FXML
    private Button btnPatternOk;

    @FXML
    private ListView<PatternStop> listPatternStop;

    @FXML
    private ListView<Trip> listTrip;

    @FXML
    private TextField txtLineCode;

    @FXML
    private TextField txtLineDescription;

    @FXML
    private Text txtStopError;

    @FXML
    void onNewPatternStop(ActionEvent event) {
        listPatternStop.getItems().add(new PatternStop());
    }

    @FXML
    void onNewTrip(ActionEvent event) {
        listTrip.getItems().add(new Trip());
    }

    @FXML
    void onPatternOk(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listPatternStop.setCellFactory(new Callback<ListView<PatternStop>, ListCell<PatternStop>>() {
            @Override
            public ListCell<PatternStop> call(ListView<PatternStop> param) {
                return new PatternStopCell();
            }
        });

        listTrip.setCellFactory(new Callback<ListView<Trip>, ListCell<Trip>>() {
            @Override
            public ListCell<Trip> call(ListView<Trip> param) {
                return new TripCell();
            }
        });
    }
}
