package edu.iipw.pap;

import edu.iipw.pap.db.model.WheelchairAccessibility;
import edu.iipw.pap.db.model.Stop;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StopController {

    @FXML
    private Button btnStopOk;

    @FXML
    private CheckBox checkStopWheelchairAccessible;

    @FXML
    private TextField txtStopCode;

    @FXML
    private Text txtStopError;

    @FXML
    private Spinner<Double> spinStopLat;

    @FXML
    private Spinner<Double> spinStopLon;

    @FXML
    private TextField txtStopName;

    @FXML
    void onStopOk(ActionEvent event) throws Exception{
        WheelchairAccessibility wca = WheelchairAccessibility.UNKNOWN;
        if(checkStopWheelchairAccessible.isIndeterminate())
        {
            wca = WheelchairAccessibility.UNKNOWN;
        }
        else if(!checkStopWheelchairAccessible.isIndeterminate() && checkStopWheelchairAccessible.isSelected())
        {
            wca = WheelchairAccessibility.ACCESSIBLE;
        }
        else if(!checkStopWheelchairAccessible.isIndeterminate() && !checkStopWheelchairAccessible.isSelected())
        {
            wca = WheelchairAccessibility.INACCESSIBLE;
        }
        try
        {
            // var stop = new Stop(this.txtStopName.getText(), this.txtStopCode.getText(),
            //     Double.valueOf(this.txtStopLat.getText()), Double.valueOf(this.txtStopLon.getText()), wca);
            //save_to_db(stop);
            //refresh list;
        }
        catch (Exception e)
        {
            this.txtStopError.setText(e.toString());
        }
        Stage stage = (Stage) btnStopOk.getScene().getWindow();
        stage.close();

    }

}
