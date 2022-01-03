package edu.iipw.pap.controller;

import edu.iipw.pap.db.Database;
import edu.iipw.pap.db.model.Agency;
import edu.iipw.pap.interfaces.IController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AddAgencyController implements IController{
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
            Agency agency = new Agency();
            agency.setName(txtAgencyName.getText());
            agency.setWebsite(txtAgencyWebsite.getText());
            agency.setTimezone(txtAgencyTimezone.getText());
            agency.setTelephone(txtAgencyTelephone.getText());
            Database.add(agency);
        } catch (Exception e) {
            txtStopError.setText(e.toString());
        }

        Stage stage = (Stage) btnAgencyOk.getScene().getWindow();
        stage.close();
    }

    private Agency agency_;

    @Override
    public <T> void setObject(T obj) throws Exception{
        if(Agency.class.isInstance(obj))
        {
            this.agency_ = (Agency) obj;
        }
        else {
            //FIXME: wlasny wyjatek
            throw new Exception("bład");
        }
    }

}
