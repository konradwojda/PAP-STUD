package edu.iipw.pap.controller;

import java.io.File;

import edu.iipw.pap.db.Database;
import edu.iipw.pap.GTFSExporter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class ExportToGTFSController {
    @FXML
    private Button btnBrowse;

    @FXML
    private Button btnExportOk;

    @FXML
    private Text txtDirectory;

    @FXML
    private Text txtExportError;

    @FXML
    private TextField txtFileName;


    @FXML
    void onBrowse(ActionEvent event) {
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = (Stage) btnExportOk.getScene().getWindow();
        File file = directoryChooser.showDialog(stage);
        if(file != null){
            txtDirectory.setText(file.getAbsolutePath());
        }

    }

    @FXML
    void onExportOk(ActionEvent event) throws Exception {
        String path  = txtDirectory.getText() + "/" + txtFileName.getText() + ".zip";
        try {
            GTFSExporter.exportToZip(path, Database.INSTANCE);
        } catch (Exception e) {
            txtExportError.setText(e.toString());
            return;
        }
        Stage stage = (Stage) btnExportOk.getScene().getWindow();
        stage.close();
    }
}
