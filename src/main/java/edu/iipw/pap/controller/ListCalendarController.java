package edu.iipw.pap.controller;

import java.io.IOException;
import java.time.LocalDate;

import edu.iipw.pap.db.model.Calendar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private TableColumn<Calendar, Boolean> colCalendarThusday;

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

    public void refrenceMainController(MainController mainController){
        this.mainController = mainController;
    }

    @FXML
    void onAddCalendar(ActionEvent event) throws IOException {
        mainController.CreatePopUp("/view/addCalendar.fxml", btnAddCalendar);
    }

    @FXML
    void onEditCalendar(ActionEvent event) {

    }

    @FXML
    void onRemoveCalendar(ActionEvent event) {

    }

    @FXML
    void onSearchCalendar(ActionEvent event) {

    }


}
