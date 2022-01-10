package edu.iipw.pap.controller;

import edu.iipw.pap.db.Database;
import edu.iipw.pap.db.model.Agency;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * ListAgencyController is responsible for agency view in main controller
 */
public class ListAgencyController {
    /**
     * Button to add new agency
     */
    @FXML
    private Button btnAddAgency;

    /**
     * Button to edit existing agency
     */
    @FXML
    private Button btnEditAgency;

    /**
     * Button to remove existing agency
     */
    @FXML
    private Button btnRemoveAgency;

    /**
     * TableColumn for agency id
     */
    @FXML
    private TableColumn<Agency, Integer> colAgencyId;

    /**
     * TableColumn for agency name
     */
    @FXML
    private TableColumn<Agency, String> colAgencyName;

    /**
     * TableColumn for agency telephone
     */
    @FXML
    private TableColumn<Agency, String> colAgencyTelephone;

    /**
     * TableColumn for agency timezone
     */
    @FXML
    private TableColumn<Agency, String> colAgencyTimezone;

    /**
     * TableColumn for agency website
     */
    @FXML
    private TableColumn<Agency, String> colAgencyWebsite;

    @FXML
    private VBox viewAgency;

    @FXML
    private GridPane pnAgency;

    /**
     * TableView for list of agencies
     */
    @FXML
    private TableView<Agency> tblAgency;

    /**
     * Reference to main controller
     */
    private MainController mainController;

    public void refrenceMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Refresh agencies list
     */
    private void refreshAgencies() {
        tblAgency.getItems().setAll(Database.INSTANCE.listAll(Agency.class));
    }

    /**
     * Initialize table by setting cell value factories
     */
    public void InitializeAgencyTable() {
        colAgencyId.setCellValueFactory(new PropertyValueFactory<Agency, Integer>("agencyId"));
        colAgencyName.setCellValueFactory(new PropertyValueFactory<Agency, String>("name"));
        colAgencyWebsite.setCellValueFactory(new PropertyValueFactory<Agency, String>("website"));
        colAgencyTimezone.setCellValueFactory(new PropertyValueFactory<Agency, String>("timezone"));
        colAgencyTelephone.setCellValueFactory(new PropertyValueFactory<Agency, String>("telephone"));
        refreshAgencies();
    }

    /**
     * After clicking add - create new agency and open new window
     *
     * @param event
     * @throws Exception
     */
    @FXML
    void onAddAgency(ActionEvent event) throws Exception {
        Agency agency = new Agency();
        mainController.CreatePopUpAndSetObj("/view/editAgency.fxml", btnAddAgency, agency);
        refreshAgencies();
    }

    /**
     * After clicking edit - set agency to edit and open new window
     *
     * @param event
     * @throws Exception
     */
    @FXML
    void onEditAgency(ActionEvent event) throws Exception {
        Agency agencyToEdit = tblAgency.getSelectionModel().getSelectedItem();
        if (agencyToEdit == null)
            return;
        mainController.CreatePopUpAndSetObj("/view/editAgency.fxml", btnAddAgency, agencyToEdit);
        refreshAgencies();
    }

    /**
     * Remove selected agency
     *
     * @param event
     */
    @FXML
    void onRemoveAgency(ActionEvent event) {
        Agency agencyToRemove = tblAgency.getSelectionModel().getSelectedItem();
        if (agencyToRemove == null)
            return;
        tblAgency.getItems().remove(agencyToRemove);
        Database.INSTANCE.delete(agencyToRemove);
        refreshAgencies();
    }
}
