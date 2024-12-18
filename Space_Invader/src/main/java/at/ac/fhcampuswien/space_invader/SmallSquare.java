package at.ac.fhcampuswien.space_invader;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SmallSquare {

    private double x; // X-Position des kleinen Quadrats
    private double y; // Y-Position des kleinen Quadrats
    private final double size = 20; // Größe des kleinen Quadrats
    private boolean movingRight; // Bewegungsrichtung des kleinen Quadrats
    private Projectile projectile; // Aktuelles Projektil
    private long lastShootTime = 0; // Zeitpunkt des letzten Schusses
    private final long shootCooldown = 2000000000L; // 2 Sekunden Cooldown in Nanosekunden
    private boolean movingDown = true; // Startet mit Bewegung nach unten
    private double speed; // Geschwindigkeit des kleinen Quadrats

    public SmallSquare(double x, double y) {
        this.x = x;
        this.y = y;
        this.movingRight = Math.random() > 0.5;
        this.movingDown = true; // Standardmäßig nach unten
        this.speed = 1 + Math.random() ;// Zufällige Bewegungsrichtung (rechts oder links)
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    // Methode zum Bewegen des kleinen Quadrats
    public void move(double canvasWidth) {
        if (movingRight) {
            x += 2; // Bewege nach rechts
            if (x + size >= canvasWidth) {
                movingRight = false; // Ändere Richtung, wenn der rechte Rand erreicht ist
            }
        } else {
            x -= 2; // Bewege nach links
            if (x <= 0) {
                movingRight = true; // Ändere Richtung, wenn der linke Rand erreicht ist
            }
        }

        // Bewege das Projektil, wenn eines existiert
        if (projectile != null) {
            projectile.move();
        }
    }

    // Methode zum Zeichnen des kleinen Quadrats
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillRect(x, y, size, size); // Zeichne das kleine Quadrat

        // Zeichne das Projektil, falls vorhanden
        if (projectile != null) {
            projectile.draw(gc);
        }
    }

    // Methode zum Schießen eines Projektils
    public void shootProjectile() {
        if (projectile == null || projectile.isOffScreen()) {
            projectile = new Projectile(x + size / 2 - 2, y + size); // Erstelle ein neues Projektil unter dem Quadrat
        }
    }

    // Überprüfe, ob ein Projektil abgeschossen werden kann (alle 2 Sekunden)
    public boolean canShootProjectile(long currentTime) {
        if (currentTime - lastShootTime >= shootCooldown) {
            lastShootTime = currentTime;
            return true;
        }
        return false;
    }

    // Methode zur Überprüfung, ob das Projektil das Hauptquadrat trifft
    public boolean isProjectileCollidingWith(double targetX, double targetY, double targetSize) {
        if (projectile != null && projectile.isColliding(targetX, targetY, targetSize)) {
            projectile = null; // Lösche das Projektil nach der Kollision
            return true;
        }
        return false;
    }
    public boolean isMovingDown() {

        return movingDown;
    }

    public void setMovingDown(boolean movingDown) {
        this.movingDown = movingDown;
    }

    public void updatePositionWithSpeed() {
        if (movingDown) {
            y += speed; // Nach unten mit individueller Geschwindigkeit
        } else {
            y -= speed; // Nach oben mit individueller Geschwindigkeit
        }
    }
}
