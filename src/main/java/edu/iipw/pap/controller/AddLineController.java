package edu.iipw.pap.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import edu.iipw.pap.db.Database;
import edu.iipw.pap.db.model.Agency;
import edu.iipw.pap.db.model.Line;
import edu.iipw.pap.db.model.LineType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddLineController implements Initializable {
    @FXML
    private Button btnAddPattern;

    @FXML
    private Button btnLineOk;

    @FXML
    private ChoiceBox<Agency> choiceLineAgency;

    @FXML
    private ChoiceBox<LineType> choiceLineType;

    @FXML
    private TableColumn<?, ?> colLineType;

    @FXML
    private TableColumn<?, ?> colPatternDirection;

    @FXML
    private TableColumn<?, ?> colPatternHeadsign;

    @FXML
    private TableColumn<?, ?> colPatternId;

    @FXML
    private TextField txtLineCode;

    @FXML
    private TextField txtLineDescription;

    @FXML
    private Text txtStopError;

    @FXML
    void onAddPattern(ActionEvent event) throws IOException {
        // FIXME: no tego tu nie powinno być, trzeba dać jakaś referencje do referencji
        // na referencji
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addPattern.fxml"));
        VBox page = (VBox) loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(page));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(btnAddPattern.getScene().getWindow());
        stage.showAndWait();
    }

    @FXML
    void onEditPattern(ActionEvent event) {

    }

    @FXML
    void onLineOk(ActionEvent event) throws Exception {
        try {
            var line = new Line(txtLineCode.getText(), txtLineDescription.getText(), choiceLineType.getValue(),
                    choiceLineAgency.getValue());
            Database.add(line);
        } catch (Exception e) {
            txtStopError.setText(e.toString());
        }
        Stage stage = (Stage) btnLineOk.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceLineType.getItems().setAll(LineType.values());
        choiceLineAgency.getItems().setAll(Database.listAll(Agency.class));
    }

    @FXML
    void onRemovePattern(ActionEvent event) {

    }

    @FXML
    void onSearchPattern(ActionEvent event) {

    }

}
