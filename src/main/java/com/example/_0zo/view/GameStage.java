package com.example._0zo.view;

import com.example._0zo.model.players.Player;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * GameStage is a utility class that manages the game view and scene.
 *
 * This class handles loading the game FXML file, setting up the scene,
 * and managing transitions between game and other stages.
 */
public class GameStage {

    private static Stage gameStage;
    private static List<Player> players;
    private static final String FXML_PATH = "/com/example/_0zo/game-view.fxml";

    /**
     * Private constructor to prevent instantiation.
     */
    private GameStage() {
        throw new UnsupportedOperationException("GameStage is a utility class.");
    }

    /**
     * Sets the primary stage for the game.
     *
     * @param stage the Stage to use for displaying the game
     */
    public static void setStage(Stage stage) {
        gameStage = stage;
    }

    /**
     * Sets the list of players for the current game.
     *
     * @param playerList the list of players participating in the game
     */
    public static void setPlayers(List<Player> playerList) {
        players = playerList;
    }

    /**
     * Gets the list of players in the current game.
     *
     * @return the list of players
     */
    public static List<Player> getPlayers() {
        return players;
    }

    /**
     * Displays the game view by loading the game FXML and showing it in the stage.
     *
     * @throws RuntimeException if the FXML file cannot be loaded
     */
    public static void showView() {
        FXMLLoader loader = new FXMLLoader(
                GameStage.class.getResource(FXML_PATH)
        );

        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load game.fxml", e);
        }

        Scene scene = new Scene(root, 800, 620);
        gameStage.setScene(scene);
        gameStage.setTitle("Cincuentazo - Game");
        gameStage.getIcons().add(new Image(
                Objects.requireNonNull(GameStage.class.getResource("/com/example/_0zo/Icons/poker.png")).toString()
        ));
        gameStage.show();
    }

    /**
     * Closes the game stage.
     */
    public static void deleteView() {
        if (gameStage != null) {
            gameStage.close();
        }
    }
}
