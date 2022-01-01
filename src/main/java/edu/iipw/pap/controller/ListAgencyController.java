package edu.iipw.pap.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ListAgencyController {
    @FXML
    private Button btnAddAgency;

    @FXML
    private Button btnEditAgency;

    @FXML
    private Button btnRemoveAgency;

    @FXML
    private Button btnSearchAgency;

    @FXML
    private TableColumn<?, ?> colAgencyId;

    @FXML
    private TableColumn<?, ?> colAgencyName;

    @FXML
    private TableColumn<?, ?> colAgencyTelephone;

    @FXML
    private TableColumn<?, ?> colAgencyTimezone;

    @FXML
    private TableColumn<?, ?> colAgencyWebsite;

    @FXML
    private VBox listAgency;

    @FXML
    private GridPane pnLine;

    @FXML
    private TableView<?> tblAgency;

    @FXML
    void onAddAgency(ActionEvent event) {

    }

    @FXML
    void onEditAgency(ActionEvent event) {

    }

    @FXML
    void onRemoveAgency(ActionEvent event) {

    }

    @FXML
    void onSearchAgency(ActionEvent event) {

    }

}
