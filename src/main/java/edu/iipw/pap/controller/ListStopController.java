package edu.iipw.pap.controller;

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
    private TableColumn<?, ?> colStopCode;

    @FXML
    private TableColumn<?, ?> colStopId1;

    @FXML
    private TableColumn<?, ?> colStopLat;

    @FXML
    private TableColumn<?, ?> colStopLon;

    @FXML
    private TableColumn<?, ?> colStopName;

    @FXML
    private TableColumn<?, ?> colStopWheelchairAccessible;

    @FXML
    private VBox listStop;

    @FXML
    private GridPane pnLine;

    @FXML
    private TableView<?> tblLine;

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
