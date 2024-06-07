package org.example.class_searchrescue;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.class_searchrescue.App.SimController;

/**
 * The main entry point for the JavaFX application.
 * This class extends the javafx.application.Application class.
 */
public class Application extends javafx.application.Application {
    public static SimController simController;

    /**
     * The start method is called after the application is launched.
     * It sets up the primary stage with the specified FXML layout.
     *
     * @param primaryStage the primary stage for this application
     * @throws Exception if an error occurs during loading the FXML file
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file for the main application layout
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Application.fxml"));
        Parent root = loader.load();

        // Get the SimController from the FXML loader
        simController = loader.getController();

        // Set up the scene with the loaded layout
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        // Show the primary stage
        primaryStage.show();

        // Make the primary stage not resizable
        primaryStage.setResizable(false);
    }

    /**
     * The main method launches the JavaFX application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
