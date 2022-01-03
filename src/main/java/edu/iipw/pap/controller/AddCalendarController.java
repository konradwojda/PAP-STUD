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
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
            String startDateStr = txtCalendarStart.getText();
            LocalDate startDate = startDateStr.isEmpty() ? null
                    : LocalDate.parse(startDateStr, DateTimeFormatter.ISO_LOCAL_DATE);

            String endDateStr = txtCalendarEnd.getText();
            LocalDate endDate = endDateStr.isEmpty() ? null
                    : LocalDate.parse(endDateStr, DateTimeFormatter.ISO_LOCAL_DATE);

            Calendar calendar = new Calendar();
            calendar.setName(txtCalendarName.getText());
            calendar.setStart(startDate);
            calendar.setEnd(endDate);
            calendar.setMonday(checkMonday.isSelected());
            calendar.setTuesday(checkTuesday.isSelected());
            calendar.setThursday(checkWednesday.isSelected());
            calendar.setTuesday(checkThusday.isSelected());
            calendar.setFriday(checkFriday.isSelected());
            calendar.setSaturday(checkSaturday.isSelected());
            calendar.setSunday(checkSunday.isSelected());
            Database.add(calendar);
        } catch (Exception e) {
            txtCalendarError.setText(e.toString());
        }
        Stage stage = (Stage) btnCalendarOk.getScene().getWindow();
        stage.close();
    }

    private Calendar calendar_;

    @Override
    public <T> void setObject(T obj) throws Exception {
        if (Calendar.class.isInstance(obj)) {
            this.calendar_ = (Calendar) obj;
            this.txtCalendarName.setText(this.calendar_.getName());
            if (this.calendar_.getStart() != null)
                this.txtCalendarStart.setText(this.calendar_.getStart().toString());
            if (this.calendar_.getEnd() != null)
                this.txtCalendarEnd.setText(this.calendar_.getEnd().toString());
            this.checkMonday.setSelected(this.calendar_.getMonday());
            this.checkTuesday.setSelected(this.calendar_.getTuesday());
            this.checkWednesday.setSelected(this.calendar_.getWednesday());
            this.checkThusday.setSelected(this.calendar_.getThursday());
            this.checkFriday.setSelected(this.calendar_.getFriday());
            this.checkSaturday.setSelected(this.calendar_.getSaturday());
            this.checkSunday.setSelected(this.calendar_.getSunday());
        } else {
            // FIXME: wlasny wyjatek
            throw new Exception("błąd");
        }

    }
}
