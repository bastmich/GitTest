package org.example.class_searchrescue.App;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import org.example.class_searchrescue.Application;

public class Controller {

   public boolean running = false;
    protected static boolean reset = false;

    private Image agentMan;
    private Image agentHelicopter;
    private Image agentDrone;

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
    @FXML  Label status;


    @FXML
    private void startSim(){
        running = true;
        status.setText("Running...");
        app.simController.startSim();

    };
    @FXML
    private void stopSim(){
        running = false;
        status.setText("");
        app.simController.stopSim();
    };
    @FXML
    private void resetSim(){};
    @FXML
    private void loadConfig(){};

    @FXML
    private void changeImageMan(){};
    @FXML
    private void changeImageHelicopter(){};
    @FXML
    private void changeImageDrone(){};

    private void initialisation(){}

}
