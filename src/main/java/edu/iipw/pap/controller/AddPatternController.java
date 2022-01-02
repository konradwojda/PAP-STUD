package edu.iipw.pap.controller;

import java.net.URL;
import java.util.ResourceBundle;

import edu.iipw.pap.db.model.Agency;
import edu.iipw.pap.db.model.Line;
import edu.iipw.pap.db.model.LineType;
import edu.iipw.pap.db.model.Pattern;
import edu.iipw.pap.db.model.PatternDirection;
import edu.iipw.pap.db.model.PatternStop;
import edu.iipw.pap.db.model.Stop;
import edu.iipw.pap.db.model.WheelchairAccessibility;
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

public class AddPatternController implements Initializable{


    @FXML
    private Button btnNewPatternStop;

    @FXML
    private Button btnPatternOk;

    @FXML
    private ChoiceBox<?> choiceLineAgency;

    @FXML
    private ListView listPatternStop;

    @FXML
    private TextField txtLineCode;

    @FXML
    private TextField txtLineDescription;

    @FXML
    private Text txtStopError;

    @FXML
    void onNewPatterStop(ActionEvent event) {
        // TODO: tu trzeba dodać snawnowanie sie nowych tabelek
    }

    @FXML
    void onPatternOk(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        WheelchairAccessibility wAccessibility = WheelchairAccessibility.ACCESSIBLE;
        Stop s1 = new Stop("XD", "12", 0.1, 0.2, wAccessibility);
        LineType lineType = LineType.TRAM;
        Agency a1 = new Agency("aa", "bb", "UHC", "1234");
        Line l1 = new Line("1", "WIN", lineType, a1);
        PatternDirection patternDirection = PatternDirection.INBOUND;
        Pattern p1 = new Pattern("XDDDD", patternDirection, l1);
        listPatternStop.getItems().add(new PatternStop(s1, p1, 4, 12));
        listPatternStop.setCellFactory(new Callback<ListView<PatternStop>, ListCell<PatternStop>>()
        {
            @Override
            public ListCell call(ListView param)
            {
                return new PatternStopCell();
            }

        });
    }
}
