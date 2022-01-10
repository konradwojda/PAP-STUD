package edu.iipw.pap.controller;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;

import edu.iipw.pap.db.Database;
import edu.iipw.pap.db.model.Agency;
import edu.iipw.pap.db.model.Line;
import edu.iipw.pap.db.model.LineType;
import edu.iipw.pap.db.model.Pattern;
import edu.iipw.pap.db.model.PatternDirection;
import edu.iipw.pap.exceptions.InvalidData;
import edu.iipw.pap.exceptions.InvalidObject;
import edu.iipw.pap.interfaces.IController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * EditLineController is responsible for controlling window while user is
 * adding or editing a line. It implements IController to set object that is
 * being edited.
 */
public class EditLineController implements Initializable, IController {
    /**
     * Button for adding new pattern to a line
     */
    @FXML
    private Button btnAddPattern;

    /**
     * Button for confirming changes
     */
    @FXML
    private Button btnLineOk;

    /**
     * ChoiceBox to choose an agency that line is being operated by
     */
    @FXML
    private ChoiceBox<Agency> choiceLineAgency;

    /**
     * ChoiceBox to choose line type
     */
    @FXML
    private ChoiceBox<LineType> choiceLineType;

    /**
     * TableColumn for pattern direction
     */
    @FXML
    private TableColumn<Pattern, PatternDirection> colPatternDirection;

    /**
     * TableColumn for pattern headsign
     */
    @FXML
    private TableColumn<Pattern, String> colPatternHeadsign;

    /**
     * TableColumn for pattern index
     */
    @FXML
    private TableColumn<Pattern, Integer> colPatternId;

    /**
     * TableView displaying line's patterns
     */
    @FXML
    private TableView<Pattern> tblPattern;

    /**
     * TextField to enter line code
     */
    @FXML
    private TextField txtLineCode;

    /**
     * TextField to enter line description
     */
    @FXML
    private TextField txtLineDescription;

    /**
     * Text to display error if occurs
     */
    @FXML
    private Text txtStopError;

    /**
     * Instance of line object that is being edited
     */
    private Line line_;

    /**
     * Setting object that is being edited and binding properties
     */
    @Override
    public <T> void setObject(T obj) throws InvalidObject {
        if (Line.class.isInstance(obj)) {
            this.line_ = (Line) obj;
            InitializePatternTable();
            this.txtLineCode.textProperty().bindBidirectional(this.line_.codeProperty());
            this.txtLineDescription.textProperty().bindBidirectional(this.line_.descriptionProperty());
            this.choiceLineAgency.valueProperty().bindBidirectional(this.line_.agencyProperty());
            this.choiceLineType.valueProperty().bindBidirectional(this.line_.typeProperty());
            Database.INSTANCE.markToSave(this.line_);
        } else {
            throw new InvalidObject("Niepoprawny obiekt");
        }

    }

    /**
     * After clicking add pattern open new window and add pattern to line, then mark
     * to save by database.
     *
     * @param event
     * @throws Exception
     */
    @FXML
    void onAddPattern(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/editPattern.fxml"));
        VBox page = (VBox) loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(page));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(btnAddPattern.getScene().getWindow());
        IController controller = loader.getController();
        Pattern pattern = new Pattern();
        pattern.setLine(line_);
        var linePatterns = line_.patternsProperty();
        if (linePatterns.get() == null) {
            line_.setPatterns(new HashSet<Pattern>());
        }
        linePatterns.add(pattern);
        controller.setObject(pattern);
        Database.INSTANCE.markToSave(pattern);

        stage.showAndWait();
        refreshPatterns();
    }

    /**
     * Refreshing pattern table
     */
    private void refreshPatterns() {
        if (!(this.line_.patternsProperty() == null))
            tblPattern.getItems().setAll(this.line_.patternsProperty());
    }

    /**
     * Initializing pattern table by setting cell value factories
     */
    public void InitializePatternTable() {
        colPatternDirection.setCellValueFactory(new PropertyValueFactory<Pattern, PatternDirection>("direction"));
        colPatternHeadsign.setCellValueFactory(new PropertyValueFactory<Pattern, String>("headsign"));
        colPatternId.setCellValueFactory(new PropertyValueFactory<Pattern, Integer>("patternId"));
        refreshPatterns();
    }

    /**
     * After clink on edit pattern, open window and set pattern to edit
     *
     * @param event
     * @throws Exception
     */
    @FXML
    void onEditPattern(ActionEvent event) throws Exception {
        try {
            Pattern patternToEdit = tblPattern.getSelectionModel().getSelectedItem();
            if (patternToEdit == null)
                return;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/editPattern.fxml"));
            VBox page = (VBox) loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(page));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btnAddPattern.getScene().getWindow());
            IController controller = loader.getController();
            controller.setObject(patternToEdit);
            Database.INSTANCE.markToSave(patternToEdit);

            stage.showAndWait();
            refreshPatterns();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * After clicking on Ok button, validate input and save to database
     *
     * @param event
     * @throws Exception
     */
    @FXML
    void onLineOk(ActionEvent event) throws Exception {
        try {
            line_.validateUserInput();
        } catch (InvalidData e) {
            txtStopError.setText(e.toString());
            return;
        }
        Database.INSTANCE.commitMarked();
        Stage stage = (Stage) btnLineOk.getScene().getWindow();
        stage.close();
    }

    /**
     * Initializing choice boxes
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceLineType.getItems().setAll(LineType.values());
        choiceLineAgency.getItems().setAll(Database.INSTANCE.listAll(Agency.class));
    }

    /**
     * Removing pattern and refreshing table
     *
     * @param event
     */
    @FXML
    void onRemovePattern(ActionEvent event) {
        Pattern patternToRemove = tblPattern.getSelectionModel().getSelectedItem();
        if (patternToRemove == null)
            return;
        line_.patternsProperty().remove(patternToRemove);
        Database.INSTANCE.markToDelete(patternToRemove);
        refreshPatterns();
    }

}
