package org.example.class_searchrescue.App;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import org.example.class_searchrescue.Object.Agent;
import org.example.class_searchrescue.Object.Target;

import java.awt.*;
import java.util.ArrayList;

public class SimController {

    @FXML
    private Canvas canvas;

    @FXML
    private Label timerSecond;
    @FXML
    private Label timerMinute;
    @FXML
    private Label status;

    private int numberOfAgents =5;
    static int numberOfFounded = 3;
    static int actualNumberOfFound;

    private AnimationTimer animationTimer;

    private Image targetImage = new Image("target.png");
    static Image agentImage = new Image("human.png");

    private Image clearImage = new Image("Clear.png");
    private Image foundImage = new Image("Rescued.jpg");

    protected static ArrayList<Agent> agents = new ArrayList<>();

    private Target target = new Target(targetImage);




    public SimController() {

    }

    @FXML
    private void initialize() {
        initializeAnimationTimer();
        target.changePosition(400,400);
        System.out.println("initialized");
        System.out.println(animationTimer);
        System.out.println(canvas);

    }

    private void initializeAnimationTimer() {
        // Call setupAnimationTimer after canvas is injected

        System.out.println(canvas);
        if (canvas != null) {
            setupAnimationTimer();
        }
    }

    private void initializeAgent(){
        agents.clear();
        for(int i=0;i<this.numberOfAgents;i++)
        {
            agents.add(new Agent(100,200,10,agentImage,(float)canvas.getWidth(),(float)canvas.getHeight()));
        }
    }

    private void setupAnimationTimer() {
        System.out.println("run");
        animationTimer = new AnimationTimer() {
            final long startNanoTime = System.nanoTime();
            @Override
            public void handle(long now) {
                GraphicsContext gc = canvas.getGraphicsContext2D();
                System.out.println(target.getFounded());
                if(target.getFounded()>=numberOfFounded)
                {
                    for(int i =0;i<agents.size();i++)
                    {
                        agents.get(i).changeImage(clearImage);
                        gc.drawImage(agents.get(i).getImage(), 0, 0);
                    }
                    target.changeImage(foundImage);
                    System.out.println("trouvé");
                    animationTimer.stop();
                }
                else
                {

                double t = (now - startNanoTime) / 1000000000.0;

                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                gc.drawImage(target.getImage(), target.getPositionX(), target.getPositionY());

                for (int i = 0;i< agents.size();i++) {
                    actualNumberOfFound = target.getFounded();
                    float[] newPositions = agents.get(i).updatePosition(target);
                    agents.get(i).changePosition(newPositions[0], newPositions[1]);
                    gc.drawImage(agents.get(i).getImage(), newPositions[0], newPositions[1]);

                    if (agents.get(i).checkTarget(target)) {
                        target.newFounded();
                    }

                    ArrayList<Agent> agentToCommunicate = new ArrayList<>();
                    for (int j = 0; j < agents.size(); j++) {
                        if (j != i) {
                            agentToCommunicate.add(agents.get(j));
                        }
                    }

                    agents.get(i).communication(agentToCommunicate);
                }

                }
            }
        };

    }


    public void startSim() {
        initializeAgent();
        initializeAnimationTimer();
        target.resetFounded();

        System.out.println(animationTimer);
        if (animationTimer != null) {
            System.out.println("Simulation started");
            animationTimer.start();

        }
    }
    public void restartSim(){
        if (animationTimer != null) {
            animationTimer.start();
        }
    }

    public void stopSim() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
    }

    public void resetSim(){
        stopSim();
        startSim();
    }
}
