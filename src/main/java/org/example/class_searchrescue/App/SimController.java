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

/**
 * Controller class for the search and rescue simulation.
 */
public class SimController {

    @FXML
    private Canvas canvas;  // Canvas to draw simulation
    @FXML
    private Label LabelS;  // Label to display seconds
    @FXML
    private Label LabelMs;  // Label to display milliseconds

    static int numberOfAgents = 5;  // Number of agents in the simulation
    static int numberOfFounded = 3;  // Number of targets to find to complete the simulation
    static int actualNumberOfFound;  // Number of targets found

    private AnimationTimer animationTimer;  // Timer for animation
    private long stopTime = 0;  // Time when animation was stopped
    private long deltaStopTime = 0;  // Time difference when animation was resumed

    // Load images for target, agents, background, and found indicator
    private static Image targetImage = new Image(SimController.class.getResourceAsStream("/Image/Agent/target.png"));
    static Image agentImage = new Image(SimController.class.getResourceAsStream("/Image/Agent/human.png"));
    private Image backImage = new Image(SimController.class.getResourceAsStream("/Image/Background/back1.jpg"));
    private Image foundImage = new Image(SimController.class.getResourceAsStream("/Image/Background/complete.png"));

    protected static ArrayList<Agent> agents = new ArrayList<>();  // List of agents

    protected static Target target = new Target(targetImage);  // Target object

    /**
     * Constructor for the SimController class.
     */
    public SimController() {
    }

    /**
     * Initializes the controller class. This method is automatically called after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        initializeAnimationTimer();
        target.changePosition(400, 400);
        System.out.println("initialized");
        System.out.println(animationTimer);
        System.out.println(canvas);
    }

    /**
     * Initializes the animation timer. Sets up the timer if the canvas is available.
     */
    private void initializeAnimationTimer() {
        // Call setupAnimationTimer after canvas is injected
        System.out.println(canvas);
        if (canvas != null) {
            setupAnimationTimer();
        }
    }

    /**
     * Initializes agents for the simulation.
     */
    private void initializeAgent() {
        agents.clear();
        for (int i = 0; i < this.numberOfAgents; i++) {
            agents.add(new Agent(50, 100, 10, agentImage, (float) canvas.getWidth(), (float) canvas.getHeight()));
        }
    }

    /**
     * Sets up the animation timer and handles the animation logic.
     */
    private void setupAnimationTimer() {
        System.out.println("run");

        animationTimer = new AnimationTimer() {
            final long startNanoTime = System.nanoTime();

            @Override
            public void handle(long now) {
                GraphicsContext gc = canvas.getGraphicsContext2D();
                displayTime(calculTime(now - startNanoTime - deltaStopTime));
                System.out.println(target.getFounded());
                if (target.getFounded() >= numberOfFounded) {
                    gc.drawImage(backImage, 0, 0);
                    gc.drawImage(foundImage, 150, 200);
                    animationTimer.stop();
                    actualNumberOfFound = target.getFounded();
                    System.out.println("founded");
                } else {
                    double t = (now - startNanoTime) / 1000000000.0;

                    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                    gc.drawImage(backImage, 0, 0);
                    gc.drawImage(target.getImage(), target.getPositionX(), target.getPositionY());

                    for (int i = 0; i < agents.size(); i++) {
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

    /**
     * Calculates time from nanoseconds to seconds and milliseconds.
     *
     * @param actualnS The time in nanoseconds.
     * @return An array with two elements: seconds and milliseconds.
     */
    private int[] calculTime(long actualnS) {
        int[] myTime = new int[2];
        long second = 0;
        long millisecond = 0;
        second = actualnS / 1000000000;
        millisecond = actualnS / 1000000;
        millisecond = millisecond - 1000 * second;

        myTime[0] = (int) second;
        myTime[1] = (int) millisecond;

        return myTime;
    }

    /**
     * Displays the time in the appropriate labels.
     *
     * @param myTime An array with two elements: seconds and milliseconds.
     */
    private void displayTime(int[] myTime) {
        LabelS.setText(String.valueOf(myTime[0]));
        LabelMs.setText(String.valueOf(myTime[1]));
    }

    /**
     * Updates the number of targets that need to be found to complete the simulation.
     */
    private void updateNumberOfFound() {
        numberOfFounded = (numberOfAgents / 2) + 1;
        System.out.println("Number of found : " + numberOfFounded);
    }

    /**
     * Starts the simulation.
     */
    public void startSim() {
        initializeAgent();
        initializeAnimationTimer();
        deltaStopTime = 0;
        stopTime = 0;
        updateNumberOfFound();
        target.resetFounded();
        target.changeImage(targetImage);
        System.out.println(animationTimer);
        if (animationTimer != null) {
            System.out.println("Simulation started");
            animationTimer.start();
        }
    }

    /**
     * Restarts the simulation from the last stopped time.
     */
    public void restartSim() {
        updateNumberOfFound();
        deltaStopTime = System.nanoTime() - stopTime;
        if (animationTimer != null) {
            animationTimer.start();
        }
    }

    /**
     * Stops the simulation.
     */
    public void stopSim() {
        if (animationTimer != null) {
            stopTime = System.nanoTime();
            animationTimer.stop();
        }
    }

    /**
     * Resets and restarts the simulation.
     */
    public void resetSim() {
        stopSim();
        startSim();
    }
}
