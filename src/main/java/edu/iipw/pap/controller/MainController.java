package edu.iipw.pap.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    private ListAgencyController listAgencyController;

    @FXML
    private ListLineController listLineController;

    @FXML
    private ListCalendarController listCalendarController;

    @FXML
    private ListStopController listStopController;

    public void CreatePopUp(String fxml_template, Button button) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml_template));
        VBox page = (VBox) loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(page));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(button.getScene().getWindow());
        stage.showAndWait();
    }

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

    @FXML
    private void initialize(){
        listAgencyController.refrenceMainController(this);
        listLineController.refrenceMainController(this);
        listCalendarController.refrenceMainController(this);
        listStopController.refrenceMainController(this);
    }
}
