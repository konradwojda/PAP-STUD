package edu.iipw.pap;

import edu.iipw.pap.db.model.WheelchairAccessibility;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ResourceBundle;

import edu.iipw.pap.db.Database;
import edu.iipw.pap.db.model.Stop;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class StopController implements Initializable {

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
    void onStopOk(ActionEvent event) throws Exception {
        WheelchairAccessibility wca = WheelchairAccessibility.UNKNOWN;
        if (checkStopWheelchairAccessible.isIndeterminate()) {
            wca = WheelchairAccessibility.UNKNOWN;
        } else if (!checkStopWheelchairAccessible.isIndeterminate() && checkStopWheelchairAccessible.isSelected()) {
            wca = WheelchairAccessibility.ACCESSIBLE;
        } else if (!checkStopWheelchairAccessible.isIndeterminate() && !checkStopWheelchairAccessible.isSelected()) {
            wca = WheelchairAccessibility.INACCESSIBLE;
        }

        try {
            var stop = new Stop(
                this.txtStopName.getText(),
                this.txtStopCode.getText(),
                spinStopLat.getValue(),
                spinStopLon.getValue(),
                wca
            );
            Database.add(stop);
            // refresh list;
        } catch (Exception e) {
            this.txtStopError.setText(e.toString());
        }
        Stage stage = (Stage) btnStopOk.getScene().getWindow();
        stage.close();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SpinnerValueFactory<Double> doubleLatFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(-90.0, 90.0, 0,
                0.0001);
        SpinnerValueFactory<Double> doubleLonFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(-180.0, 180.0,
                0, 0.0001);

        var doubleConverter = new StringConverter<Double>() {
            private final DecimalFormat df = new DecimalFormat("#.######");

            @Override
            public String toString(Double value) {
                // If the specified value is null, return a zero-length String
                if (value == null) {
                    return "";
                }

                return df.format(value);
            }

            @Override
            public Double fromString(String value) {
                try {
                    // If the specified value is null or zero-length, return null
                    if (value == null) {
                        return null;
                    }

                    value = value.trim();

                    if (value.length() < 1) {
                        return null;
                    }

                    // Perform the requested parsing
                    return df.parse(value).doubleValue();
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };

        doubleLatFactory.setConverter(doubleConverter);
        doubleLonFactory.setConverter(doubleConverter);

        spinStopLat.setValueFactory(doubleLatFactory);
        spinStopLon.setValueFactory(doubleLonFactory);
    }

}
