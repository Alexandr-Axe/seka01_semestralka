module cz.vse.seka01_semestralka {
    requires javafx.controls;
    requires javafx.fxml;


    opens cz.vse.seka01_semestralka.main to javafx.fxml;
    exports cz.vse.seka01_semestralka.main;
}