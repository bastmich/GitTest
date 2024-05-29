package org.example.class_searchrescue.App;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import org.example.class_searchrescue.Application;

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
  SquareMan.setVisible(false);
  SquareHelicopter.setVisible(false);
  SquareDrone.setVisible(false);

 }


    @FXML
    private void startSim(){
        System.out.println("run");
        running = true;
        app.simController.startSim();

    };
    @FXML
    private void stopSim(){
        running = false;
        app.simController.stopSim();
    };

    @FXML
    private void resetSim(){};
    @FXML
    private void loadConfig(){};

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




    private void initialisation(){}

}
