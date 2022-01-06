package edu.iipw.pap.controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ResourceBundle;

import edu.iipw.pap.db.Database;
import edu.iipw.pap.db.model.Stop;
import edu.iipw.pap.db.model.WheelchairAccessibility;
import edu.iipw.pap.interfaces.IController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

/**
 * Controller for the "create stop" popup
 */
public class AddStopController implements Initializable, IController {

    /**
     * The OK button in the popup
     */
    @FXML
    private Button btnStopOk;

    /**
     * Text box to display any errors that might occur
     * when adding a stop to the database.
     */
    @FXML
    private Text txtStopError;

    /**
     * The tri-state checkbox representing wheelchair accessibility
     * of the created stop.
     */
    @FXML
    private CheckBox checkStopWheelchairAccessible;

    /**
     * Input field for the stop name
     */
    @FXML
    private TextField txtStopName;

    /**
     * Input field for the stop code
     */
    @FXML
    private TextField txtStopCode;

    /**
     * Input field for the latitude of the stop
     */
    @FXML
    private Spinner<Double> spinStopLat;

    /**
     * Input field for the longitude of the stops
     */
    @FXML
    private Spinner<Double> spinStopLon;

    /**
     * latLonConverter is an object used by both spinners
     * to convert the user input into a Double.
     * Accepts up to six decimal digits.
     */
    private final StringConverter<Double> latLonConverter = new StringConverter<Double>() {
        private final DecimalFormat df = new DecimalFormat("#.######");

        @Override
        public String toString(Double value) {
            return value == null ? "" : df.format(value);
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

    /**
     * latInputFactory is the SpinnerValueFactory used by the * latitude input
     * spinner
     */
    private SpinnerValueFactory<Double> latInputFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(
            -90.0,
            90.0,
            0,
            0.0001);

    /**
     * lonInputFactory is the SpinnerValueFactory used by the longitude input
     * spinner
     */
    private SpinnerValueFactory<Double> lonInputFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(
            -180.0,
            180.0,
            0,
            0.0001);

    /**
     * Converts the current value of the tri-state wheelchair accessibility
     * into the WheelchairAccessibility instance.
     *
     * @return WheelchairAccessibility corresponding to the current checkbox status
     */
    private WheelchairAccessibility getAccessibilityStatus() {
        return checkStopWheelchairAccessible.isIndeterminate() ? WheelchairAccessibility.UNKNOWN
                : checkStopWheelchairAccessible.isSelected() ? WheelchairAccessibility.ACCESSIBLE
                        : WheelchairAccessibility.INACCESSIBLE;
    }

    /**
     * Event handler for the OK button
     *
     * @param event
     * @throws Exception
     */
    @FXML
    void onStopOk(ActionEvent event) throws Exception {
        try {
            // Stop stop = new Stop();
            // stop_.setName(txtStopName.getText());
            // stop_.setCode(txtStopCode.getText());
            // stop_.setLat(spinStopLat.getValue());
            // stop_.setLon(spinStopLon.getValue());
            // stop_.setWheelchairAccessible(getAccessibilityStatus());
            Database.add(stop_);

            this.txtStopName.textProperty().unbindBidirectional(this.stop_.nameProperty());

            this.txtStopCode.textProperty().unbindBidirectional(this.stop_.codeProperty());

            // Close the popup on successful entry
            Stage stage = (Stage) btnStopOk.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            this.txtStopError.setText(e.toString());
        }
    }

    /**
     * Initializes the controller by setting up all of the GUI elements.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set up the ValueConverters for spinner value factories
        latInputFactory.setConverter(latLonConverter);
        lonInputFactory.setConverter(latLonConverter);

        // Attach spinner value factories to the spinner gui elements
        spinStopLat.setValueFactory(latInputFactory);
        spinStopLon.setValueFactory(lonInputFactory);
    }

    private Stop stop_;

    @Override
    public <T> void setObject(T obj) throws Exception {
        if (Stop.class.isInstance(obj)) {
            this.stop_ = (Stop) obj;

            this.txtStopName.textProperty().bindBidirectional(this.stop_.nameProperty());

            this.txtStopCode.textProperty().bindBidirectional(this.stop_.codeProperty());

            this.spinStopLat.getValueFactory().valueProperty().bindBidirectional(this.stop_.latProperty().asObject());

            this.spinStopLon.getValueFactory().valueProperty().bindBidirectional(this.stop_.lonProperty().asObject());

            WheelchairAccessibilityMux wam = new WheelchairAccessibilityMux();

            wam.accessibleProperty().set(this.stop_.getWheelchairAccessible());

            this.stop_.wheelchairAccessibleProperty().bindBidirectional(wam.accessibleProperty());

            this.checkStopWheelchairAccessible.selectedProperty().bindBidirectional(wam.checkedProperty());

            this.checkStopWheelchairAccessible.indeterminateProperty().bindBidirectional(wam.indeterminateProperty());



        } else {
            // FIXME: wlasny wyjatek
            throw new Exception("błąd");
        }

    }

    private class WheelchairAccessibilityMux {
        private BooleanProperty indeterminate = new SimpleBooleanProperty();
        private BooleanProperty checked = new SimpleBooleanProperty();
        private ObjectProperty<WheelchairAccessibility> accessible = new SimpleObjectProperty<>();

        private ChangeListener<Boolean> accessibilitySetter = (ObservableValue<? extends Boolean> observable,
                Boolean oldValue, Boolean newValue) -> {
            setAccessibility();
        };

        private ChangeListener<WheelchairAccessibility> checkboxSetter = (
                ObservableValue<? extends WheelchairAccessibility> observable, WheelchairAccessibility oldValue,
                WheelchairAccessibility newValue) -> {
            setCheckbox();
        };

        public WheelchairAccessibilityMux() {
            indeterminate.addListener(accessibilitySetter);
            checked.addListener(accessibilitySetter);
            accessible.addListener(checkboxSetter);
        }

        private void setCheckbox() {
            switch (accessible.get()) {
                case UNKNOWN:
                    indeterminate.set(true);
                    break;
                case ACCESSIBLE:
                    indeterminate.set(false);
                    checked.set(true);
                    break;
                case INACCESSIBLE:
                    indeterminate.set(false);
                    checked.set(false);
                    break;
            }
        }

        private void setAccessibility() {
            if (indeterminate.get()) {
                accessible.set(WheelchairAccessibility.UNKNOWN);
            } else if (checked.get()) {
                accessible.set(WheelchairAccessibility.ACCESSIBLE);
            } else {
                accessible.set(WheelchairAccessibility.INACCESSIBLE);
            }
        }

        public BooleanProperty indeterminateProperty() {
            return indeterminate;
        }

        public BooleanProperty checkedProperty() {
            return checked;
        }

        public ObjectProperty<WheelchairAccessibility> accessibleProperty() {
            return accessible;
        }
    }

}
