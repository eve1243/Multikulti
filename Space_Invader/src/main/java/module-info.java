module at.ac.fhcampuswien.space_invader {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens at.ac.fhcampuswien.space_invader to javafx.fxml;
    exports at.ac.fhcampuswien.space_invader;
}