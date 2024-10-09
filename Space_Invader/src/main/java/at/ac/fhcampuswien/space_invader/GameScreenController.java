package at.ac.fhcampuswien.space_invader;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameScreenController {

    @FXML
    private Label scoreLabel; // Label für den Score
    @FXML
    private Label livesLabel; // Label für die Lives
    @FXML
    private Canvas canvas; // Canvas für das Quadrat

    private int score; // Aktueller Score
    private int lives; // Verfügbare Lives

    private double squareX = 275; // Startposition des Hauptquadrats (X)
    private final double squareY = 180; // Y-Position des Hauptquadrats (fest)
    private final double squareSize = 50; // Größe des Hauptquadrats

    // Eigenschaften der kleineren Quadrate
    private final List<SmallSquare> smallSquares = new ArrayList<>();
    private final Random random = new Random();

    public GameScreenController() {
        this.score = 300; // Setze den Anfangsscore
        this.lives = 3; // Setze die Anfangsanzahl der Lives
    }

    public void initialize() {
        updateScoreLabel(); // Initialisiere das Score-Label
        updateLivesLabel(); // Initialisiere das Lives-Label
        drawSquare(); // Quadrat initial zeichnen

        // Erstelle bis zu 7 kleinere Quadrate mit zufälligen Positionen
        createSmallSquares();

        // Animation Timer für die Bewegung der kleinen Quadrate
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateSmallSquares();
                drawAll(); // Zeichne alles (Hauptquadrat und kleine Quadrate)
            }
        };
        animationTimer.start();

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

    // Methode zum Erstellen der kleinen Quadrate
    private void createSmallSquares() {
        for (int i = 0; i < 7; i++) {
            double smallSquareX = random.nextDouble() * (canvas.getWidth() - 20); // Zufällige X-Position
            smallSquares.add(new SmallSquare(smallSquareX, squareY - 60)); // Position weiter oben (60 Pixel über dem Hauptquadrat)
        }
    }

    // Methode zum Aktualisieren der kleinen Quadrate
    private void updateSmallSquares() {
        for (SmallSquare smallSquare : smallSquares) {
            smallSquare.move(canvas.getWidth()); // Bewege jedes kleine Quadrat
        }
    }

    // Methode zum Zeichnen von Hauptquadrat und kleinen Quadraten
    private void drawAll() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // Lösche den Canvas
        drawSquare(); // Zeichne das Hauptquadrat
        drawSmallSquares(gc); // Zeichne die kleinen Quadrate
    }

    // Methode zum Zeichnen des Hauptquadrats
    private void drawSquare() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(javafx.scene.paint.Color.BLUE); // Farbe des Hauptquadrats
        gc.fillRect(squareX, squareY, squareSize, squareSize); // Zeichne das Hauptquadrat
    }

    // Methode zum Zeichnen der kleinen Quadrate
    private void drawSmallSquares(GraphicsContext gc) {
        for (SmallSquare smallSquare : smallSquares) {
            smallSquare.draw(gc); // Zeichne das kleine Quadrat
        }
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
