package edu.iipw.pap;

import java.io.IOException;

import edu.iipw.pap.db.model.Agency;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Controller {
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
    private TableColumn<?, ?> colAgencyName;

    @FXML
    private TableColumn<?, ?> colAgencyTelephone;

    @FXML
    private TableColumn<?, ?> colAgencyTimezone;

    @FXML
    private TableColumn<?, ?> colAgencyWebsite;

    @FXML
    private TableColumn<?, ?> colLineAgencyId;

    @FXML
    private TableColumn<?, ?> colLineCode;

    @FXML
    private TableColumn<?, ?> colLineDescription;

    @FXML
    private TableColumn<?, ?> colLineId;

    @FXML
    private TableColumn<?, ?> colLineType;

    @FXML
    private TableColumn<?, ?> colStopCode;

    @FXML
    private TableColumn<?, ?> colStopId;

    @FXML
    private TableColumn<?, ?> colStopLat;

    @FXML
    private TableColumn<?, ?> colStopLon;

    @FXML
    private TableColumn<?, ?> colStopName;

    @FXML
    private TableColumn<?, ?> colStopWheelchairAccesible;

    @FXML
    private Pane plnStatus;

    @FXML
    private GridPane pnAgency;

    @FXML
    private GridPane pnLine;

    @FXML
    private GridPane pnStop;

    @FXML
    private TableView<?> tblAgency;

    @FXML
    private TableView<?> tblLine;

    @FXML
    private TableView<?> tblStop;

    @FXML
    private Text textHeader;

    @FXML
    void onAddAgency(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addAgency.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(page));
        stage.initOwner(btnAddStop.getScene().getWindow());
        stage.showAndWait();
    }

    @FXML
    void onAddLine(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addLine.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(page));
        stage.initOwner(btnAddStop.getScene().getWindow());
        stage.showAndWait();
    }

    @FXML
    void onAddStop(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addStop.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(page));
        stage.initOwner(btnAddStop.getScene().getWindow());
        stage.showAndWait();
    }

    @FXML
    void onAgency(ActionEvent event) {
        textHeader.setText("Agencies");
        plnStatus.setBackground(new Background(new BackgroundFill(Color.rgb(8, 83, 163), CornerRadii.EMPTY, Insets.EMPTY)));
        pnAgency.toFront();
    }

    @FXML
    void onLine(ActionEvent event) {
        textHeader.setText("Lines");
        plnStatus.setBackground(new Background(new BackgroundFill(Color.rgb(60, 147, 240), CornerRadii.EMPTY, Insets.EMPTY)));
        pnLine.toFront();
    }

    @FXML
    void onRemoveAgency(ActionEvent event) {

    }

    @FXML
    void onRemoveLine(ActionEvent event) {

    }

    @FXML
    void onRemoveStop(ActionEvent event) {

    }

    @FXML
    void onSearchAgency(ActionEvent event) {

    }

    @FXML
    void onSearchLine(ActionEvent event) {

    }

    @FXML
    void onSearchStop(ActionEvent event) {

    }

    @FXML
    void onStop(ActionEvent event) {
        textHeader.setText("Stops");
        plnStatus.setBackground(new Background(new BackgroundFill(Color.rgb(163, 112, 24), CornerRadii.EMPTY, Insets.EMPTY)));
        pnStop.toFront();
    }

}
