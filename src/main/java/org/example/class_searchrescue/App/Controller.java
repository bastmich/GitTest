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


/**
 * Controller class for handling the user interface events and interactions for the Search and Rescue application.
 */
public class Controller {

    //Process variables
   private boolean running = false;
   private boolean pause = false;

    // Images for different types of agents
    private Image agentMan = new Image(SimController.class.getResourceAsStream("/Image/Agent/human.png"));
    private Image agentHelicopter = new Image(SimController.class.getResourceAsStream("/Image/Agent/helicopter.png"));
    private Image agentDrone = new Image(SimController.class.getResourceAsStream("/Image/Agent/drone.png"));

    // Variables for configuration file parameters
    private float targetPositionxFile;
    private float targetPositionyFile;
    private float agentsSpeedFile;
    private float agentsDetectionRangeFile;
    private float agentsCommunicationRangeFile;

    // FXML annotated user interface components
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

    /**
     * Initializes the controller class. This method is automatically called after the FXML file has been loaded.
     */
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

    /**
     * Updates the slider value and the number of agents.
     * @param newValue the new value for the slider.
     */
    private void updateSlider(int newValue)
    {
        if(!running ||SimController.actualNumberOfFound== SimController.numberOfFounded)
        {
            slider.adjustValue(newValue);
            SimController.numberOfAgents = newValue;
        }
        else
        {
            slider.adjustValue(SimController.numberOfAgents);
        }
    }

    /**
     * Starts the simulation.
     */
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

     if (!running && SimController.actualNumberOfFound<SimController.numberOfFounded) {

         Application.simController.startSim();
         updateConfigLabel();
         running = true;
     }
     else if(pause && SimController.actualNumberOfFound<SimController.numberOfFounded)
     {
         System.out.println(SimController.actualNumberOfFound);
         System.out.println(SimController.numberOfFounded);
         System.out.println("restart");
         Application.simController.restartSim();
         pause = false;
         updateConfigLabel();
     }
     else if (SimController.actualNumberOfFound==SimController.numberOfFounded)
     {
         Application.simController.resetSim();
     }
    }

    /**
     * Stops the simulation.
     */
    @FXML
    private void stopSim(){
        if (running) {
            pause = true;
            Application.simController.stopSim();
        }
    }


    /**
     * Resets the simulation.
     */
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

        Application.simController.resetSim();
        updateConfigLabel();
    }

    /**
     * Loads the configuration from a file.
     */
    @FXML
    private void loadConfig() {
        // Open the file selection windows
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            File configFile = new File(selectedFile.getAbsolutePath());

            // Check if the file exist
            if (!configFile.exists() || !configFile.isFile()) {
                System.out.println("Ce fichier n'existe pas : " + selectedFile.getAbsolutePath());
                return;
            }

            // UseBufferedReader
            try (BufferedReader br = new BufferedReader(new FileReader(configFile))) {
                String line;

                // Read the file
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(" ");
                    switch (parts[0]) {
                        case "target_position_x_y":
                            targetPositionxFile = Float.parseFloat(parts[1]);
                            targetPositionyFile = Float.parseFloat(parts[2]);
                            break;
                        case "agents_speed":
                            agentsSpeedFile = Float.parseFloat(parts[1]);
                            break;
                        case "agents_detection_range":
                            agentsDetectionRangeFile = Float.parseFloat(parts[1]);
                            break;
                        case "agents_communication_range":
                            agentsCommunicationRangeFile = Float.parseFloat(parts[1]);
                            break;
                    }
                }
                //Update agents and set correct texts
                uploadState.setText("File uploaded successfully");
                Application.simController.updateCongigFile(agentsSpeedFile,agentsCommunicationRangeFile,agentsDetectionRangeFile,targetPositionxFile,targetPositionyFile);
                updateConfigLabel();
                if (!SimController.isInitialized)
                {
                    agentSpeed.setText(Float.toString(agentsSpeedFile));
                    agentDetectionRange.setText(Float.toString(agentsDetectionRangeFile));
                    agentCommunicationRange.setText(Float.toString(agentsCommunicationRangeFile));
                }

            } catch (IOException e) {
                System.out.println("Erreur lors de la lecture du fichier de configuration : " + e.getMessage());
            }

        } else {
            System.out.println("Aucun fichier sélectionné.");
        }
    }

    /**
     * Changes the agent's image to a man.
     */
    @FXML
    private void changeImageMan(){
     for(int i=0;i<SimController.agents.size();i++)
     {
       SimController.agents.get(i).changeImage(agentMan);
     }
     //Visibility management
        SquareMan.setVisible(true);
        SquareHelicopter.setVisible(false);
        SquareDrone.setVisible(false);
    }

    /**
     * Changes the agent's image to a helicopter.
     */
    @FXML
    private void changeImageHelicopter(){
     for(int i=0;i<SimController.agents.size();i++)
     {
      SimController.agents.get(i).changeImage(agentHelicopter);
     }
     //Visibility management
        SquareMan.setVisible(false);
        SquareHelicopter.setVisible(true);
        SquareDrone.setVisible(false);
    }

    /**
     * Changes the agent's image to a drone.
     */
    @FXML
    private void changeImageDrone(){
     for(int i=0;i<SimController.agents.size();i++)
     {
      SimController.agents.get(i).changeImage(agentDrone);
     }
     //Visibility management
        SquareMan.setVisible(false);
        SquareHelicopter.setVisible(false);
        SquareDrone.setVisible(true);
    }

    /**
     * Updates the configuration labels in the GUI with the current simulation values.
     */
    private void updateConfigLabel()
    {
        targetPositionX.setText(Float.toString(SimController.target.getPositionX()+50));
        targetPositionY.setText(Float.toString(SimController.target.getPositionY()+50));
        agentSpeed.setText(Float.toString(SimController.agents.getFirst().getVelocity()));
        agentDetectionRange.setText(Float.toString(SimController.agents.getFirst().getRadiusDetection()));
        agentCommunicationRange.setText(Float.toString(SimController.agents.getFirst().radiusCommunication()));
    }
}
