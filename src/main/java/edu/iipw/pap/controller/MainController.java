package edu.iipw.pap.controller;

import java.io.File;
import java.io.IOException;

import edu.iipw.pap.GTFSExporter;
import edu.iipw.pap.db.Database;
import edu.iipw.pap.exceptions.InvalidObject;
import edu.iipw.pap.interfaces.IController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

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
    private Button btnExportToGTFS;

    @FXML
    private VBox listLine;

    @FXML
    private VBox listStop;

    @FXML
    private VBox listAgency;

    @FXML
    private VBox listCalendar;

    @FXML
    private VBox viewPatternTimetable;

    @FXML
    private VBox viewStopTimetable;

    @FXML
    private Pane paneBackground;

    @FXML
    private ListAgencyController listAgencyController;

    @FXML
    private ListLineController listLineController;

    @FXML
    private ListCalendarController listCalendarController;

    @FXML
    private ListStopController listStopController;

    @FXML
    private ViewPatternTimetableController viewPatternTimetableController;

    @FXML
    private ViewStopTimetableController viewStopTimetableController;

    public void CreatePopUp(String fxml_template, Button button) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml_template));
        VBox page = (VBox) loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(page));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(button.getScene().getWindow());
        stage.showAndWait();
    }

    public <T> void CreatePopUpAndSetObj(String fxml_template, Button button, T obj) throws InvalidObject, IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml_template));
        VBox page = (VBox) loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(page));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(button.getScene().getWindow());
        IController controller = loader.getController();
        controller.setObject(obj);
        stage.showAndWait();
    }

    @FXML
    void onAgency(ActionEvent event) {
        listAgencyController.InitializeAgencyTable();
        paneBackground.toFront();
        listAgency.toFront();
    }

    @FXML
    void onCalendar(ActionEvent event) {
        listCalendarController.InitializeCalnderTable();
        paneBackground.toFront();
        listCalendar.toFront();
    }

    @FXML
    void onLine(ActionEvent event) {
        listLineController.InitializeLineTable();
        paneBackground.toFront();
        listLine.toFront();
    }

    @FXML
    void onStop(ActionEvent event) {
        listStopController.InitializeStopTable();
        paneBackground.toFront();
        listStop.toFront();
    }

    @FXML
    void onStopTimetable(ActionEvent event) {
        viewStopTimetableController.InitializeStopTimetableTable();
        paneBackground.toFront();
        viewStopTimetable.toFront();
    }

    @FXML
    void onPatternTimetable(ActionEvent event) {
        viewPatternTimetableController.InitializePatternTimetableTable();
        paneBackground.toFront();
        viewPatternTimetable.toFront();
    }

    @FXML
    void onExportToGTFS(ActionEvent event) throws Exception {
        FileChooser fc = new FileChooser();
        fc.setTitle("Where to save the GTFS?");
        fc.getExtensionFilters().add(
            new ExtensionFilter("ZIP archive", "*.zip")
        );
        File f = fc.showSaveDialog(this.btnExportToGTFS.getScene().getWindow());
        if (f == null) return;

        try {
            String path = f.getAbsolutePath();
            if(!path.endsWith(".zip"))
                path += ".zip";
            GTFSExporter.exportToZip(path, Database.INSTANCE);
        } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR, e.toString());
            alert.showAndWait();
        }
    }

    @FXML
    private void initialize() {
        listAgencyController.refrenceMainController(this);
        listLineController.refrenceMainController(this);
        listCalendarController.refrenceMainController(this);
        listStopController.refrenceMainController(this);

        listAgencyController.InitializeAgencyTable();
        listCalendarController.InitializeCalnderTable();
        listLineController.InitializeLineTable();
        listStopController.InitializeStopTable();
        listAgencyController.InitializeAgencyTable();
        viewPatternTimetableController.InitializePatternTimetableTable();
        viewStopTimetableController.InitializeStopTimetableTable();
    }
}
