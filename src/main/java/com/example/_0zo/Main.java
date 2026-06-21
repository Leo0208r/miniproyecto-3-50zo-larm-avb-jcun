package com.example._0zo;

import com.example._0zo.view.MenuStage;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        MenuStage.setStage(primaryStage);
        MenuStage.showView();
    }
}
