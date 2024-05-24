package org.example.class_searchrescue.Application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.class_searchrescue.Object.Agent;

import java.util.ArrayList;

public class Controller {

    protected static boolean running = false;
    protected static boolean reset = false;

    private Image agentMan;
    private Image agentHelicopter;
    private Image agentDrone;

    @FXML float targetPositionX;
    @FXML float getTargetPositionY;
    @FXML float agentSpeed;
    @FXML float agentDetetctionRange;
    @FXML float agentCommunicationRange;


    @FXML
    private void startSim(){
        running = true;
    };
    @FXML
    private void stopSim(){
        running = false;
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
