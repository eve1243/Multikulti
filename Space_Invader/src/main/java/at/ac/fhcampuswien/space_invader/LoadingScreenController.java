package at.ac.fhcampuswien.space_invader;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class LoadingScreenController {
    @FXML
    private Button button;
    @FXML
    private TextField nameTextField;

    public Player player;


    @FXML
    private void handleButtonClick() {
        try {
            // Erstelle einen neuen Spieler
            player = new Player(nameTextField.getText(), 0);

            // Lade das zweite FXML-File
            FXMLLoader loader = new FXMLLoader(getClass().getResource("game-screen.fxml"));
            Parent root = loader.load();

            // Hole das GameScreenController-Objekt
            GameScreenController controller = loader.getController();
            controller.setPlayer(player);


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
