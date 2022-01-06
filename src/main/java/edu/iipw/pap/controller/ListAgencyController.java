package edu.iipw.pap.controller;

import java.io.IOException;

import edu.iipw.pap.db.Database;
import edu.iipw.pap.db.model.Agency;
import edu.iipw.pap.interfaces.IController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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

    public void refrenceMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private void refreshAgencies() {
        tblAgency.getItems().setAll(Database.INSTANCE.listAll(Agency.class));
    }

    public void InitializeAgencyTable() {
        colAgencyId.setCellValueFactory(new PropertyValueFactory<Agency, Integer>("agencyId"));
        colAgencyName.setCellValueFactory(new PropertyValueFactory<Agency, String>("name"));
        colAgencyWebsite.setCellValueFactory(new PropertyValueFactory<Agency, String>("website"));
        colAgencyTimezone.setCellValueFactory(new PropertyValueFactory<Agency, String>("timezone"));
        colAgencyTelephone.setCellValueFactory(new PropertyValueFactory<Agency, String>("telephone"));
        refreshAgencies();
    }

    @FXML
    void onAddAgency(ActionEvent event) throws Exception {
        // mainController.CreatePopUp("/view/addAgency.fxml", btnAddAgency);
        Agency agency = new Agency();
        mainController.CreatePopUpAndSetObj("/view/editAgency.fxml", btnAddAgency, agency);
        refreshAgencies();
    }

    @FXML
    void onEditAgency(ActionEvent event) throws Exception {
        try {
            Agency agencyToEdit = tblAgency.getSelectionModel().getSelectedItem();
            mainController.CreatePopUpAndSetObj("/view/editAgency.fxml", btnAddAgency, agencyToEdit);
            refreshAgencies();
        } catch (Exception e) {
        }
    }

    @FXML
    void onRemoveAgency(ActionEvent event) {
        Agency agencyToRemove = tblAgency.getSelectionModel().getSelectedItem();
        tblAgency.getItems().remove(agencyToRemove);
        Database.INSTANCE.delete(agencyToRemove);
        refreshAgencies();
    }

    @FXML
    void onSearchAgency(ActionEvent event) {

    }

}
