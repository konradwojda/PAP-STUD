package edu.iipw.pap;

import edu.iipw.pap.db.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/view/main.fxml"));
        HBox page = (HBox) loader.load();
        // FXMLLoader loader = new FXMLLoader(App.class.getResource("/view/gui.fxml"));
        // AnchorPane page = (AnchorPane) loader.load();
        Scene scene = new Scene(page);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        // Setup logging
        System.setProperty(
                "java.util.logging.config.file",
                App.class.getClassLoader().getResource("logging.properties").getFile());

        Database.INSTANCE.initialize();
        try {
            launch();
        } finally {
            Database.INSTANCE.close();
        }
    }

}
