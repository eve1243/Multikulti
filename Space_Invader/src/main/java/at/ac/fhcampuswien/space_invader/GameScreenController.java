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

            // Erstelle kleine Quadrate nacheinander
            createSmallSquaresSequentially();

            // Animation Timer für die Bewegung der kleinen Quadrate
            AnimationTimer animationTimer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    updateSmallSquares(now);
                    drawAll(); // Zeichne alles (Hauptquadrat und kleine Quadrate)
                }
            };
            animationTimer.start();

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



        private void updateSmallSquares(long now) {
            for (SmallSquare smallSquare : smallSquares) {
                // Bewegung des kleinen Quadrats
                smallSquare.move(canvas.getWidth());

                // Überprüfen, ob das Quadrat die Richtung ändern soll (nach oben/unten)
                if (smallSquare.isMovingDown() && smallSquare.getY() >= canvas.getHeight() / 4) {
                    smallSquare.setMovingDown(false); // Richtung wechseln (nach oben)
                } else if (!smallSquare.isMovingDown() && smallSquare.getY() <= 0) {
                    smallSquare.setMovingDown(true); // Richtung wechseln (nach unten)
                }

                // Geschwindigkeit aktualisieren
                smallSquare.updatePositionWithSpeed();

                // Prüfe, ob das Quadrat ein Projektil abschießen soll
                if (smallSquare.canShootProjectile(now, squareX, squareSize)) {
                    smallSquare.shootProjectile();
                }

                // Prüfe, ob das Projektil das Hauptquadrat trifft
                if (smallSquare.isProjectileCollidingWith(squareX, squareY, squareSize)) {
                    loseLife(); // Leben abziehen, wenn das Hauptquadrat getroffen wird
                }
            }
        }

        private void loseLife() {
            lives--;
            updateLivesLabel();
            if (lives <= 0) {
                System.out.println("Game Over");
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("end-screen.fxml"));
                    Parent root = loader.load();

                    EndScreenController endScreenController = loader.getController();

                    endScreenController.setFinalScore(score);

                    Scene scene = new Scene(root);

                    Platform.runLater(() -> {
                        // Hole die Stage aus der aktuellen Scene
                        Stage stage = (Stage) canvas.getScene().getWindow();
                        if (stage != null) {
                            stage.setScene(scene); // Setze die neue Szene
                            stage.show(); // Zeige die Stage
                        } else {
                            System.err.println("Stage ist null. Scene konnte nicht gesetzt werden.");
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace(); // Gibt den Fehler aus, falls das FXML nicht geladen werden kann
                    System.err.println("Fehler beim Laden der Endbildschirm-Szene: " + e.getMessage());
                }
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
