package edu.iipw.pap;

import edu.iipw.pap.db.Database;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/view/gui.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        Scene scene = new Scene(page);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        Database.initialize();
        try {
            launch();
        } finally {
            Database.close();
        }
    }

}
