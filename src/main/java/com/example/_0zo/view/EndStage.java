package com.example._0zo.view;

import com.example._0zo.model.players.Player;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * EndStage is a utility class that manages the end-game view and scene.
 *
 * This class handles loading the end-game FXML file, displaying game results,
 * and managing transitions back to menu or rematch.
 */
public class EndStage {

    private static Stage endStage;
    private static Player winner;
    private static int totalRounds;
    private static final String FXML_PATH = "/com/example/_0zo/end.fxml";

    /**
     * Private constructor to prevent instantiation.
     */
    private EndStage() {
        throw new UnsupportedOperationException("EndStage is a utility class.");
    }

    /**
     * Sets the primary stage for the end screen.
     *
     * @param stage the Stage to use for displaying the end screen
     */
    public static void setStage(Stage stage) {
        endStage = stage;
    }

    /**
     * Sets the winner of the game.
     *
     * @param gameWinner the Player who won the game
     */
    public static void setWinner(Player gameWinner) {
        winner = gameWinner;
    }

    /**
     * Sets the total number of rounds played.
     *
     * @param rounds the number of rounds
     */
    public static void setTotalRounds(int rounds) {
        totalRounds = rounds;
    }

    /**
     * Gets the winner of the game.
     *
     * @return the winning Player
     */
    public static Player getWinner() {
        return winner;
    }

    /**
     * Gets the total number of rounds played.
     *
     * @return the number of rounds
     */
    public static int getTotalRounds() {
        return totalRounds;
    }

    /**
     * Displays the end screen by loading the end FXML and showing it in the stage.
     *
     * @throws RuntimeException if the FXML file cannot be loaded
     */
    public static void showView() {
        FXMLLoader loader = new FXMLLoader(
                EndStage.class.getResource(FXML_PATH)
        );

        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load end.fxml", e);
        }

        Scene scene = new Scene(root, 480, 560);
        endStage.setScene(scene);
        endStage.setTitle("Cincuentazo - Game Over");
        endStage.getIcons().add(new Image(
                Objects.requireNonNull(EndStage.class.getResource("/com/example/_0zo/Icons/poker.png")).toString()
        ));
        endStage.show();
    }

    /**
     * Closes the end stage.
     */
    public static void deleteView() {
        if (endStage != null) {
            endStage.close();
        }
    }
}
