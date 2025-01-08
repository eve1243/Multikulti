package at.ac.fhcampuswien.space_invader;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class EndScreenController {

    @FXML
    private Label finalScoreLabel;
    // Label zum Anzeigen des finalen Scores
    @FXML
    private Button restartButton; // Button für Neustart
    @FXML
    private Label playerNameLabel; // Label für den Spielernamen
    // Neustart des Spiels
    @FXML
    private void handleRestartButtonClick() {
        try {
            // Lade den Game-Screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("game-screen.fxml"));
            Parent root = loader.load();

            // Hole das GameScreenController-Objekt
            GameScreenController controller = loader.getController();
            Player player = new Player(playerNameLabel.getText(), 0);
            controller.setPlayer(player);


            // Erstelle eine neue Szene
            Scene scene = new Scene(root);

            // Hole das aktuelle Fenster
            Stage stage = (Stage) restartButton.getScene().getWindow();

            // Setze die neue Szene
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Beende die Anwendung
    @FXML
    private void handleExitButtonClick() {
        Stage stage = (Stage) restartButton.getScene().getWindow();
        stage.close(); // Schließt das aktuelle Fenster
    }

    public void setPlayer(Player player) {
        finalScoreLabel.setText("Final Score: " + player.getScore());
        playerNameLabel.setText( player.getName());
    }
}
