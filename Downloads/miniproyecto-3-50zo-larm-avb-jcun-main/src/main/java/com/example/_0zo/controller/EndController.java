package com.example._0zo.controller;

import com.example._0zo.model.players.Player;
import com.example._0zo.view.EndStage;
import com.example._0zo.view.MenuStage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Controller for the End Game View. Handles displaying game results and navigation.
 *
 * This controller shows the winner, game statistics, and provides options to
 * play again (rematch) or return to the main menu.
 */
public class EndController {

    @FXML
    private Label resultIcon;

    @FXML
    private Label resultSubtitle;

    @FXML
    private Label winnerName;

    @FXML
    private Label statRoundsValue;

    @FXML
    private Label statCardsValue;

    @FXML
    private Label statMaxSumValue;

    @FXML
    private Button btnRematch;

    @FXML
    private Button btnMenu;

    /**
     * Initializes the end screen controller. Called automatically by JavaFX.
     */
    @FXML
    public void initialize() {
        Player winner = EndStage.getWinner();
        int totalRounds = EndStage.getTotalRounds();

        if (winner != null) {
            resultIcon.setText("🏆");
            resultSubtitle.setText("¡Ganador de la partida!");
            winnerName.setText(winner.getName());
        } else {
            resultIcon.setText("❌");
            resultSubtitle.setText("No hay ganador");
            winnerName.setText("---");
        }

        // Set statistics
        statRoundsValue.setText(String.valueOf(totalRounds));
        statCardsValue.setText("---"); // Could track this if implemented
        statMaxSumValue.setText("---"); // Could track this if implemented

        // Setup button handlers
        btnRematch.setOnAction(event -> onRematchClicked());
        btnMenu.setOnAction(event -> onMenuClicked());
    }

    /**
     * Handles the rematch button click.
     * Restarts the game with the same number of players.
     */
    @FXML
    private void onRematchClicked() {
        // Return to the menu to start a new game
        // The game setup will be repeated
        MenuStage.showView();
    }

    /**
     * Handles the menu button click.
     * Returns to the main menu.
     */
    @FXML
    private void onMenuClicked() {
        MenuStage.showView();
    }
}
