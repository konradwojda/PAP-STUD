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

/**
 * MainController is responsible for main view and switching between various
 * views
 */
public class MainController {

    /**
     * Button to switch to agency view
     */
    @FXML
    private Button btnAgency;

    /**
     * Button to switch to calendar view
     */
    @FXML
    private Button btnCalendar;

    /**
     * Button to switch to line view
     */
    @FXML
    private Button btnLine;

    /**
     * Button to switch to stop view
     */
    @FXML
    private Button btnStop;

    /**
     * Button to export database to gtfs
     */
    @FXML
    private Button btnExportToGTFS;

    /**
     * Line view box
     */
    @FXML
    private VBox listLine;

    /**
     * Stop view box
     */
    @FXML
    private VBox listStop;

    /**
     * Agency view box
     */
    @FXML
    private VBox listAgency;

    /**
     * Calendar view box
     */
    @FXML
    private VBox listCalendar;

    /**
     * Pattern Timetable view box
     */
    @FXML
    private VBox viewPatternTimetable;

    /**
     * Stop Timetable view box
     */
    @FXML
    private VBox viewStopTimetable;

    /**
     * Pane used to switching between views
     */
    @FXML
    private Pane paneBackground;

    /**
     * Reference to agency controller
     */
    @FXML
    private ListAgencyController listAgencyController;

    /**
     * Reference to line controller
     */
    @FXML
    private ListLineController listLineController;

    /**
     * Reference to calendar controller
     */
    @FXML
    private ListCalendarController listCalendarController;

    /**
     * Reference to stop controller
     */
    @FXML
    private ListStopController listStopController;

    /**
     * Reference to pattern timetable controller
     */
    @FXML
    private ViewPatternTimetableController viewPatternTimetableController;

    /**
     * Reference to stop timetable controller
     */
    @FXML
    private ViewStopTimetableController viewStopTimetableController;

    /**
     * Function responsible for creating pop ups
     *
     * @param fxml_template
     * @param button
     * @throws Exception
     */
    public void CreatePopUp(String fxml_template, Button button) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml_template));
        VBox page = (VBox) loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(page));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(button.getScene().getWindow());
        stage.showAndWait();
    }

    /**
     * Function responsible for creating pop up and setting object
     *
     * @param <T>           object to be set
     * @param fxml_template
     * @param button
     * @param obj
     * @throws InvalidObject if object is invalid type
     * @throws IOException
     */
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

    /**
     * Switch to agency view
     *
     * @param event
     */
    @FXML
    void onAgency(ActionEvent event) {
        listAgencyController.InitializeAgencyTable();
        paneBackground.toFront();
        listAgency.toFront();
    }

    /**
     * Switch to calendar view
     *
     * @param event
     */
    @FXML
    void onCalendar(ActionEvent event) {
        listCalendarController.InitializeCalnderTable();
        paneBackground.toFront();
        listCalendar.toFront();
    }

    /**
     * Switch to line view
     *
     * @param event
     */
    @FXML
    void onLine(ActionEvent event) {
        listLineController.InitializeLineTable();
        paneBackground.toFront();
        listLine.toFront();
    }

    /**
     * Switch to stop view
     *
     * @param event
     */
    @FXML
    void onStop(ActionEvent event) {
        listStopController.InitializeStopTable();
        paneBackground.toFront();
        listStop.toFront();
    }

    /**
     * Switch to stop timetable view
     *
     * @param event
     */
    @FXML
    void onStopTimetable(ActionEvent event) {
        viewStopTimetableController.InitializeStopTimetableTable();
        paneBackground.toFront();
        viewStopTimetable.toFront();
    }

    /**
     * Switch to pattern timetable view
     *
     * @param event
     */
    @FXML
    void onPatternTimetable(ActionEvent event) {
        viewPatternTimetableController.InitializePatternTimetableTable();
        paneBackground.toFront();
        viewPatternTimetable.toFront();
    }

    /**
     * Export database to GTFS
     *
     * @param event
     * @throws Exception
     */
    @FXML
    void onExportToGTFS(ActionEvent event) throws Exception {
        FileChooser fc = new FileChooser();
        fc.setTitle("Where to save the GTFS?");
        fc.getExtensionFilters().add(
                new ExtensionFilter("ZIP archive", "*.zip"));
        File f = fc.showSaveDialog(this.btnExportToGTFS.getScene().getWindow());
        if (f == null)
            return;

        try {
            String path = f.getAbsolutePath();
            if (!path.endsWith(".zip"))
                path += ".zip";
            GTFSExporter.exportToZip(path, Database.INSTANCE);
        } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR, e.toString());
            alert.showAndWait();
        }
    }

    /**
     * Initialize all controllers
     */
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
