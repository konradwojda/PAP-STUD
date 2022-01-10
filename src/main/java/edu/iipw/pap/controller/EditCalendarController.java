package edu.iipw.pap.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import edu.iipw.pap.db.Database;
import edu.iipw.pap.db.model.Calendar;
import edu.iipw.pap.exceptions.InvalidData;
import edu.iipw.pap.exceptions.InvalidObject;
import edu.iipw.pap.interfaces.IController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.text.Text;
import javafx.stage.Stage;
// import net.bytebuddy.asm.Advice.Local;
import javafx.util.StringConverter;

/**
 * EditCalendarController is responsible for controlling window while user is
 * adding or editing a calendar. It implements IController to set object that is
 * being edited.
 */
public class EditCalendarController implements IController {

    /**
     * Button for confirming changes
     */
    @FXML
    private Button btnCalendarOk;

    /**
     * Checkbox to enter whether the calendar is valid on Fridays
     */
    @FXML
    private CheckBox checkFriday;

    /**
     * Checkbox to enter whether the calendar is valid on Mondays
     */
    @FXML
    private CheckBox checkMonday;

    /**
     * Checkbox to enter whether the calendar is valid on Saturdays
     */
    @FXML
    private CheckBox checkSaturday;

    /**
     * Checkbox to enter whether the calendar is valid on Sundays
     */
    @FXML
    private CheckBox checkSunday;

    /**
     * Checkbox to enter whether the calendar is valid on Thursdays
     */
    @FXML
    private CheckBox checkThusday;

    /**
     * Checkbox to enter whether the calendar is valid on Tuesdays
     */
    @FXML
    private CheckBox checkTuesday;

    /**
     * Checkbox to enter whether the calendar is valid on Wednesdays
     */
    @FXML
    private CheckBox checkWednesday;

    /**
     * TextField to enter calendar end date.
     */
    @FXML
    private TextField txtCalendarEnd;

    /**
     * Text to display error if occurs
     */
    @FXML
    private Text txtCalendarError;

    /**
     * TextField to enter calendar name
     */
    @FXML
    private TextField txtCalendarName;

    /**
     * TextField to enter calendar start date
     */
    @FXML
    private TextField txtCalendarStart;

    /**
     * After pressing Ok button, validate input, save edited calendar instance to
     * database and close stage.
     */
    @FXML
    void onCalendarOk(ActionEvent event) throws Exception {
        try {
            calendar_.validateUserInput();
        } catch (InvalidData e) {
            txtCalendarError.setText(e.toString());
            return;
        }
        Database.INSTANCE.save(calendar_);
        Stage stage = (Stage) btnCalendarOk.getScene().getWindow();
        stage.close();
    }

    /**
     * Converts user input to LocalDate object, and LocalDate object to String
     */
    private class StringToDate extends StringConverter<LocalDate> {

        @Override
        public String toString(LocalDate object) {
            if (object == null)
                return null;
            return object.format(DateTimeFormatter.ISO_LOCAL_DATE);
        }

        @Override
        public LocalDate fromString(String string) {
            if (string == null)
                return null;
            return LocalDate.parse(string, DateTimeFormatter.ISO_LOCAL_DATE);
        }

    }

    /**
     * TextFormatter for start date
     */
    final TextFormatter<LocalDate> startDateTextFormatter = new TextFormatter<>(new StringToDate());

    /**
     * TextFormatter for end date
     */
    final TextFormatter<LocalDate> endDateTextFormatter = new TextFormatter<>(new StringToDate());

    /**
     * Instance of calendar object that is being edited
     */
    private Calendar calendar_;

    /**
     * Setting object that is being edited and binding properties.
     */
    @Override
    public <T> void setObject(T obj) throws InvalidObject {
        if (Calendar.class.isInstance(obj)) {
            this.calendar_ = (Calendar) obj;
            this.txtCalendarName.textProperty().bindBidirectional(this.calendar_.nameProperty());
            this.txtCalendarStart.textFormatterProperty().set(startDateTextFormatter);
            startDateTextFormatter.valueProperty().bindBidirectional(this.calendar_.startProperty());
            this.txtCalendarEnd.textFormatterProperty().set(endDateTextFormatter);
            endDateTextFormatter.valueProperty().bindBidirectional(this.calendar_.endProperty());
            this.checkMonday.selectedProperty().bindBidirectional(this.calendar_.mondayProperty());
            this.checkTuesday.selectedProperty().bindBidirectional(this.calendar_.tuesdayProperty());
            this.checkWednesday.selectedProperty().bindBidirectional(this.calendar_.wednesdayProperty());
            this.checkThusday.selectedProperty().bindBidirectional(this.calendar_.thursdayProperty());
            this.checkFriday.selectedProperty().bindBidirectional(this.calendar_.fridayProperty());
            this.checkSaturday.selectedProperty().bindBidirectional(this.calendar_.saturdayProperty());
            this.checkSunday.selectedProperty().bindBidirectional(this.calendar_.sundayProperty());
        } else {
            throw new InvalidObject("Niepoprawny obiekt");
        }

    }
}
