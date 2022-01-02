package edu.iipw.pap.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class AddPatternController {


    @FXML
    private Button btnNewPatternStop;

    @FXML
    private Button btnPatternOk;

    @FXML
    private ChoiceBox<?> choiceLineAgency;

    @FXML
    private ListView<?> listPatternStop;

    @FXML
    private TextField txtLineCode;

    @FXML
    private TextField txtLineDescription;

    @FXML
    private Text txtStopError;

    @FXML
    void onNewPatterStop(ActionEvent event) {

    }

    @FXML
    void onPatternOk(ActionEvent event) {

    }
}
