package com.example._0zo.controller;

import com.example._0zo.model.players.HumanPlayer;
import com.example._0zo.model.players.MachinePlayer;
import com.example._0zo.model.players.Player;
import com.example._0zo.view.GameStage;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * JavaFX controller for the main menu screen of Cincuentazo.
 *
 * <p>Lets the user choose the number of CPU opponents (1, 2, or 3) and
 * then start the game. The selected option is visually highlighted and
 * a bounce animation is applied to the chosen button. The Play button
 * remains disabled until a player count has been selected.</p>
 *
 * <p>On game start, a {@link HumanPlayer} named "Tú" and the chosen number
 * of {@link MachinePlayer} instances are created, stored via
 * {@link GameStage#setPlayers(List)}, and the game scene is displayed.</p>
 *
 * @author Leonardo Alexis
 * @author Alejandro Velez
 * @author Julio Cesar
 * @version 1.0
 * @see GameController
 */
public class MenuController {

    /** Button to select 1 CPU opponent. */
    @FXML private Button btn1;

    /** Button to select 2 CPU opponents. */
    @FXML private Button btn2;

    /** Button to select 3 CPU opponents. */
    @FXML private Button btn3;

    /** Button that starts the game; disabled until a player count is selected. */
    @FXML private Button btnPlay;

    /** The number of CPU opponents the user has selected (0 = none chosen yet). */
    private int selectedPlayers = 0;

    // ─── Style constants ──────────────────────────────────────────────────────

    /** Base style applied to unselected count buttons. */
    private static final String BTN_BASE =
            "-fx-background-color: #032415; -fx-text-fill: #fbbf24; " +
                    "-fx-border-color: #fbbf24; -fx-border-radius: 6; " +
                    "-fx-font-size: 20px; -fx-cursor: hand;";

    /** Style applied to the currently selected count button. */
    private static final String BTN_SELECTED =
            "-fx-background-color: #fbbf24; -fx-text-fill: #021f13; " +
                    "-fx-border-color: #fbbf24; -fx-border-radius: 6; " +
                    "-fx-font-size: 20px; -fx-cursor: hand; -fx-font-weight: bold;";

    /** Style for the Play button when no player count has been chosen. */
    private static final String BTN_PLAY_DISABLED =
            "-fx-background-color: #555; -fx-text-fill: #999; " +
                    "-fx-font-weight: bold; -fx-font-size: 18px; " +
                    "-fx-background-radius: 8; -fx-cursor: default;";

    /** Style for the Play button once a player count is selected. */
    private static final String BTN_PLAY_ENABLED =
            "-fx-background-color: #fbbf24; -fx-text-fill: #021f13; " +
                    "-fx-font-weight: bold; -fx-font-size: 18px; " +
                    "-fx-background-radius: 8; -fx-cursor: hand;";

    // ─── Lifecycle ────────────────────────────────────────────────────────────

    /**
     * Initializes the menu controller after FXML injection.
     *
     * <p>Sets the Play button to its disabled state until the user selects
     * a player count.</p>
     */
    @FXML
    public void initialize() {
        btnPlay.setDisable(true);
        btnPlay.setStyle(BTN_PLAY_DISABLED);
    }

    // ─── FXML handlers ────────────────────────────────────────────────────────

    /**
     * Handles a click on the "1 opponent" button.
     */
    @FXML
    private void onBtn1Clicked() { selectPlayers(1); }

    /**
     * Handles a click on the "2 opponents" button.
     */
    @FXML
    private void onBtn2Clicked() { selectPlayers(2); }

    /**
     * Handles a click on the "3 opponents" button.
     */
    @FXML
    private void onBtn3Clicked() { selectPlayers(3); }

    /**
     * Handles a click on the Play button; starts the game if a count has been chosen.
     */
    @FXML
    private void onPlayClicked() { startGame(); }

    // ─── Private helpers ──────────────────────────────────────────────────────

    /**
     * Updates the UI to reflect the newly selected player count and plays a
     * bounce animation on the chosen button.
     *
     * @param num the number of CPU opponents selected (1, 2, or 3)
     */
    private void selectPlayers(int num) {
        selectedPlayers = num;
        btn1.setStyle(num == 1 ? BTN_SELECTED : BTN_BASE);
        btn2.setStyle(num == 2 ? BTN_SELECTED : BTN_BASE);
        btn3.setStyle(num == 3 ? BTN_SELECTED : BTN_BASE);
        btnPlay.setDisable(false);
        btnPlay.setStyle(BTN_PLAY_ENABLED);

        Button selected = (num == 1) ? btn1 : (num == 2) ? btn2 : btn3;
        ScaleTransition st = new ScaleTransition(Duration.millis(120), selected);
        st.setFromX(1.0); st.setFromY(1.0);
        st.setToX(1.15);  st.setToY(1.15);
        st.setAutoReverse(true);
        st.setCycleCount(2);
        st.play();
    }

    /**
     * Builds the player list, stores it in {@link GameStage}, and transitions
     * to the game screen.
     *
     * <p>The list always starts with a {@link HumanPlayer} followed by
     * {@code selectedPlayers} {@link MachinePlayer} instances.</p>
     */
    private void startGame() {
        if (selectedPlayers == 0) return;

        List<Player> players = new ArrayList<>();
        players.add(new HumanPlayer("Tú"));

        for (int i = 1; i <= selectedPlayers; i++) {
            players.add(new MachinePlayer("CPU " + i));
        }

        GameStage.setPlayers(players);
        GameStage.showView();
    }
}