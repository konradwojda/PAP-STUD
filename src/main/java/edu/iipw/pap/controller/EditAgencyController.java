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

public class EditAgencyController implements IController {
    @FXML
    private Button btnAgencyOk;

    @FXML
    private TextField txtAgencyName;

    @FXML
    private TextField txtAgencyTelephone;

    @FXML
    private TextField txtAgencyTimezone;

    @FXML
    private TextField txtAgencyWebsite;

    @FXML
    private Text txtStopError;

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

    private Agency agency_;

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
