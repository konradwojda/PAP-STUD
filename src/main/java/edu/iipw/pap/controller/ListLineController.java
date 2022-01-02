package edu.iipw.pap.controller;

import java.io.IOException;

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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ListLineController {
    @FXML
    private Button btnAddLine;

    @FXML
    private Button btnEditLine;

    @FXML
    private Button btnRemoveLine;

    @FXML
    private Button btnSearchLine;

    @FXML
    private TableColumn<Line, Agency> colLineAgency;

    @FXML
    private TableColumn<Line, String> colLineCode;

    @FXML
    private TableColumn<Line, String> colLineDescription;

    @FXML
    private TableColumn<Line, Integer> colLineId;

    @FXML
    private TableColumn<Line, LineType> colLineType;

    @FXML
    private VBox listLine;

    @FXML
    private GridPane pnLine;

    @FXML
    private TableView<Line> tblLine;

    private MainController mainController;

    public void refrenceMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private void refreshLines() {
        tblLine.getItems().setAll(Database.listAll(Line.class));
    }

    public void InitializeLineTable() {
        colLineId.setCellValueFactory(new PropertyValueFactory<Line, Integer>("lineId"));
        colLineCode.setCellValueFactory(new PropertyValueFactory<Line, String>("code"));
        colLineDescription.setCellValueFactory(new PropertyValueFactory<Line, String>("description"));
        colLineType.setCellValueFactory(new PropertyValueFactory<Line, LineType>("type"));
        colLineAgency.setCellValueFactory(new PropertyValueFactory<Line, Agency>("agency"));
        refreshLines();
    }

    @FXML
    void onAddLine(ActionEvent event) throws IOException {
        mainController.CreatePopUp("/view/addLine.fxml", btnAddLine);
        refreshLines();
    }

    @FXML
    void onEditLine(ActionEvent event) {

    }

    @FXML
    void onRemoveLine(ActionEvent event) {
        Line lineToRemove = tblLine.getSelectionModel().getSelectedItem();
        tblLine.getItems().remove(lineToRemove);
        Database.delete(lineToRemove);
        refreshLines();
    }

    @FXML
    void onSearchLine(ActionEvent event) {

    }

}
