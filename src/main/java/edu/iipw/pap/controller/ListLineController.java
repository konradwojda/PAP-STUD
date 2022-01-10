package edu.iipw.pap.controller;

import edu.iipw.pap.db.Database;
import edu.iipw.pap.db.model.Agency;
import edu.iipw.pap.db.model.Line;
import edu.iipw.pap.db.model.LineType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * ListLineController is responsible for line view in main controller
 */
public class ListLineController {

    /**
     * Button for adding line
     */
    @FXML
    private Button btnAddLine;

    /**
     * Button for editing line
     */
    @FXML
    private Button btnEditLine;

    /**
     * Button for removing line
     */
    @FXML
    private Button btnRemoveLine;

    /**
     * TableColumn for line agency
     */
    @FXML
    private TableColumn<Line, Agency> colLineAgency;

    /**
     * TableColumn for line code
     */
    @FXML
    private TableColumn<Line, String> colLineCode;

    /**
     * TableColumn for line description
     */
    @FXML
    private TableColumn<Line, String> colLineDescription;

    /**
     * TableColumn for line id
     */
    @FXML
    private TableColumn<Line, Integer> colLineId;

    /**
     * TableColumn for line type
     */
    @FXML
    private TableColumn<Line, LineType> colLineType;

    /**
     * TableView to display list of lines
     */
    @FXML
    private TableView<Line> tblLine;

    /**
     * Reference to main controller
     */
    private MainController mainController;

    public void refrenceMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Refresh line list
     */
    private void refreshLines() {
        tblLine.getItems().setAll(Database.INSTANCE.listAll(Line.class));
    }

    /**
     * Initialize table by setting cell value factories
     */
    public void InitializeLineTable() {
        colLineId.setCellValueFactory(new PropertyValueFactory<Line, Integer>("lineId"));
        colLineCode.setCellValueFactory(new PropertyValueFactory<Line, String>("code"));
        colLineDescription.setCellValueFactory(new PropertyValueFactory<Line, String>("description"));
        colLineType.setCellValueFactory(new PropertyValueFactory<Line, LineType>("type"));
        colLineAgency.setCellValueFactory(new PropertyValueFactory<Line, Agency>("agency"));
        refreshLines();
    }

    /**
     * After clicking on add - create new line and open editing window
     *
     * @param event
     * @throws Exception
     */
    @FXML
    void onAddLine(ActionEvent event) throws Exception {
        Line line = new Line();
        mainController.CreatePopUpAndSetObj("/view/editLine.fxml", btnAddLine, line);
        refreshLines();
    }

    /**
     * After clicking on edit - select line and open editing window
     *
     * @param event
     * @throws Exception
     */
    @FXML
    void onEditLine(ActionEvent event) {
        try {
            Line lineToEdit = tblLine.getSelectionModel().getSelectedItem();
            if (lineToEdit == null)
                return;
            mainController.CreatePopUpAndSetObj("/view/editLine.fxml", btnAddLine, lineToEdit);
            refreshLines();
        } catch (Exception e) {
            assert false;
        }

    }

    /**
     * Remove selected line
     *
     * @param event
     */
    @FXML
    void onRemoveLine(ActionEvent event) {
        Line lineToRemove = tblLine.getSelectionModel().getSelectedItem();
        if (lineToRemove == null)
            return;
        tblLine.getItems().remove(lineToRemove);
        Database.INSTANCE.delete(lineToRemove);
        refreshLines();
    }
}
