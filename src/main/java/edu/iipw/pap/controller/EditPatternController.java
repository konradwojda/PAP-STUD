package edu.iipw.pap.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;

import edu.iipw.pap.db.Database;
import edu.iipw.pap.db.model.PatternStop;
import edu.iipw.pap.db.model.Pattern;
import edu.iipw.pap.db.model.PatternDirection;
import edu.iipw.pap.db.model.Trip;
import edu.iipw.pap.interfaces.IController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class EditPatternController implements Initializable, IController {
    @FXML
    private Button btnPatternOk;

    @FXML
    private ChoiceBox<PatternDirection> choiceDirection;

    @FXML
    private ListView<PatternStop> listPatternStop;

    @FXML
    private ListView<Trip> listTrip;

    @FXML
    private TextField txtLineHeadsign;

    @FXML
    private Text txtPatternError;

    private Pattern pattern_;

    @Override
    public <T> void setObject(T obj) throws Exception {
        if(Pattern.class.isInstance(obj)) {
            this.pattern_ = (Pattern) obj;

            this.txtLineHeadsign.textProperty().bindBidirectional(this.pattern_.headsignProperty());

            // FIXME: Line DIRECTION powinien byc dropdownem z dwoma wartościami

        }
        else {
            // FIXME: wlasny wyjatek
            throw new Exception("dupa");
        }
    }

    @FXML
    void onNewPatternStop(ActionEvent event) {
        PatternStop patternStop = new PatternStop();
        patternStop.setPattern(this.pattern_);
        listPatternStop.getItems().add(patternStop);
    }

    @FXML
    void onNewTrip(ActionEvent event) {
        listTrip.getItems().add(new Trip());
    }

    @FXML
    void onPatternOk(ActionEvent event) throws Exception{
        try {
            // FIXME: niewłaściwe boxy
            // pattern_.setHeadsign(txtLineCode.getText());
            pattern_.setDirection(PatternDirection.INBOUND);
            List<PatternStop> patternStops = listPatternStop.getItems();
            pattern_.setPatternStops(patternStops);
            // System.out.println(pattern_.getPatternStops());
        }
        catch (Exception e) {
            txtPatternError.setText(e.toString());
        }
        Stage stage = (Stage) btnPatternOk.getScene().getWindow();
        stage.close();
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
