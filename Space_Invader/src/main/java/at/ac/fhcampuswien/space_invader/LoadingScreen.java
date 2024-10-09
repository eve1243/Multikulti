package at.ac.fhcampuswien.space_invader;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoadingScreen extends Application {

    public static void main(String[] args) {
        launch(args); // args an launch() übergeben
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Lade die FXML-Datei
        FXMLLoader fxmlLoader = new FXMLLoader(LoadingScreen.class.getResource("loading-screen.fxml"));
        Parent root = fxmlLoader.load(); // Lade die Wurzel aus der FXML-Datei

        // Die Größe der Szene hier weglassen, um sie von der FXML-Datei steuern zu lassen
        Scene scene = new Scene(root);

        // Setze die Szene für das Stage
        stage.setScene(scene);

        // Setze den Titel für das Fenster
        stage.setTitle("Loading Screen");

        // Stelle sicher, dass das Fenster die FXML-Größe hat
        stage.sizeToScene();

        // Zeige das Fenster
        stage.show();
    }
}
