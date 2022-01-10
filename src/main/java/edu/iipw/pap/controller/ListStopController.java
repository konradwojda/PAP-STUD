package edu.iipw.pap.controller;

import edu.iipw.pap.db.Database;
import edu.iipw.pap.db.model.Stop;
import edu.iipw.pap.db.model.WheelchairAccessibility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * ListStopController is responsible for stop view in main controller
 */
public class ListStopController {
    /**
     * Button for adding stop
     */
    @FXML
    private Button btnAddStop;

    /**
     * Button for editing stop
     */
    @FXML
    private Button btnEditStop;

    /**
     * Button for remove stop
     */
    @FXML
    private Button btnRemoveStop;

    /**
     * TableColumn for stop code
     */
    @FXML
    private TableColumn<Stop, String> colStopCode;

    /**
     * TableColumn for stop id
     */
    @FXML
    private TableColumn<Stop, Integer> colStopId;

    /**
     * TableColumn for stop latitude
     */
    @FXML
    private TableColumn<Stop, Double> colStopLat;

    /**
     * TableColumn for stop longitude
     */
    @FXML
    private TableColumn<Stop, Double> colStopLon;

    /**
     * TableColumn for stop name
     */
    @FXML
    private TableColumn<Stop, String> colStopName;

    /**
     * TableColumn to display whether stop is wheelchair accessible
     */
    @FXML
    private TableColumn<Stop, WheelchairAccessibility> colStopWheelchairAccessible;

    /**
     * TableView to display all stops
     */
    @FXML
    private TableView<Stop> tblStop;

    /**
     * Reference to main controller
     */
    private MainController mainController;

    /**
     * Binds a reference to the MainController - required for pop-up creation.
     */
    public void referMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Refresh stop table
     */
    private void refreshStops() {
        tblStop.getItems().setAll(Database.INSTANCE.listAll(Stop.class));
    }

    /**
     * Initialize table - set cell value factories
     */
    public void InitializeStopTable() {
        colStopId.setCellValueFactory(new PropertyValueFactory<Stop, Integer>("stopId"));
        colStopName.setCellValueFactory(new PropertyValueFactory<Stop, String>("name"));
        colStopCode.setCellValueFactory(new PropertyValueFactory<Stop, String>("code"));
        colStopLat.setCellValueFactory(new PropertyValueFactory<Stop, Double>("lat"));
        colStopLon.setCellValueFactory(new PropertyValueFactory<Stop, Double>("lon"));
        colStopWheelchairAccessible
                .setCellValueFactory(new PropertyValueFactory<Stop, WheelchairAccessibility>("wheelchairAccessible"));
        refreshStops();
    }

    /**
     * After clicking add - create new object and open editing window
     *
     * @param event
     * @throws Exception
     */
    @FXML
    void onAddStop(ActionEvent event) throws Exception {
        Stop stop = new Stop();
        mainController.CreatePopUpAndSetObj("/view/editStop.fxml", btnAddStop, stop);
        refreshStops();
    }

    /**
     * After clicking edit - select stop and open editing window
     *
     * @param event
     * @throws Exception
     */
    @FXML
    void onEditStop(ActionEvent event) {
        try {
            Stop stopToEdit = tblStop.getSelectionModel().getSelectedItem();
            if (stopToEdit == null)
                return;
            mainController.CreatePopUpAndSetObj("/view/editStop.fxml", btnAddStop, stopToEdit);
            refreshStops();
        } catch (Exception e) {
            assert false;
        }
    }

    /**
     * Remove selected stop
     *
     * @param event
     */
    @FXML
    void onRemoveStop(ActionEvent event) {
        Stop stopToRemove = tblStop.getSelectionModel().getSelectedItem();
        if (stopToRemove == null)
            return;
        tblStop.getItems().remove(stopToRemove);
        Database.INSTANCE.delete(stopToRemove);
        refreshStops();
    }
}
