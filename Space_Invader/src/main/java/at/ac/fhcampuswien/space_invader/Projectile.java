package at.ac.fhcampuswien.space_invader;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Projectile {

    private double x;
    private double y;
    private final double size = 5; // Größe des Projektils
    private final double speed = 5; // Geschwindigkeit des Projektils
    private final boolean isPlayerProjectile; // Flag, ob es das Spieler-Projektil ist

    // Konstruktor für Projektil
    public Projectile(double x, double y, boolean isPlayerProjectile) {
        this.x = x;
        this.y = y;
        this.isPlayerProjectile = isPlayerProjectile;
    }

    // Methode zum Bewegen des Projektils (je nach Richtung)
    public void move() {
        if (isPlayerProjectile) {
            y -= speed; // Spieler-Projektil bewegt sich nach oben
        } else {
            y += speed; // Kleine Quadrate schießen nach unten
        }
    }

    // Methode zum Zeichnen des Projektils
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLACK); // Projektilfarbe
        gc.fillRect(x, y, size, size); // Zeichne das Projektil an der aktuellen Position
    }

    // Überprüfen, ob das Projektil den Bildschirm verlässt
    public boolean isOffScreen() {
        return y < 0 || y > 600; // Für den Fall, dass das Projektil den Bildschirm verlassen hat
    }

    // Überprüfen, ob das Projektil mit einem kleinen roten Quadrat kollidiert
    public boolean isColliding(double targetX, double targetY, double targetSize) {
        // Einfacher AABB-Kollisionscheck (Axis-Aligned Bounding Box)
        return x < targetX + targetSize && x + size > targetX &&
                y < targetY + targetSize && y + size > targetY;
    }

    // Überprüfen, ob es das Spieler-Projektil ist
    public boolean isPlayerProjectile() {
        return isPlayerProjectile;
    }
}
