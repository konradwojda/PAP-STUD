package edu.iipw.pap.controller;

import edu.iipw.pap.db.Database;
import edu.iipw.pap.db.model.Agency;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AddAgencyController {
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
            var agency = new Agency(
                    txtAgencyName.getText(),
                    txtAgencyWebsite.getText(),
                    txtAgencyTimezone.getText(),
                    txtAgencyTelephone.getText());
            Database.add(agency);
        } catch (Exception e) {
            txtStopError.setText(e.toString());
        }

        Stage stage = (Stage) btnAgencyOk.getScene().getWindow();
        stage.close();
    }

}
