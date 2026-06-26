package com.example._0zo.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * MenuStage is a utility class that manages the menu view and scene.
 *
 * This class handles loading the menu FXML file, setting up the scene,
 * and managing transitions between menu and game stages.
 */
public class MenuStage {
    private static Stage menuStage;
    private static final String FXML_PATH = "/com/example/_0zo/menu-view.fxml";

    /**
     * Private constructor to prevent instantiation.
     */
    private MenuStage() {
        throw new UnsupportedOperationException("MenuStage is a utility class.");
    }

    /**
     * Sets the primary stage for the menu.
     *
     * @param stage the Stage to use for displaying the menu
     */
    public static void setStage(Stage stage) {
        menuStage = stage;
        GameStage.setStage(stage);
        EndStage.setStage(stage);
    }

    /**
     * Displays the menu view by loading the menu FXML and showing it in the stage.
     */
    public static void showView() {
        FXMLLoader loader = new FXMLLoader(
                MenuStage.class.getResource(FXML_PATH)
        );
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load menu-view.fxml", e);
        }
        Scene scene = new Scene(root, 600, 450);
        menuStage.setScene(scene);
        menuStage.setTitle("Cincuentazo");
        menuStage.getIcons().add(new Image(
                Objects.requireNonNull(MenuStage.class.getResource("/com/example/_0zo/Icons/poker.png")).toString()
        ));
        menuStage.show();
    }

    /**
     * Closes the menu stage.
     */
    public static void deleteView() {
        if (menuStage != null) {
            menuStage.close();
        }
    }
}
