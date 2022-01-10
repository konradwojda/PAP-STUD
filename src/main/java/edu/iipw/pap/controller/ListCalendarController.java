package edu.iipw.pap.controller;

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

/**
 * ListCalendarContoller is reposnsible for calendar display in main controller
 */
public class ListCalendarController {

    /**
     * Button to add calendar
     */
    @FXML
    private Button btnAddCalendar;

    /**
     * Button to edit calendar
     */
    @FXML
    private Button btnEditCalendar;

    /**
     * Button to remove calendar
     */
    @FXML
    private Button btnRemoveCalendar;

    /**
     * TableColumn for calendar end date
     */
    @FXML
    private TableColumn<Calendar, LocalDate> colCalendarEnd;

    /**
     * TableColumn to display if calendar is valid on fridays
     */
    @FXML
    private TableColumn<Calendar, Boolean> colCalendarFriday;

    /**
     * TableColumn to display calendar id
     */
    @FXML
    private TableColumn<Calendar, Integer> colCalendarId;

    /**
     * TableColumn to display if calendar is valid on mondays
     */
    @FXML
    private TableColumn<Calendar, Boolean> colCalendarMonday;

    /**
     * TableColumn to display calendar name
     */
    @FXML
    private TableColumn<Calendar, String> colCalendarName;

    /**
     * TableColumn to display if calendar is valid on saturdays
     */
    @FXML
    private TableColumn<Calendar, Boolean> colCalendarSaturday;

    /**
     * TableColumn for calendar start date
     */
    @FXML
    private TableColumn<Calendar, LocalDate> colCalendarStart;

    /**
     * TableColumn to display if calendar is valid on sundays
     */
    @FXML
    private TableColumn<Calendar, Boolean> colCalendarSunday;

    /**
     * TableColumn to display if calendar is valid on thursdays
     */
    @FXML
    private TableColumn<Calendar, Boolean> colCalendarThursday;

    /**
     * TableColumn to display if calendar is valid on tuesdays
     */
    @FXML
    private TableColumn<Calendar, Boolean> colCalendarTuesday;

    /**
     * TableColumn to display if calendar is valid on wednesdays
     */
    @FXML
    private TableColumn<Calendar, Boolean> colCalendarWednesday;

    @FXML
    private VBox listLine;

    @FXML
    private GridPane pnCalendar;

    /**
     * TableView to display calendar list
     */
    @FXML
    private TableView<Calendar> tblCalendar;

    /**
     * Reference to main controller
     */
    private MainController mainController;

    public void refrenceMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Refresh calendar list
     */
    private void refreshCalendars() {
        tblCalendar.getItems().setAll(Database.INSTANCE.listAll(Calendar.class));
    }

    /**
     * Initialize calendar table by setting cell value factories
     */
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

    /**
     * After clicking add - create new calendar and open editing window
     *
     * @param event
     * @throws Exception
     */
    @FXML
    void onAddCalendar(ActionEvent event) throws Exception {
        Calendar calendar = new Calendar();
        mainController.CreatePopUpAndSetObj("/view/editCalendar.fxml", btnAddCalendar, calendar);
        refreshCalendars();
    }

    /**
     * After clicking edit - set calendar and open editing window
     *
     * @param event
     * @throws Exception
     */
    @FXML
    void onEditCalendar(ActionEvent event) throws Exception {
        Calendar calendarToEdit = tblCalendar.getSelectionModel().getSelectedItem();
        if (calendarToEdit == null)
            return;
        mainController.CreatePopUpAndSetObj("/view/editCalendar.fxml", btnAddCalendar, calendarToEdit);
        refreshCalendars();
    }

    /**
     * Remove selected calendar
     *
     * @param event
     */
    @FXML
    void onRemoveCalendar(ActionEvent event) {
        Calendar calendarToRemove = tblCalendar.getSelectionModel().getSelectedItem();
        if (calendarToRemove == null)
            return;
        tblCalendar.getItems().remove(calendarToRemove);
        Database.INSTANCE.delete(calendarToRemove);
        refreshCalendars();
    }
}
