package edu.iipw.pap.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ListLineController {
    @FXML
    private Button btnAddLine;

    @FXML
    private Button btnEditLine;

    @FXML
    private Button btnRemoveLine;

    @FXML
    private Button btnSearchLine;

    @FXML
    private TableColumn<?, ?> colLineAgency;

    @FXML
    private TableColumn<?, ?> colLineCode;

    @FXML
    private TableColumn<?, ?> colLineDescription;

    @FXML
    private TableColumn<?, ?> colLineId;

    @FXML
    private TableColumn<?, ?> colLineType;

    @FXML
    private VBox listLine;

    @FXML
    private GridPane pnLine;

    @FXML
    private TableView<?> tblLine;

    @FXML
    void onAddLine(ActionEvent event) {
        System.out.println("ADD LINE");
    }

    @FXML
    void onEditLine(ActionEvent event) {

    }

    @FXML
    void onRemoveLine(ActionEvent event) {

    }

    @FXML
    void onSearchLine(ActionEvent event) {

    }

}
