package edu.iipw.pap.controller;

import java.io.IOException;

import edu.iipw.pap.db.Database;
import edu.iipw.pap.db.model.Stop;
import edu.iipw.pap.db.model.WheelchairAccessibility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ListStopController {
    @FXML
    private Button btnAddStop;

    @FXML
    private Button btnEditStop;

    @FXML
    private Button btnRemoveStop;

    @FXML
    private Button btnSearchStop;

    @FXML
    private TableColumn<Stop, String> colStopCode;

    @FXML
    private TableColumn<Stop, Integer> colStopId;

    @FXML
    private TableColumn<Stop, Double> colStopLat;

    @FXML
    private TableColumn<Stop, Double> colStopLon;

    @FXML
    private TableColumn<Stop, String> colStopName;

    @FXML
    private TableColumn<Stop, WheelchairAccessibility> colStopWheelchairAccessible;

    @FXML
    private VBox listStop;

    @FXML
    private GridPane pnStop;

    @FXML
    private TableView<Stop> tblStop;

    private MainController mainController;

    public void refrenceMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private void refreshStops() {
        tblStop.getItems().setAll(Database.INSTANCE.listAll(Stop.class));
    }

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

    @FXML
    void onAddStop(ActionEvent event) throws Exception {
        // mainController.CreatePopUp("/view/addStop.fxml", btnAddStop);
        Stop stop = new Stop();
        mainController.CreatePopUpAndSetObj("/view/addStop.fxml", btnAddStop, stop);
        refreshStops();
    }

    @FXML
    void onEditStop(ActionEvent event) {
        try {
            Stop stopToEdit = tblStop.getSelectionModel().getSelectedItem();
            mainController.CreatePopUpAndSetObj("/view/addStop.fxml", btnAddStop, stopToEdit);
            refreshStops();
        } catch (Exception e) {
        }
    }

    @FXML
    void onRemoveStop(ActionEvent event) {
        Stop stopToRemove = tblStop.getSelectionModel().getSelectedItem();
        tblStop.getItems().remove(stopToRemove);
        Database.INSTANCE.delete(stopToRemove);
        refreshStops();
    }

    @FXML
    void onSearchStop(ActionEvent event) {

    }
}
