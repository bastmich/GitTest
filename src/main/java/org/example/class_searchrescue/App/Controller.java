package org.example.class_searchrescue.App;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import org.example.class_searchrescue.Application;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import javafx.application.Platform;


public class Controller {

   private boolean running = false;



    private Image agentMan = new Image(SimController.class.getResourceAsStream("/Image/Agent/human.png"));
    private Image agentHelicopter = new Image(SimController.class.getResourceAsStream("/Image/Agent/helicopter.png"));
    private Image agentDrone = new Image(SimController.class.getResourceAsStream("/Image/Agent/drone.png"));
    private Application app ;

    private int targetPositionxFile;
    private int targetPositionyFile;
    private double agentsSpeedFile;
    private int agentsDetectionRangeFile;
    private int agentsCommunicationRangeFile;

    public Controller(Application app) {
        this.app = app;
    }
    public Controller(){}


    @FXML Label targetPositionX;
    @FXML Label targetPositionY;
    @FXML Label agentSpeed;
    @FXML Label agentDetectionRange;
    @FXML Label agentCommunicationRange;
    @FXML Label uploadState;

    @FXML Slider slider;

    @FXML Rectangle SquareMan;
    @FXML Rectangle SquareHelicopter;
    @FXML Rectangle SquareDrone;


 @FXML
 private void initialize() {
  SquareMan.setVisible(true);
  SquareHelicopter.setVisible(false);
  SquareDrone.setVisible(false);
  slider.valueProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
          updateSlider((int) slider.getValue());
      }
  });

 }
    private void updateSlider(int newValue)
    {
        if(!running ||SimController.actualNumberOfFound==SimController.numberOfFounded)
        {
            slider.adjustValue(newValue);
            app.simController.numberOfAgents = newValue;
        }
        else
        {
            slider.adjustValue(app.simController.numberOfAgents);
        }
    }

    @FXML
    private void startSim(){

        if(SquareMan.isVisible())
        {
            SimController.agentImage =agentMan;
        }
        else if(SquareDrone.isVisible())
        {
            SimController.agentImage = agentDrone;
        }
        else if(SquareHelicopter.isVisible())
        {
            SimController.agentImage=agentHelicopter;
        }


        System.out.println(running);
     if (!running && SimController.actualNumberOfFound<SimController.numberOfFounded) {
         System.out.println("run");

         app.simController.startSim();
         updateConfigLabel();
         running = true;
     }
     else if(SimController.actualNumberOfFound<SimController.numberOfFounded)
     {
         System.out.println(SimController.actualNumberOfFound);
         System.out.println(SimController.numberOfFounded);
         System.out.println("restart");
         app.simController.restartSim();
         updateConfigLabel();
     }
     else
     {
         app.simController.resetSim();
     }

    };
    @FXML
    private void stopSim(){
        if (running) {

            app.simController.stopSim();
        }
    };

    @FXML
    private void resetSim(){


        if(SquareMan.isVisible())
        {
            SimController.agentImage =agentMan;
        }
        else if(SquareDrone.isVisible())
        {
            SimController.agentImage = agentDrone;
        }
        else if(SquareHelicopter.isVisible())
        {
            SimController.agentImage=agentHelicopter;
        }

        app.simController.resetSim();
        updateConfigLabel();
    };


    @FXML
    private void loadConfig() {
        // Ouvrir le sélecteur de fichiers
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            File configFile = new File(selectedFile.getAbsolutePath());

            // Vérifier si le fichier existe
            if (!configFile.exists() || !configFile.isFile()) {
                System.out.println("Ce fichier n'existe pas : " + selectedFile.getAbsolutePath());
                return;
            }

            // Utiliser BufferedReader
            try (BufferedReader br = new BufferedReader(new FileReader(configFile))) {
                String line;
                // Lire le fichier
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(" ");
                    switch (parts[0]) {
                        case "target_position_x_y":
                            targetPositionxFile = Integer.parseInt(parts[1]);
                            targetPositionyFile = Integer.parseInt(parts[2]);
                            Platform.runLater(() -> app.simController.target.changePosition((float) targetPositionxFile, (float) targetPositionyFile));
                            break;
                        case "agents_speed":
                            agentsSpeedFile = Double.parseDouble(parts[1]);
                            Platform.runLater(() -> {
                                for (int i = 0; i < app.simController.agents.size(); i++) {
                                    app.simController.agents.get(i).setVelocity((float) agentsSpeedFile);
                                }
                            });
                            break;
                        case "agents_detection_range":
                            agentsDetectionRangeFile = Integer.parseInt(parts[1]);
                            Platform.runLater(() -> {
                                for (int i = 0; i < app.simController.agents.size(); i++) {
                                    app.simController.agents.get(i).setRadiusDetection((float) agentsDetectionRangeFile);
                                }
                            });
                            break;
                        case "agents_communication_range":
                            agentsCommunicationRangeFile = Integer.parseInt(parts[1]);
                            Platform.runLater(() -> {
                                for (int i = 0; i < app.simController.agents.size(); i++) {
                                    app.simController.agents.get(i).changeRadiusCommunication((float) agentsCommunicationRangeFile);
                                }
                            });
                            break;
                    }
                }
                Platform.runLater(() -> uploadState.setText("File uploaded successfully"));
            } catch (IOException e) {
                System.out.println("Erreur lors de la lecture du fichier de configuration : " + e.getMessage());
            }

            // Appeler updateConfigLabel sur le thread JavaFX
            if (running) {
                Platform.runLater(this::updateConfigLabel);
            }
        } else {
            System.out.println("Aucun fichier sélectionné.");
        }
    }

    @FXML
    private void changeImageMan(){

     for(int i=0;i<app.simController.agents.size();i++)
     {
       app.simController.agents.get(i).changeImage(agentMan);
     }
     System.out.println("man");

     //Visibility management

            SquareMan.setVisible(true);
            SquareHelicopter.setVisible(false);
            SquareDrone.setVisible(false);

    };
    @FXML
    private void changeImageHelicopter(){
     for(int i=0;i<app.simController.agents.size();i++)
     {
      app.simController.agents.get(i).changeImage(agentHelicopter);
     }
     //Visibility management

            SquareMan.setVisible(false);
            SquareHelicopter.setVisible(true);
            SquareDrone.setVisible(false);

    };
    @FXML
    private void changeImageDrone(){
     for(int i=0;i<app.simController.agents.size();i++)
     {
      app.simController.agents.get(i).changeImage(agentDrone);
     }

     //Visibility management

            SquareMan.setVisible(false);
            SquareHelicopter.setVisible(false);
            SquareDrone.setVisible(true);

    };

    private void updateConfigLabel()
    {
        targetPositionX.setText(Float.toString(app.simController.target.getPositionX()+50));
        targetPositionY.setText(Float.toString(app.simController.target.getPositionY()+50));
        agentSpeed.setText(Double.toString(app.simController.agents.get(0).getVelocity()));
        agentDetectionRange.setText(Float.toString(app.simController.agents.get(0).getRadiusDetection()));
        agentCommunicationRange.setText(Float.toString(app.simController.agents.get(0).radiusCommunication()));

    }

}
