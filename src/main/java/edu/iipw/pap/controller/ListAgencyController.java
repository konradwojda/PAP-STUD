package edu.iipw.pap.controller;

import java.io.IOException;

import edu.iipw.pap.db.model.Agency;
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
    private TableColumn<Agency, Integer> colAgencyId;

    @FXML
    private TableColumn<Agency, String> colAgencyName;

    @FXML
    private TableColumn<Agency, String> colAgencyTelephone;

    @FXML
    private TableColumn<Agency, String> colAgencyTimezone;

    @FXML
    private TableColumn<Agency, String> colAgencyWebsite;

    @FXML
    private VBox listAgency;

    @FXML
    private GridPane pnAgency;

    @FXML
    private TableView<Agency> tblAgency;

    private MainController mainController;

    public void refrenceMainController(MainController mainController){
        this.mainController = mainController;
    }

    @FXML
    void onAddAgency(ActionEvent event) throws IOException {
        mainController.CreatePopUp("/view/addAgency.fxml", btnAddAgency);
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
