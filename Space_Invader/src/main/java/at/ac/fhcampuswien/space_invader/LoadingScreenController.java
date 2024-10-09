package at.ac.fhcampuswien.space_invader;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class LoadingScreenController {
    @FXML
    private Button button;

    @FXML
    private void handleButtonClick() {
        try {
            // Lade das zweite FXML-File
            FXMLLoader loader = new FXMLLoader(getClass().getResource("game-screen.fxml"));
            Parent root = loader.load();

            // Erstelle eine neue Szene
            Scene scene = new Scene(root);

            // Hole das aktuelle Fenster
            Stage stage = (Stage) button.getScene().getWindow();

            // Setze die neue Szene
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
