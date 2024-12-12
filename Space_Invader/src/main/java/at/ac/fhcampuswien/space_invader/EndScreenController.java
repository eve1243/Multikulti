package at.ac.fhcampuswien.space_invader;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EndScreenController {

    @FXML
    private Label finalScoreLabel; // Label zum Anzeigen des finalen Scores

    // Methode zum Setzen des finalen Scores
    public void setFinalScore(int score) {
        finalScoreLabel.setText("Final Score: " + score);
    }
}
