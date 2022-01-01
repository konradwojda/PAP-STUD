package edu.iipw.pap.controller;

import edu.iipw.pap.db.model.Stop;
import edu.iipw.pap.db.model.WheelchairAccessibility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

    @FXML
    void onAddStop(ActionEvent event) {

    }

    @FXML
    void onEditStop(ActionEvent event) {

    }

    @FXML
    void onRemoveStop(ActionEvent event) {

    }

    @FXML
    void onSearchStop(ActionEvent event) {

    }
}
