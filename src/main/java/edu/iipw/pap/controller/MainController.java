package edu.iipw.pap.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MainController {
    @FXML
    private Button btnAgency;

    @FXML
    private Button btnCalendar;

    @FXML
    private Button btnLine;

    @FXML
    private Button btnStop;

    @FXML
    private VBox listLine;

    @FXML
    private VBox listStop;

    @FXML
    private VBox listAgency;

    @FXML
    private VBox listCalendar;

    @FXML
    void onAgency(ActionEvent event) {
        listAgency.toFront();
    }

    @FXML
    void onCalendar(ActionEvent event) {
        listCalendar.toFront();
    }

    @FXML
    void onLine(ActionEvent event) {
        listLine.toFront();
    }

    @FXML
    void onStop(ActionEvent event) {
        listStop.toFront();
    }
}
