package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.models.Project;
import org.example.views.Menu;


/**
 * JavaFX App
 */
public class App extends Application {

    public static Project project;
    public static Scene scene;
    @Override
    public void start(Stage stage) {
        Menu menu = new Menu();
        scene = new Scene(menu.getRoot());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}