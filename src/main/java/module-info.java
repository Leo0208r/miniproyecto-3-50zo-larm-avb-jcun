/**
 * Main module for the Cincuentazo application.
 * Requires JavaFX controls and FXML for the graphical interface.
 */

module com.example._0zo {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example._0zo to javafx.fxml;
    opens com.example._0zo.controller to javafx.fxml;
    opens com.example._0zo.view to javafx.fxml;
    opens com.example._0zo.model to javafx.fxml;
    opens com.example._0zo.model.players to javafx.fxml;
    opens com.example._0zo.model.game to javafx.fxml;

    exports com.example._0zo;
    exports com.example._0zo.controller;
    exports com.example._0zo.view;
    exports com.example._0zo.model;
    exports com.example._0zo.model.players;
    exports com.example._0zo.model.game;
}
