/*
 * SE1021 - 021
 * Winter 2017
 * Lab: Lab 6 Exceptions
 * Name: Rock Boynton
 * Created: 1/18/18
 */

package boyntonrl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class for the Lab06 JavaFX application
 */
public class Lab06 extends Application {

    /**
     * Width of JavaFX application
     */
    public static final int WIDTH = 677;
    /**
     * Height of JavaFX application
     */
    public static final int HEIGHT = 803;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Lab06.fxml"));
        primaryStage.setTitle("Website Tester");
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
