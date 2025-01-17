# Space Invader 🚀

Ein klassisches Arcade-Spiel, neu interpretiert von Team **Multikulti**.

## 📋 Projektübersicht

**Space Invader** ist ein Spiel, bei dem der Spieler durch Bewegung und das Abschießen von Projektilen kleine Quadrate zerstören muss, die sich zufällig bewegen. Ziel ist es, einen möglichst hohen Punktestand zu erreichen, ohne alle Leben zu verlieren.

### 🎯 Gewählter Schwierigkeitsgrad: 
**3 (Mittel)**  

Das Projekt ist so konzipiert, dass es für unser Team eine gute Balance zwischen Herausforderung und Machbarkeit bietet.

---

## 👥 Team Multikulti

### Teammitglieder und Rollen:

#### **Teghnoor Singh-Pamma**  
- **Teamleiter:** Gesamtorganisation des Projekts und Koordination der Teammitglieder.  
- **Endscreen & Punktestand:** Entwicklung des Endbildschirms, der den Punktestand und Highscore anzeigt.

#### **Everlyn Gathoni Njeri**  
- **Technische Leitung:** Umsetzung der Spielmechaniken, Projektil-Logik und Kollisionserkennung.  
- **Grafikdesign & Animation:** Erstellung und Animation der kleinen Quadrate.  
- **Technische Dokumentation:** Dokumentation der Kernimplementierungen.

#### **Ljilja Savic**  
- **Benutzeroberfläche (UI):** Design und Implementierung von Menüs und Anzeigen.  
- **Qualitätssicherung:** Testen, Debugging und Performance-Optimierung.

---

## 🕹️ Spielbeschreibung

### Hauptfeatures:
1. **Spielersteuerung:** Der Spieler kann sich horizontal bewegen und Projektile abschießen.  
2. **Bewegung der Gegner:** Kleine Quadrate bewegen sich dynamisch und zufällig.  
3. **Punktestand & Highscore:** Spieler verdienen Punkte durch das Zerstören von Gegnern. Der Highscore wird lokal gespeichert.  
4. **Spielende:** Das Spiel endet, wenn der Spieler keine Leben mehr hat.  

---

## ⚙️ Implementierungsdetails

### 🔄 Bewegungslogik:
- **Spieler:** Der Spieler bewegt sich horizontal mit den Pfeiltasten.  
- **Gegner:** Quadrate bewegen sich horizontal und ändern ihre Richtung, wenn sie den Rand des Spielfelds erreichen.  

### 🔍 Kollisionserkennung:
- **Projektil trifft Gegner:** Das Projektil zerstört das Quadrat und erhöht den Punktestand.  
- **Gegner trifft Spieler:** Der Spieler verliert ein Leben, wenn er getroffen wird.

### 📊 Punktestand & Leben:
- Der Punktestand erhöht sich mit jedem getroffenen Gegner.  
- Leben werden bei Kollisionen mit Gegnern oder deren Projektilen reduziert.

---

## 🛠️ Technologie-Stack

- **Programmiersprache:** JavaScript  
- **Framework/Library:** HTML5 Canvas API für Grafiken  
- **Tools:** IntelliJ, Git/GitHub  

---

## 🚀 Anleitung zur Installation und Ausführung

1. **Repository klonen:**  
   ```bash
   git clone https://github.com/<Benutzername>/space-invader.git
   cd space-invader

