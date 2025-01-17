package at.ac.fhcampuswien.space_invader;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
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
    @FXML
    private ImageView backgroundImageView; // ImageView für das Hintergrundbild
    @FXML
    private Label playerNameLabel;



    private int score; // Aktueller Score
    private int lives; // Verfügbare Lives

    private long lastShootTime = 0; // Zeitpunkt des letzten Schusses
    private final long SHOOT_COOLDOWN = 500_000_000; // Cooldown in Nanosekunden (0,5 Sekunden)


    private double squareX = 275; // Startposition des Hauptquadrats (X)
    private final double squareY = 180; // Y-Position des Hauptquadrats (fest)
    private final double squareSize = 50; // Größe des Hauptquadrats

    // Liste der Projektilen
    private final List<Projectile> playerProjectiles = new ArrayList<>();

    // Eigenschaften der kleineren Quadrate
    private final List<SmallSquare> smallSquares = new ArrayList<>();
    private final Random random = new Random();

    public GameScreenController() {
        this.score = 0; // Setze den Anfangsscore
        this.lives = 3; // Setze die Anfangsanzahl der Lives
    }

    public void initialize() {
        // Setze das Hintergrundbild
        Image backgroundImage = new Image("file:src/main/resources/at/ac/fhcampuswien/space_invader/images/computer-background.png");
        backgroundImageView.setImage(backgroundImage);
        backgroundImageView.setPreserveRatio(false);

        updateScoreLabel(); // Initialisiere das Score-Label
        updateLivesLabel(); // Initialisiere das Lives-Label
        // Setze den Hintergrund des Canvas auf transparent
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        drawSquare(); // Quadrat initial zeichnen

        // Erstelle kleine Quadrate nacheinander
        createSmallSquaresSequentially();

        // Animation Timer für die Bewegung der kleinen Quadrate
        // Animation Timer für die Bewegung der kleinen Quadrate
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateSmallSquares(now); // Aktualisiere die Positionen der kleinen Quadrate
                updatePlayerProjectiles(); // Aktualisiere die Positionen der Spieler-Projektile

                checkCollisionsWithSmallSquares(); // Überprüfe Kollisionen mit kleinen Quadraten

                drawAll(); // Zeichne alles (Hauptquadrat, kleine Quadrate und Projektile)
            }
        };
        animationTimer.start(); // Starte den Timer

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
            case SPACE:
                // Wenn die Leertaste gedrückt wird, schieße ein Projektil
                shootPlayerProjectile();
                break;
            default:
                break;
        }
    }

    private void shootPlayerProjectile() {
        long currentTime = System.nanoTime(); // Aktuelle Zeit in Nanosekunden
        if (currentTime - lastShootTime >= SHOOT_COOLDOWN) {
            // Wenn genug Zeit seit dem letzten Schuss vergangen ist
            Projectile projectile = new Projectile(squareX + squareSize / 2 - 2, squareY, true);
            playerProjectiles.add(projectile); // Füge das Projektil der Liste hinzu
            lastShootTime = currentTime; // Setze den Zeitpunkt des letzten Schusses
        }
    }




    private void drawAll() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // Lösche den Canvas
        drawSquare(); // Zeichne das Hauptquadrat
        drawSmallSquares(gc); // Zeichne die kleinen Quadrate
        drawPlayerProjectiles(gc); // Zeichne die Spieler-Projektile
    }

    // Methode zum Zeichnen der Spieler-Projektile
    private void drawPlayerProjectiles(GraphicsContext gc) {
        for (Projectile projectile : playerProjectiles) {
            projectile.draw(gc); // Zeichne das Projektil
        }
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

    // Methode zum Erstellen der kleinen Quadrate
    private void createSmallSquaresSequentially() {
        final int totalSquares = 7; // Anzahl der Quadrate
        final long delay = 500_000_000; // Verzögerung in Nanosekunden (0,5 Sekunden)

        AnimationTimer squareCreator = new AnimationTimer() {
            private int createdSquares = 0;
            private long lastCreationTime = 0;

            @Override
            public void handle(long now) {
                if (createdSquares < totalSquares && now - lastCreationTime >= delay) {
                    double smallSquareX = random.nextDouble() * (canvas.getWidth() - 20); // Zufällige X-Position
                    double startY = -50; // Startposition außerhalb des sichtbaren Bereichs
                    smallSquares.add(new SmallSquare(smallSquareX, startY)); // Hinzufügen mit Startposition

                    createdSquares++;
                    lastCreationTime = now; // Aktualisiere den letzten Erstellungszeitpunkt
                }

                if (createdSquares >= totalSquares) {
                    stop(); // Beende den Timer, wenn alle Quadrate erstellt wurden
                }
            }
        };

        squareCreator.start(); // Starte den Timer
    }

    private void updatePlayerProjectiles() {
        // Bewege alle Projektile des Spielers
        for (Projectile projectile : playerProjectiles) {
            projectile.move(); // Bewege das Projektil
        }

        // Entferne Projektil, wenn es den Bildschirm verlassen hat
        playerProjectiles.removeIf(Projectile::isOffScreen);
    }

    private void checkCollisionsWithSmallSquares() {
        // Verwende eine temporäre Liste, um die zu entfernenden Elemente zu speichern
        List<Projectile> projectilesToRemove = new ArrayList<>();
        List<SmallSquare> smallSquaresToRemove = new ArrayList<>();

        // Überprüfe Kollisionen für jedes Projektil
        for (Projectile projectile : playerProjectiles) {
            for (SmallSquare smallSquare : smallSquares) {
                if (projectile.isColliding(smallSquare.getX(), smallSquare.getY(), smallSquare.getSize())) {
                    smallSquaresToRemove.add(smallSquare); // Füge das kleine Quadrat zur Entfernen-Liste hinzu
                    projectilesToRemove.add(projectile); // Füge das Projektil zur Entfernen-Liste hinzu
                    break; // Wenn ein Projektil kollidiert, gehe zur nächsten Iteration
                }
            }
        }

        // Entferne die Objekte außerhalb der Iteration
        smallSquares.removeAll(smallSquaresToRemove);
        score += smallSquaresToRemove.size() * 100; // Erhöhe den Score
        updateScoreLabel(); // Aktualisiere das Score-Label
        playerProjectiles.removeAll(projectilesToRemove);

        // Überprüfe, ob alle kleinen Quadrate entfernt wurden
        if (smallSquares.isEmpty()) {
            endGame(); // Wechsle zum Endscreen
        }
    }


    // Methode zum Aktualisieren der kleinen Quadrate
    private void updateSmallSquares(long now) {
        for (SmallSquare smallSquare : smallSquares) {
            smallSquare.move(canvas.getWidth());

            if (smallSquare.isMovingDown() && smallSquare.getY() >= canvas.getHeight() / 4) {
                smallSquare.setMovingDown(false); // Richtung wechseln
            } else if (!smallSquare.isMovingDown() && smallSquare.getY() <= 0) {
                smallSquare.setMovingDown(true); // Richtung wechseln
            }

            smallSquare.updatePositionWithSpeed();

            if (smallSquare.canShootProjectile(now, squareX, squareSize)) {
                smallSquare.shootProjectile();
            }

            if (smallSquare.isProjectileCollidingWith(squareX, squareY, squareSize)) {
                loseLife();
            }
        }
    }

    private boolean gameEnded = false;

    private void endGame() {
        if (gameEnded) return; // Beende, wenn der Endscreen bereits angezeigt wird
        gameEnded = true;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("end-screen.fxml"));
            Parent root = loader.load();

            EndScreenController endScreenController = loader.getController();
            Player player = new Player(playerNameLabel.getText(), score);
            endScreenController.setPlayer(player);

            Scene scene = new Scene(root);

            Platform.runLater(() -> {
                Stage stage = (Stage) canvas.getScene().getWindow();
                if (stage != null) {
                    stage.setScene(scene);
                    stage.show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loseLife() {
        lives--;
        updateLivesLabel();

        if (lives <= 0 && !gameEnded) {
            endGame();
        }
    }


    private void updateScoreLabel() {
        scoreLabel.setText("Score: " + score);
    }

    private void updateLivesLabel() {
        livesLabel.setText("Lives: " + lives);
    }

    public void setPlayer(Player player) {
        playerNameLabel.setText(player.getName());
    }
}
