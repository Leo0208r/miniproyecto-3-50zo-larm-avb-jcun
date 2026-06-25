package com.example._0zo;

import com.example._0zo.view.MenuStage;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main application entry point for the Poker game (50/Juego del 50).
 * 
 * This class initializes the JavaFX application and displays the main menu stage.
 * The game supports 1 to 3 players and features both human and computer-controlled players.
 * 
 * @author Leonardo Alexis
 * @author Julio Cesar
 * @author Alejandro Velez
 * @version 1.0
 */
public class Main extends Application {

    /**
     * Main method that launches the JavaFX application.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the JavaFX application and displays the menu.
     *
     * @param primaryStage the primary stage for the application
     */
    @Override
    public void start(Stage primaryStage) {
        MenuStage.setStage(primaryStage);
        MenuStage.showView();
    }
}
