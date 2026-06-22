package com.example._0zo.controller;

import com.example._0zo.model.players.HumanPlayer;
import com.example._0zo.model.players.MachinePlayer;
import com.example._0zo.model.players.Player;
import com.example._0zo.view.GameStage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the Menu View. Handles player selection and game initialization.
 *
 * This controller manages the main menu where users select the number of machine players
 * (1, 2, or 3) and initiates the game setup.
 */
public class MenuController {

    @FXML
    private Button btn1;

    @FXML
    private Button btn2;

    @FXML
    private Button btn3;

    @FXML
    private Button btnPlay;

    private int selectedPlayers = 0;

    /**
     * Initializes the menu controller. Sets up button event handlers.
     */
    @FXML
    public void initialize() {
        btn1.setOnAction(event -> selectPlayers(1));
        btn2.setOnAction(event -> selectPlayers(2));
        btn3.setOnAction(event -> selectPlayers(3));
        btnPlay.setOnAction(event -> startGame());

        // Initially disable the play button until a selection is made
        btnPlay.setDisable(true);
    }

    /**
     * Handles the selection of the number of machine players.
     *
     * @param numPlayers the number of machine players (1, 2, or 3)
     */
    private void selectPlayers(int numPlayers) {
        selectedPlayers = numPlayers;

        // Visual feedback: update button styles
        btn1.setStyle(numPlayers == 1 ? "-fx-background-color: #4CAF50;" : "");
        btn2.setStyle(numPlayers == 2 ? "-fx-background-color: #4CAF50;" : "");
        btn3.setStyle(numPlayers == 3 ? "-fx-background-color: #4CAF50;" : "");

        // Enable play button
        btnPlay.setDisable(false);
    }

    /**
     * Starts the game by creating players and transitioning to the game view.
     */
    private void startGame() {
        if (selectedPlayers == 0) {
            return; // Should not happen due to button disable logic
        }

        // Create the human player and the selected number of machine players
        List<Player> players = new ArrayList<>();
        players.add(new HumanPlayer("You"));

        for (int i = 1; i <= selectedPlayers; i++) {
            players.add(new MachinePlayer("CPU " + i));
        }

        // Transition to the game stage
        GameStage.setPlayers(players);
        GameStage.showView();
    }
}
