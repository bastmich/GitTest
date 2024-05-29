package org.example.class_searchrescue.App;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import org.example.class_searchrescue.Object.Agent;
import org.example.class_searchrescue.Object.Target;

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

    private AnimationTimer animationTimer;

    private Image targetImage = new Image("target.png");
    protected static ArrayList<Agent> agents = new ArrayList<>();

    private Target target = new Target(targetImage);

    public SimController() {

    }

    @FXML
    private void initialize() {
        // Call initializeAnimationTimer() to ensure canvas is initialized before setting up the animation timer
        initializeAnimationTimer();
        initializeAgent();
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
        for(int i=0;i<5;i++)
        {
            agents.add(new Agent(0,0,0));
        }
    }

    private void setupAnimationTimer() {
        animationTimer = new AnimationTimer() {
            final long startNanoTime = System.nanoTime();
            @Override
            public void handle(long now) {

                GraphicsContext gc = canvas.getGraphicsContext2D();
                double t = (now - startNanoTime) / 1000000000.0;

                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                gc.drawImage(target.getImage(), 0, 0);

                for (int i = 0;i< agents.size();i++)
                {
                    float[] newPositions = agents.get(i).updatePosition();
                    agents.get(i).changePosition(newPositions[0],newPositions[1]);
                    gc.drawImage(agents.get(i).getImage(),newPositions[0],newPositions[1]);

                }
            }
        };
        System.out.println("init");
    }


    public void startSim() {
        System.out.println("try");

        initializeAnimationTimer();
        System.out.println(animationTimer);
        if (animationTimer != null) {
            System.out.println("Simulation started");
            animationTimer.start();

        }
    }

    public void stopSim() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
    }
}
