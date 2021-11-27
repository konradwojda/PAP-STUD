package edu.iipw.pap;

import java.net.URL;
import java.util.ResourceBundle;

import edu.iipw.pap.db.model.Agency;
import edu.iipw.pap.db.model.LineType;
import edu.iipw.pap.db.Database;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LineController implements Initializable{
    @FXML
    private Button btnLineOk;

    @FXML
    private ChoiceBox<Agency> choiceLineAgency;

    @FXML
    private ChoiceBox<LineType> choiceLineType;

    @FXML
    private TextField txtLineCode;

    @FXML
    private TextField txtLineDescription;

    @FXML
    private Text txtStopError;

    @FXML
    void onLineOk(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        choiceLineType.getItems().setAll(LineType.values());
        choiceLineAgency.getItems().setAll(Database.listAll(Agency.class));
    }

}
