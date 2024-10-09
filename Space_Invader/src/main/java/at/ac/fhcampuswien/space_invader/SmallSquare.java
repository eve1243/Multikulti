package at.ac.fhcampuswien.space_invader;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SmallSquare {
    private double x; // X-Position des kleinen Quadrats
    private double y; // Y-Position des kleinen Quadrats
    private final double size = 20; // Größe des kleinen Quadrats
    private double projectileY; // Y-Position des Projektils
    private boolean isShooting; // Gibt an, ob das kleine Quadrat schießt
    private boolean movingRight; // Gibt an, in welche Richtung sich das Quadrat bewegt

    public SmallSquare(double x, double y) {
        this.x = x;
        this.y = y;
        this.projectileY = y; // Setze die Anfangsposition des Projektils auf die Y-Position des kleinen Quadrats
        this.isShooting = false; // Zu Beginn schießt das kleine Quadrat nicht
        this.movingRight = Math.random() < 0.5; // 50% Chance, nach rechts zu bewegen
    }

    public void move(double canvasWidth) {
        // Bewege das kleine Quadrat nach links oder rechts
        if (movingRight) {
            x += 2; // Bewege nach rechts
            if (x + size >= canvasWidth) { // Wenn das kleine Quadrat den rechten Rand erreicht
                movingRight = false; // Wechsel die Richtung
            }
        } else {
            x -= 2; // Bewege nach links
            if (x <= 0) { // Wenn das kleine Quadrat den linken Rand erreicht
                movingRight = true; // Wechsel die Richtung
            }
        }

        // Schüsse abfeuern
        shoot();
    }

    private void shoot() {
        // 2% Chance, dass es schießt
        if (Math.random() < 0.02 && !isShooting) {
            isShooting = true;
            projectileY = y; // Setze die Y-Position des Projektils
        }

        if (isShooting) {
            projectileY += 5; // Bewege das Projektil nach unten
            if (projectileY > 400) { // Wenn das Projektil den unteren Rand erreicht
                isShooting = false; // Stoppe das Schießen
            }
        }
    }

    public void draw(GraphicsContext gc) {
        // Zeichne das kleine Quadrat
        gc.setFill(Color.RED);
        gc.fillRect(x, y, size, size); // Zeichne das kleine Quadrat

        // Zeichne das Projektil
        if (isShooting) {
            gc.setFill(Color.YELLOW); // Farbe des Projektils
            gc.fillRect(x + (size / 2) - 2, projectileY, 4, 10); // Zeichne das Projektil (rechteckig)
        }
    }
}
