package org.example.class_searchrescue.Application;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.example.class_searchrescue.Object.Agent;
import org.example.class_searchrescue.Object.Target;

import java.util.ArrayList;
import java.util.Calendar;

public class SimController {
    @FXML
    private Canvas canvas;
    @FXML
    private int timerSecond;
    @FXML
    private int timerMinute;

    private Image targetImage;
    protected static ArrayList<Agent> agents=new ArrayList<Agent>(5);
    Target target = new Target(targetImage);
    protected void Run(){
        if (Controller.running)
        {





        }
    }

}