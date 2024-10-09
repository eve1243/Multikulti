package at.ac.fhcampuswien.space_invader;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;

public class GameScreenController {

    @FXML
    private Label scoreLabel; // Label für den Score
    @FXML
    private Label livesLabel; // Label für die Lives
    @FXML
    private Canvas canvas; // Canvas für das Quadrat

    private int score; // Aktueller Score
    private int lives; // Verfügbare Lives

    private double squareX = 275; // Startposition des Quadrats (X)
    private final double squareY = 180; // Y-Position des Quadrats (fest)
    private final double squareSize = 50; // Größe des Quadrats

    public GameScreenController() {
        this.score = 300; // Setze den Anfangsscore
        this.lives = 3; // Setze die Anfangsanzahl der Lives
    }

    public void initialize() {
        updateScoreLabel(); // Initialisiere das Score-Label
        updateLivesLabel(); // Initialisiere das Lives-Label
        drawSquare(); // Quadrat initial zeichnen

        // Füge den KeyEvent-Handler hinzu, wenn die Scene vorhanden ist
        if (canvas.getScene() != null) {
            canvas.getScene().setOnKeyPressed(this::handleKeyPress);
            canvas.requestFocus(); // Fordert den Fokus für das Canvas an
        } else {
            // Setze den Handler, wenn die Scene später gesetzt wird
            canvas.sceneProperty().addListener((obs, oldScene, newScene) -> {
                if (newScene != null) {
                    newScene.setOnKeyPressed(this::handleKeyPress);
                    canvas.requestFocus(); // Fordert den Fokus für das Canvas an
                }
            });
        }
    }


    // Methode zum Aktualisieren des Scores
    public void addScore(int points) {
        score += points; // Punkte zum Score hinzufügen
        updateScoreLabel(); // Score-Label aktualisieren
    }

    // Methode zum Verlieren eines Lebens
    public void loseLife() {
        if (lives > 0) {
            lives--; // Ein Leben abziehen
            updateLivesLabel(); // Lives-Label aktualisieren
        }
    }

    // Methode zum Zeichnen des Quadrats
    private void drawSquare() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // Lösche den Canvas
        gc.fillRect(squareX, squareY, squareSize, squareSize); // Zeichne das Quadrat
    }

    // Methode zum Verarbeiten der Tasteneingaben
    @FXML
    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
                if (squareX > 0) { // Überprüfe, ob das Quadrat den linken Rand erreicht hat
                    squareX -= 10; // Bewege das Quadrat nach links
                }
                break;
            case RIGHT:
                if (squareX + squareSize < canvas.getWidth()) { // Überprüfe den rechten Rand
                    squareX += 10; // Bewege das Quadrat nach rechts
                }
                break;
            default:
                break;
        }
        drawSquare(); // Zeichne das Quadrat mit der neuen Position
    }

    // Methode zum Aktualisieren des Score-Labels
    private void updateScoreLabel() {
        scoreLabel.setText("Score: " + score); // Setze den Text des Score-Labels
    }

    // Methode zum Aktualisieren des Lives-Labels
    private void updateLivesLabel() {
        livesLabel.setText("Lives: " + lives); // Setze den Text des Lives-Labels
    }
}
