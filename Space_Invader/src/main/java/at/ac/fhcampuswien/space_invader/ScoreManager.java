package at.ac.fhcampuswien.space_invader;

import java.io.*;
import java.util.*;

public class ScoreManager {

    private static final String FILE_PATH = "scores.txt"; // Pfad zur Score-Datei

    // Methode zum Überprüfen und Erstellen der Datei, falls sie nicht existiert
    private static void ensureFileExists() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try {
                // Datei erstellen, wenn sie nicht existiert
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Player> loadScores() {
        ensureFileExists(); // Überprüfe, ob die Datei existiert

        List<Player> players = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String name = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    players.add(new Player(name, score));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Sortiere nach Score absteigend
        players.sort((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));
        return players;
    }

    public static void saveScores(List<Player> players) {
        ensureFileExists(); // Stelle sicher, dass die Datei existiert

        // Schreibe die Liste der Spieler in die Datei (maximal 10 Einträge)
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Player player : players) {
                bw.write(player.getName() + ":" + player.getScore());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static boolean isNewHighScore(Player newPlayer) {
        List<Player> players = loadScores();

        // Wenn weniger als 10 Spieler existieren, ist der neue Spieler ein Highscore
        if (players.size() < 10) {
            return true;
        }

        // Überprüfe, ob der neue Score besser ist als der niedrigste der 10 besten Scores
        return newPlayer.getScore() > players.get(players.size() - 1).getScore();
    }

    private static boolean scoreSaved = false; // Flag, um Mehrfachspeicherung zu verhindern
    public static void resetScoreSaved() {
        scoreSaved = false;
    }

    public static void addPlayerIfHighScore(Player newPlayer) {
        if (scoreSaved) {
          return; // Beende die Methode, wenn der Score schon gespeichert wurde
        }

        List<Player> players = loadScores(); // Lade die bestehenden Scores aus der Datei

        // Überprüfe, ob der neue Spieler ein Highscore ist
        if (isNewHighScore(newPlayer)) {
            // Entferne den niedrigsten Score, falls die Liste voll ist
            if (players.size() >= 10) {
                players.remove(players.size() - 1); // Entfernt den Spieler mit dem niedrigsten Score
            }

            // Füge den neuen Spieler hinzu und sortiere die Liste
            players.add(newPlayer);
            players.sort((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));

            // Speichere die aktualisierte Liste
            saveScores(players);

       } else {

            System.out.println("Kein Highscore erreicht");
        }

        scoreSaved = true; // Markiere, dass der Score gespeichert wurde
    }


}
