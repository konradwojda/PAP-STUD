package edu.iipw.pap.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import edu.iipw.pap.db.Database;
import edu.iipw.pap.db.model.Calendar;
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

public class AddCalendarController implements IController {
    @FXML
    private Button btnCalendarOk;

    @FXML
    private CheckBox checkFriday;

    @FXML
    private CheckBox checkMonday;

    @FXML
    private CheckBox checkSaturday;

    @FXML
    private CheckBox checkSunday;

    @FXML
    private CheckBox checkThusday;

    @FXML
    private CheckBox checkTuesday;

    @FXML
    private CheckBox checkWednesday;

    @FXML
    private TextField txtCalendarEnd;

    @FXML
    private Text txtCalendarError;

    @FXML
    private TextField txtCalendarName;

    @FXML
    private TextField txtCalendarStart;

    @FXML
    void onCalendarOk(ActionEvent event) throws Exception {
        try {
            // String startDateStr = txtCalendarStart.getText();
            // LocalDate startDate = startDateStr.isEmpty() ? null
            //         : LocalDate.parse(startDateStr, DateTimeFormatter.ISO_LOCAL_DATE);

            // String endDateStr = txtCalendarEnd.getText();
            // LocalDate endDate = endDateStr.isEmpty() ? null
            //         : LocalDate.parse(endDateStr, DateTimeFormatter.ISO_LOCAL_DATE);

            // Calendar calendar = new Calendar();
            // calendar.setName(txtCalendarName.getText());
            // calendar.setStart(startDate);
            // calendar.setEnd(endDate);
            // calendar.setMonday(checkMonday.isSelected());
            // calendar.setTuesday(checkTuesday.isSelected());
            // calendar.setThursday(checkWednesday.isSelected());
            // calendar.setTuesday(checkThusday.isSelected());
            // calendar.setFriday(checkFriday.isSelected());
            // calendar.setSaturday(checkSaturday.isSelected());
            // calendar.setSunday(checkSunday.isSelected());
            Database.add(calendar_);
        } catch (Exception e) {
            txtCalendarError.setText(e.toString());
        }
        Stage stage = (Stage) btnCalendarOk.getScene().getWindow();
        stage.close();
    }

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

    final TextFormatter<LocalDate> startDateTextFormatter = new TextFormatter<>(new StringToDate());

    final TextFormatter<LocalDate> endDateTextFormatter = new TextFormatter<>(new StringToDate());


    private Calendar calendar_;

    @Override
    public <T> void setObject(T obj) throws Exception {
        if (Calendar.class.isInstance(obj)) {
            this.calendar_ = (Calendar) obj;
            this.txtCalendarName.textProperty().bindBidirectional(this.calendar_.nameProperty());

            // nie propaguje się na okno niżej

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
            // FIXME: wlasny wyjatek
            throw new Exception("błąd");
        }

    }
}
