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
 * JavaFX controller for the main menu screen.
 *
 * <p>Allows the user to choose the number of CPU opponents (1–3) and start the
 * game. One {@link HumanPlayer} is always added first; the selected number of
 * {@link MachinePlayer} instances are added afterward. The player list is then
 * passed to {@link com.example._0zo.view.GameStage} before transitioning to the
 * game screen.</p>
 *
 * @author Leonardo Alexis
 * @author Julio Cesar
 * @author Alejandro Velez
 * @version 1.0
 */
public class MenuController {

    @FXML private Button btn1;
    @FXML private Button btn2;
    @FXML private Button btn3;
    @FXML private Button btnPlay;

    private int selectedPlayers = 0;

    private static final String BTN_BASE =
            "-fx-background-color: #032415; -fx-text-fill: #fbbf24; " +
                    "-fx-border-color: #fbbf24; -fx-border-radius: 6; " +
                    "-fx-font-size: 20px; -fx-cursor: hand;";

    private static final String BTN_SELECTED =
            "-fx-background-color: #fbbf24; -fx-text-fill: #021f13; " +
                    "-fx-border-color: #fbbf24; -fx-border-radius: 6; " +
                    "-fx-font-size: 20px; -fx-cursor: hand; -fx-font-weight: bold;";

    private static final String BTN_PLAY_DISABLED =
            "-fx-background-color: #555; -fx-text-fill: #999; " +
                    "-fx-font-weight: bold; -fx-font-size: 18px; " +
                    "-fx-background-radius: 8; -fx-cursor: default;";

    private static final String BTN_PLAY_ENABLED =
            "-fx-background-color: #fbbf24; -fx-text-fill: #021f13; " +
                    "-fx-font-weight: bold; -fx-font-size: 18px; " +
                    "-fx-background-radius: 8; -fx-cursor: hand;";

    /**
     * Initialises the menu: disables the Play button until a player count is chosen.
     */
    @FXML
    public void initialize() {
        btnPlay.setDisable(true);
        btnPlay.setStyle(BTN_PLAY_DISABLED);
    }

    /**
     * Handles a click on the "1 CPU" button.
     */
    @FXML
    private void onBtn1Clicked() { selectPlayers(1); }

    /**
     * Handles a click on the "2 CPUs" button.
     */
    @FXML
    private void onBtn2Clicked() { selectPlayers(2); }

    /**
     * Handles a click on the "3 CPUs" button.
     */
    @FXML
    private void onBtn3Clicked() { selectPlayers(3); }

    /**
     * Handles a click on the Play button; starts the game if a count is selected.
     */
    @FXML
    private void onPlayClicked() { startGame(); }

    /**
     * Highlights the chosen player-count button, enables Play, and plays a bounce animation.
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

        // Bounce animation on selected button
        Button selected = (num == 1) ? btn1 : (num == 2) ? btn2 : btn3;
        ScaleTransition st = new ScaleTransition(Duration.millis(120), selected);
        st.setFromX(1.0); st.setFromY(1.0);
        st.setToX(1.15);  st.setToY(1.15);
        st.setAutoReverse(true);
        st.setCycleCount(2);
        st.play();
    }

    /**
     * Builds the player list based on the current selection and launches the game view.
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