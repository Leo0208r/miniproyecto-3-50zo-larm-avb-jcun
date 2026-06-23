module com.example._0zo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example._0zo to javafx.fxml;
    opens com.example._0zo.controller to javafx.fxml;
    opens com.example._0zo.model to javafx.fxml;
    opens com.example._0zo.view to javafx.fxml;
    exports com.example._0zo;
    exports com.example._0zo.controller;
    exports com.example._0zo.model;
    exports com.example._0zo.view;
}