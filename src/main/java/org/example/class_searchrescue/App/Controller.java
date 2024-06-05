package org.example.class_searchrescue.App;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import org.example.class_searchrescue.Application;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;


public class Controller {

   private boolean running = false;



    private Image agentMan = new Image("human.png");
    private Image agentHelicopter = new Image("helicopter.png");
    private Image agentDrone = new Image("drone.png");

    private Application app ;

    public Controller(Application app) {
        this.app = app;
    }
    public Controller(){}

    @FXML Label targetPositionX;
    @FXML Label targetPositionY;
    @FXML Label agentSpeed;
    @FXML Label agentDetectionRange;
    @FXML Label agentCommunicationRange;


    @FXML Rectangle SquareMan;
    @FXML Rectangle SquareHelicopter;
    @FXML Rectangle SquareDrone;


 @FXML
 private void initialize() {
  SquareMan.setVisible(true);
  SquareHelicopter.setVisible(false);
  SquareDrone.setVisible(false);
     System.out.println("controller");

 }


    @FXML
    private void startSim(){
        System.out.println(running);
     if (!running && SimController.actualNumberOfFound<SimController.numberOfFounded) {
         System.out.println("run");

         app.simController.startSim();
         running = true;
     }
     else if(SimController.actualNumberOfFound<SimController.numberOfFounded)
     {
         System.out.println("restart");
         app.simController.restartSim();
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
    };
    @FXML
    private void loadConfig(){

        //Open file browser and choose
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(null);
        File selectedFile = fileChooser.getSelectedFile();

        File configFile = new File(selectedFile.getAbsolutePath());

        // Check if file exist
        if (!configFile.exists() || !configFile.isFile()) {
            System.out.println("This file doesn't exist: " + selectedFile.getAbsolutePath());
            return;
        }

        // Use BufferReader
        try (BufferedReader br = new BufferedReader(new FileReader(configFile))) {
            String line;
            //Read file
            while ((line = br.readLine()) != null) {

                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error : " + e.getMessage());
        }
    };

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




}
