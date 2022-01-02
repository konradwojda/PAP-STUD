package edu.iipw.pap.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import edu.iipw.pap.db.Database;
import edu.iipw.pap.db.model.Agency;
import edu.iipw.pap.db.model.Line;
import edu.iipw.pap.db.model.LineType;
import edu.iipw.pap.db.model.Stop;
import edu.iipw.pap.db.model.WheelchairAccessibility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Controller implements Initializable {
    @FXML
    private Button btnAddAgency;

    @FXML
    private Button btnAddLine;

    @FXML
    private Button btnAddStop;

    @FXML
    private Button btnAgency;

    @FXML
    private Button btnLine;

    @FXML
    private Button btnRemoveAgency;

    @FXML
    private Button btnRemoveLine;

    @FXML
    private Button btnRemoveStop;

    @FXML
    private Button btnSearchAgency;

    @FXML
    private Button btnSearchLine;

    @FXML
    private Button btnSearchStop;

    @FXML
    private Button btnStop;

    @FXML
    private TableColumn<Agency, Integer> colAgencyId;

    @FXML
    private TableColumn<Agency, String> colAgencyName;

    @FXML
    private TableColumn<Agency, String> colAgencyTelephone;

    @FXML
    private TableColumn<Agency, String> colAgencyTimezone;

    @FXML
    private TableColumn<Agency, String> colAgencyWebsite;

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
    private TableColumn<Stop, String> colStopCode;

    @FXML
    private TableColumn<Stop, Integer> colStopId;

    @FXML
    private TableColumn<Stop, Double> colStopLat;

    @FXML
    private TableColumn<Stop, Double> colStopLon;

    @FXML
    private TableColumn<Stop, String> colStopName;

    @FXML
    private TableColumn<Stop, WheelchairAccessibility> colStopWheelchairAccessible;

    @FXML
    private Pane plnStatus;

    @FXML
    private GridPane pnAgency;

    @FXML
    private GridPane pnLine;

    @FXML
    private GridPane pnStop;

    @FXML
    private TableView<Agency> tblAgency;

    @FXML
    private TableView<Line> tblLine;

    @FXML
    private TableView<Stop> tblStop;

    @FXML
    private Text textHeader;

    private void CreatePopUp(String fxml_template, Button button) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml_template));
        AnchorPane page = (AnchorPane) loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(page));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(button.getScene().getWindow());
        stage.showAndWait();
    }

    private void InitializeLineTable() {
        colLineId.setCellValueFactory(new PropertyValueFactory<Line, Integer>("lineId"));
        colLineCode.setCellValueFactory(new PropertyValueFactory<Line, String>("code"));
        colLineDescription.setCellValueFactory(new PropertyValueFactory<Line, String>("description"));
        colLineType.setCellValueFactory(new PropertyValueFactory<Line, LineType>("type"));
        colLineAgency.setCellValueFactory(new PropertyValueFactory<Line, Agency>("agency"));
        refreshLines();
    }

    private void refreshLines() {
        tblLine.getItems().setAll(Database.listAll(Line.class));
    }

    private void InitializeAgencyTable() {
        colAgencyId.setCellValueFactory(new PropertyValueFactory<Agency, Integer>("agencyId"));
        colAgencyName.setCellValueFactory(new PropertyValueFactory<Agency, String>("name"));
        colAgencyWebsite.setCellValueFactory(new PropertyValueFactory<Agency, String>("website"));
        colAgencyTimezone.setCellValueFactory(new PropertyValueFactory<Agency, String>("timezone"));
        colAgencyTelephone.setCellValueFactory(new PropertyValueFactory<Agency, String>("telephone"));
        refreshAgencies();
    }

    private void refreshAgencies() {
        tblAgency.getItems().setAll(Database.listAll(Agency.class));
    }

    private void InitializeStopTable() {
        colStopId.setCellValueFactory(new PropertyValueFactory<Stop, Integer>("stopId"));
        colStopName.setCellValueFactory(new PropertyValueFactory<Stop, String>("name"));
        colStopCode.setCellValueFactory(new PropertyValueFactory<Stop, String>("code"));
        colStopLat.setCellValueFactory(new PropertyValueFactory<Stop, Double>("lat"));
        colStopLon.setCellValueFactory(new PropertyValueFactory<Stop, Double>("lon"));
        colStopWheelchairAccessible
                .setCellValueFactory(new PropertyValueFactory<Stop, WheelchairAccessibility>("wheelchairAccessible"));
        refreshStops();
    }

    private void refreshStops() {
        tblStop.getItems().setAll(Database.listAll(Stop.class));
    }

    @FXML
    void onAddAgency(ActionEvent event) throws IOException {
        CreatePopUp("/view/addAgency.fxml", btnAddAgency);
        refreshAgencies();
    }

    @FXML
    void onAddLine(ActionEvent event) throws IOException {
        CreatePopUp("/view/addLine.fxml", btnAddLine);
        refreshLines();
    }

    @FXML
    void onAddStop(ActionEvent event) throws IOException {
        CreatePopUp("/view/addStop.fxml", btnAddStop);
        refreshStops();
    }

    @FXML
    void onAgency(ActionEvent event) {
        textHeader.setText("Agencies");
        plnStatus.setBackground(
                new Background(new BackgroundFill(Color.rgb(8, 83, 163), CornerRadii.EMPTY, Insets.EMPTY)));
        refreshAgencies();
        pnAgency.toFront();
    }

    @FXML
    void onLine(ActionEvent event) {
        textHeader.setText("Lines");
        plnStatus.setBackground(
                new Background(new BackgroundFill(Color.rgb(60, 147, 240), CornerRadii.EMPTY, Insets.EMPTY)));
        refreshLines();
        pnLine.toFront();
    }

    @FXML
    void onRemoveAgency(ActionEvent event) {
        Agency agencyToRemove = tblAgency.getSelectionModel().getSelectedItem();
        tblAgency.getItems().remove(agencyToRemove);
        Database.delete(agencyToRemove);
        refreshAgencies();
    }

    @FXML
    void onRemoveLine(ActionEvent event) {
        Line lineToRemove = tblLine.getSelectionModel().getSelectedItem();
        tblLine.getItems().remove(lineToRemove);
        Database.delete(lineToRemove);
        refreshLines();
    }

    @FXML
    void onRemoveStop(ActionEvent event) {
        Stop stopToRemove = tblStop.getSelectionModel().getSelectedItem();
        tblStop.getItems().remove(stopToRemove);
        Database.delete(stopToRemove);
        refreshStops();
    }

    @FXML
    void onSearchAgency(ActionEvent event) {
        // InitializeAgencyTable();
        // Agency a1 = new Agency("MZK Stalowa Wola", "mzk.pl", "UTF+1", "123456789");
        // tblAgency.getItems().add(a1);
    }

    @FXML
    void onSearchLine(ActionEvent event) {
        // InitializeLineTable();
        // Agency a1 = new Agency("MZK Stalowa Wola", "mzk.pl", "UTF+1", "123456789");
        // Line l1 = new Line("2", "Winnica/Metro Młociny", LineType.TRAM, a1);
        // tblLine.getItems().add(l1);
    }

    @FXML
    void onSearchStop(ActionEvent event) {
        // InitializeStopTable();
        // Stop s1 = new Stop("Nowodwory", "11312", 21424.121, -214234.22,
        // WheelchairAccessibility.ACCESSIBLE);
        // tblStop.getItems().add(s1);
    }

    @FXML
    void onStop(ActionEvent event) {
        textHeader.setText("Stops");
        plnStatus.setBackground(
                new Background(new BackgroundFill(Color.rgb(163, 112, 24), CornerRadii.EMPTY, Insets.EMPTY)));
        pnStop.toFront();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        InitializeAgencyTable();
        InitializeLineTable();
        InitializeStopTable();
    }
}
