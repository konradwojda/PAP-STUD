package edu.iipw.pap.controller;

import edu.iipw.pap.db.Database;
import edu.iipw.pap.db.model.Agency;
import edu.iipw.pap.exceptions.InvalidData;
import edu.iipw.pap.exceptions.InvalidObject;
import edu.iipw.pap.interfaces.IController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * EditAgencyController is responsible for controlling window while user is
 * adding or editing an agency. It implements IController to set object that is
 * being edited.
 */
public class EditAgencyController implements IController {

    /**
     * Button for confirming changes
     */
    @FXML
    private Button btnAgencyOk;

    /**
     * TextField to enter an agency name
     */
    @FXML
    private TextField txtAgencyName;

    /**
     * TextField to enter agency telephone
     */
    @FXML
    private TextField txtAgencyTelephone;

    /**
     * TextField to enter agency timezone
     */
    @FXML
    private TextField txtAgencyTimezone;

    /**
     * TextField to enter agency website
     */
    @FXML
    private TextField txtAgencyWebsite;

    /**
     * Text to display error if occurs
     */
    @FXML
    private Text txtStopError;

    /**
     * After pressing Ok button, validate input, save edited agency instance to
     * database and close stage.
     */
    @FXML
    void onAgencyOk(ActionEvent event) throws Exception {
        try {
            agency_.validateUserInput();
        } catch (InvalidData e) {
            txtStopError.setText(e.toString());
            return;
        }
        Database.INSTANCE.save(agency_);
        Stage stage = (Stage) btnAgencyOk.getScene().getWindow();
        stage.close();
    }

    /**
     * Instance of object that is being edited.
     */
    private Agency agency_;

    /**
     * Setting object that is being edited and binding properties.
     */
    @Override
    public <T> void setObject(T obj) throws InvalidObject {
        if (Agency.class.isInstance(obj)) {
            this.agency_ = (Agency) obj;
            this.txtAgencyName.textProperty().bindBidirectional(this.agency_.nameProperty());
            this.txtAgencyTelephone.textProperty().bindBidirectional(this.agency_.telephoneProperty());
            this.txtAgencyTimezone.textProperty().bindBidirectional(this.agency_.timezoneProperty());
            this.txtAgencyWebsite.textProperty().bindBidirectional(this.agency_.websiteProperty());
        } else {
            throw new InvalidObject("Niepoprawny obiekt");
        }
    }

}
