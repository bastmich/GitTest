package org.example.class_searchrescue;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.class_searchrescue.App.SimController;

public class Application extends javafx.application.Application {
    public static SimController simController;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Application.fxml"));
        Parent root = loader.load();

        simController = loader.getController();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.setMaxHeight(800);
        primaryStage.setMaxWidth(1300);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
