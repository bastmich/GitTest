package org.example.class_searchrescue.App;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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



    @FXML
    private void startSim(){
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
    };
    @FXML
    private void changeImageHelicopter(){
     for(int i=0;i<app.simController.agents.size();i++)
     {
      app.simController.agents.get(i).changeImage(agentHelicopter);
     }
    };
    @FXML
    private void changeImageDrone(){
     for(int i=0;i<app.simController.agents.size();i++)
     {
      app.simController.agents.get(i).changeImage(agentDrone);
     }
    };

    private void initialisation(){}

}
