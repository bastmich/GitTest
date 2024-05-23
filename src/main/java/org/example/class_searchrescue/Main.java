package org.example.class_searchrescue;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
//Test
//Test flo
public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Charger le fichier FXML
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Application.fxml"));
        Parent root = fxmlLoader.load();

        // Créer une scène avec le contenu du fichier FXML
        Scene scene = new Scene(root);

        // Définir la scène sur le stage et afficher le stage
        stage.setScene(scene);
        stage.setTitle("Search and Rescue");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
