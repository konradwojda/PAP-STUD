package edu.iipw.pap.controller;

import java.io.IOException;

import edu.iipw.pap.db.model.Agency;
import edu.iipw.pap.db.model.Line;
import edu.iipw.pap.db.model.LineType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

    public void refrenceMainController(MainController mainController){
        this.mainController = mainController;
    }

    @FXML
    void onAddLine(ActionEvent event) throws IOException {
        mainController.CreatePopUp("/view/addPattern.fxml", btnAddLine);
    }

    @FXML
    void onEditLine(ActionEvent event) {

    }

    @FXML
    void onRemoveLine(ActionEvent event) {

    }

    @FXML
    void onSearchLine(ActionEvent event) {

    }

}
