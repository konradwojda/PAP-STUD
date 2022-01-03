package edu.iipw.pap.controller;

import java.io.IOException;
import java.time.LocalDate;

import edu.iipw.pap.db.Database;
import edu.iipw.pap.db.model.Calendar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ListCalendarController {
    @FXML
    private Button btnAddCalendar;

    @FXML
    private Button btnEditCalendar;

    @FXML
    private Button btnRemoveCalendar;

    @FXML
    private Button btnSearchCalendar;

    @FXML
    private TableColumn<Calendar, LocalDate> colCalendarEnd;

    @FXML
    private TableColumn<Calendar, Boolean> colCalendarFriday;

    @FXML
    private TableColumn<Calendar, Integer> colCalendarId;

    @FXML
    private TableColumn<Calendar, Boolean> colCalendarMonday;

    @FXML
    private TableColumn<Calendar, String> colCalendarName;

    @FXML
    private TableColumn<Calendar, Boolean> colCalendarSaturday;

    @FXML
    private TableColumn<Calendar, LocalDate> colCalendarStart;

    @FXML
    private TableColumn<Calendar, Boolean> colCalendarSunday;

    @FXML
    private TableColumn<Calendar, Boolean> colCalendarThursday;

    @FXML
    private TableColumn<Calendar, Boolean> colCalendarTuesday;

    @FXML
    private TableColumn<Calendar, Boolean> colCalendarWednesday;

    @FXML
    private VBox listLine;

    @FXML
    private GridPane pnCalendar;

    @FXML
    private TableView<Calendar> tblCalendar;

    private MainController mainController;

    public void refrenceMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private void refreshCalendars() {
        tblCalendar.getItems().setAll(Database.listAll(Calendar.class));
    }

    public void InitializeCalnderTable() {
        colCalendarEnd.setCellValueFactory(new PropertyValueFactory<Calendar, LocalDate>("end"));
        colCalendarFriday.setCellValueFactory(new PropertyValueFactory<Calendar, Boolean>("friday"));
        colCalendarId.setCellValueFactory(new PropertyValueFactory<Calendar, Integer>("calendarId"));
        colCalendarMonday.setCellValueFactory(new PropertyValueFactory<Calendar, Boolean>("monday"));
        colCalendarName.setCellValueFactory(new PropertyValueFactory<Calendar, String>("name"));
        colCalendarTuesday.setCellValueFactory(new PropertyValueFactory<Calendar, Boolean>("tuesday"));
        colCalendarThursday.setCellValueFactory(new PropertyValueFactory<Calendar, Boolean>("thursday"));
        colCalendarWednesday.setCellValueFactory(new PropertyValueFactory<Calendar, Boolean>("wednesday"));
        colCalendarSaturday.setCellValueFactory(new PropertyValueFactory<Calendar, Boolean>("saturday"));
        colCalendarSunday.setCellValueFactory(new PropertyValueFactory<Calendar, Boolean>("sunday"));
        colCalendarStart.setCellValueFactory(new PropertyValueFactory<Calendar, LocalDate>("start"));
        refreshCalendars();
    }

    @FXML
    void onAddCalendar(ActionEvent event) throws Exception {
        // mainController.CreatePopUp("/view/addCalendar.fxml", btnAddCalendar);
        Calendar calendar = new Calendar();
        mainController.CreatePopUpAndSetObj("/view/addCalendar.fxml", btnAddCalendar, calendar);
        refreshCalendars();
    }

    @FXML
    void onEditCalendar(ActionEvent event) {
        try {
            Calendar calendarToEdit = tblCalendar.getSelectionModel().getSelectedItem();
            mainController.CreatePopUpAndSetObj("/view/addCalendar.fxml", btnAddCalendar, calendarToEdit);
            refreshCalendars();
        } catch (Exception e) {
        }

    }

    @FXML
    void onRemoveCalendar(ActionEvent event) {
        Calendar calendarToRemove = tblCalendar.getSelectionModel().getSelectedItem();
        tblCalendar.getItems().remove(calendarToRemove);
        Database.delete(calendarToRemove);
        refreshCalendars();
    }

    @FXML
    void onSearchCalendar(ActionEvent event) {

    }

}
