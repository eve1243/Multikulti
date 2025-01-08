package at.ac.fhcampuswien.space_invader;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;

import java.util.Collections;
import java.util.List;

public class EndScreenController {

    @FXML
    private Label finalScoreLabel;
    // Label zum Anzeigen des finalen Scores
    @FXML
    private Button restartButton; // Button für Neustart
    @FXML
    private Label playerNameLabel; // Label für den Spielernamen
    @FXML
    private TreeTableView<Player> finalscore; // Das TreeTableView

    @FXML
    private TreeTableColumn<Player, String> name; // Die Spalte für den Namen

    @FXML
    private TreeTableColumn<Player, Integer> score; // Die Spalte für den Score
    @FXML
    private Label noHighscoreLabel; // Label für den Fall, dass kein Highscore erreicht wurde

    // Methode, um den Player in das TreeTableView zu setzen
    // Methode, um den Player in das TreeTableView zu setzen
    public void setPlayer(Player player) {
       playerNameLabel.setText(player.getName());
        finalScoreLabel.setText("Final Score: " + player.getScore());

        // Speichere den neuen Highscore
        ScoreManager.addPlayerIfHighScore(player);

        // Lade die besten 10 Spieler
        List<Player> players = ScoreManager.loadScores();

        ObservableList<Player> playersList = FXCollections.observableArrayList(players);
        TreeItem<Player> root = new TreeItem<>();
        for (Player p : players) {
            TreeItem<Player> playerItem = new TreeItem<>(p);
            root.getChildren().add(playerItem);
        }

        finalscore.setRoot(root);
        finalscore.setShowRoot(false);

        name.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        score.setCellValueFactory(new TreeItemPropertyValueFactory<>("score"));
    }

    // Neustart des Spiels
    @FXML
    private void handleRestartButtonClick() {
        try {
            ScoreManager.resetScoreSaved(); // Setze das Flag zurück
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


}
