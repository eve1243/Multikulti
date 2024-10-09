package at.ac.fhcampuswien.space_invader;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Projectile {

    private double x; // X-Position des Projektils
    private double y; // Y-Position des Projektils
    private final double width = 4; // Breite des Projektils
    private final double height = 10; // Höhe des Projektils
    private final double speed = 4; // Geschwindigkeit des Projektils

    public Projectile(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Methode zur Bewegung des Projektils nach unten
    public void move() {
        y += speed;
    }

    // Methode zum Zeichnen des Projektils
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(x, y, width, height); // Zeichne das Projektil
    }

    // Überprüfen, ob das Projektil den Bildschirm verlassen hat
    public boolean isOffScreen() {
        return y > 600; // Angenommen, der Canvas ist 600 Pixel hoch
    }

    // Überprüfe, ob das Projektil das Ziel trifft (Hauptquadrat)
    public boolean isColliding(double targetX, double targetY, double targetSize) {
        return x < targetX + targetSize && x + width > targetX && y < targetY + targetSize && y + height > targetY;
    }
}
