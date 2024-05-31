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

   public boolean running = false;
    protected static boolean reset = false;

    private Image agentMan = new Image("human.png");
    private Image agentHelicopter = new Image("helicopter.png");
    private Image agentDrone = new Image("drone.png");

    private Application app ;

    public Controller(Application app) {
        this.app = app;
    }
    public Controller(){}

    @FXML Label targetPosition;
    @FXML Label TargetPosition;
    @FXML Label agentSpeed;
    @FXML Label agentDetetctionRange;
    @FXML Label agentCommunicationRange;
    @FXML Rectangle SquareMan;
    @FXML Rectangle SquareHelicopter;
    @FXML Rectangle SquareDrone;


 @FXML
 private void initialize() {
  SquareMan.setVisible(true);
  SquareHelicopter.setVisible(false);
  SquareDrone.setVisible(false);

 }


    @FXML
    private void startSim(){
     if (!running) {
         System.out.println("run");
         running = true;
         app.simController.startSim();
     }

     //Visibility management
        if (running) {
            SquareMan.setVisible(true);
            SquareHelicopter.setVisible(false);
            SquareDrone.setVisible(false);
        }
    };
    @FXML
    private void stopSim(){
        if (running) {
            running = false;
            app.simController.stopSim();
        }
    };

    @FXML
    private void resetSim(){};
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
        if (running){
            SquareMan.setVisible(true);
            SquareHelicopter.setVisible(false);
            SquareDrone.setVisible(false);
        }

    };
    @FXML
    private void changeImageHelicopter(){
     for(int i=0;i<app.simController.agents.size();i++)
     {
      app.simController.agents.get(i).changeImage(agentHelicopter);
     }
     //Visibility management
        if (running) {
            SquareMan.setVisible(false);
            SquareHelicopter.setVisible(true);
            SquareDrone.setVisible(false);
        }
    };
    @FXML
    private void changeImageDrone(){
     for(int i=0;i<app.simController.agents.size();i++)
     {
      app.simController.agents.get(i).changeImage(agentDrone);
     }

     //Visibility management
        if (running) {
            SquareMan.setVisible(false);
            SquareHelicopter.setVisible(false);
            SquareDrone.setVisible(true);
        }
    };




    private void initialisation(){}

}
