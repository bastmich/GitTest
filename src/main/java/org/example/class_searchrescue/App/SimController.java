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

    static protected int numberOfAgents = 5;  // Number of agents in the simulation
    static protected int numberOfFounded = 3;  // Number of targets to find to complete the simulation
    static protected int actualNumberOfFound;  // Number of targets found

    private AnimationTimer animationTimer;  // Timer for animation
    private long stopTime = 0;  // Time when animation was stopped
    private long deltaStopTime = 0;  // Time difference when animation was resumed
    static long timeoutTime = 60000000000L;
    private boolean timeout=false;

    // Load images for target, agents, background, and found indicator
    private static Image targetImage = new Image(SimController.class.getResourceAsStream("/Image/Agent/target.png"));
    static Image agentImage = new Image(SimController.class.getResourceAsStream("/Image/Agent/human.png"));
    private Image backImage = new Image(SimController.class.getResourceAsStream("/Image/Background/back1.jpg"));
    private Image foundImage = new Image(SimController.class.getResourceAsStream("/Image/Background/complete.png"));
    private Image timeoutImage = new Image(SimController.class.getResourceAsStream("/Image/Background/timeout.png"));
    private Image deadImage = new Image(SimController.class.getResourceAsStream("/Image/Agent/dead.png"));

    //Default value of the agent
    private float agentVelocityDefault = 10.0f;
    private float agentDetectionRadiusDefault = 50.0f;
    private float agentCommunicationRadiusDefault = 100.0f;

    //Value of the agent
    static float agentVelocityConfig;
    static float agentDetectionRadiusConfig;
    static float agentCommunicationRadiusConfig;

    static boolean isInitialized = false;

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
        System.out.println("Initialize controller ...");
        initializeAnimationTimer();
        target.changePosition(400, 400);
        System.out.println("Controller initialized");
        System.out.println(canvas);
        agentVelocityConfig=agentVelocityDefault;
        agentDetectionRadiusConfig=agentDetectionRadiusDefault;
        agentCommunicationRadiusConfig=agentCommunicationRadiusDefault;
        }

    /**
     * Initializes the animation timer. Sets up the timer if the canvas is available.
     */
    private void initializeAnimationTimer() {
        System.out.println("Initialize animation timer ...");
        // Call setupAnimationTimer after canvas is injected
        if (canvas != null) {
            setupAnimationTimer();
        }
        System.out.println("Animation timer initialized");
    }

    /**
     * Initializes agents for the simulation.
     */
    private void initializeAgent() {
        agents.clear();
        System.out.println("Initialize agents ...");
        if (isInitialized)
        {
            for (int i = 0; i < numberOfAgents; i++) {
                agents.add(new Agent(agentDetectionRadiusConfig, agentCommunicationRadiusConfig, agentVelocityConfig, agentImage, (float) this.canvas.getWidth(), (float) this.canvas.getHeight()));
            }
        }
        else
        {
            for (int i = 0; i < numberOfAgents; i++) {
                System.out.println("+1");
                System.out.println(canvas);
                agents.add(new Agent(agentDetectionRadiusDefault, agentCommunicationRadiusDefault, agentVelocityDefault, agentImage,(float) this.canvas.getWidth(), (float) this.canvas.getHeight()));
                }
        }
        System.out.println("Agents initialized");
    }

    /**
     * Sets up the animation timer and handles the animation logic.
     */
    private void setupAnimationTimer() {
        System.out.println("Setup animation timer");

        animationTimer = new AnimationTimer() {
            final long startNanoTime = System.nanoTime();

            @Override
            public void handle(long now) {
                GraphicsContext gc = canvas.getGraphicsContext2D();
                //Function to display the time on the UI
                displayTime(calculTime(now - startNanoTime - deltaStopTime));

                //Check if the max time is exceeded
                if((now - startNanoTime - deltaStopTime)>=timeoutTime)
                {
                    //Draw final image for timeout
                    gc.drawImage(backImage, 0, 0);
                    gc.drawImage(timeoutImage, 200, 245);
                    target.changeImage(deadImage);
                    gc.drawImage(target.getImage(), target.getPositionX(), target.getPositionY());
                    timeout();
                }
                //Check if the target is founded by all the needed agents
                else if (target.getFounded() >= numberOfFounded) {
                    gc.drawImage(backImage, 0, 0);
                    gc.drawImage(foundImage, 150, 200);
                    animationTimer.stop();
                    actualNumberOfFound = target.getFounded();
                    System.out.println("founded");
                }
                else
                {
                    double t = (now - startNanoTime) / 1000000000.0;
                    //Draw the back image
                    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                    gc.drawImage(backImage, 0, 0);
                    gc.drawImage(target.getImage(), target.getPositionX(), target.getPositionY());

                    for (int i = 0; i < agents.size(); i++) {
                        //Update the number of found
                        actualNumberOfFound = target.getFounded();
                        //Change the position of agent
                        float[] newPositions = agents.get(i).updatePosition(target);
                        agents.get(i).changePosition(newPositions[0], newPositions[1]);
                        //Draw the agent at the new position
                        gc.drawImage(agents.get(i).getImage(), newPositions[0], newPositions[1]);
                        //Check if the agent can find the target
                        if (agents.get(i).checkTarget(target)) {
                            target.newFounded();
                        }
                        //Check if the agent can communicate with other agents
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
     * Updates the variable of the agent ith the variable of the configuration files
     */
    public void updateCongigFile(float velocity,float communicationRadius, float detectionRadius, float targetX, float targetY)
    {
        this.agentVelocityConfig = velocity;
        this.agentCommunicationRadiusConfig = communicationRadius;
        this.agentDetectionRadiusConfig = detectionRadius;
        target.changePosition(targetX,targetY);
        for(int i=0;i<agents.size();i++)
        {
            agents.get(i).setVelocity(velocity);
            agents.get(i).changeRadiusCommunication(communicationRadius);
            agents.get(i).setRadiusDetection(detectionRadius);
        }
        isInitialized=true;
    }
    /**
     * Fonction to stop when the time is too long
     */
    private void timeout()
    {
        timeout=true;
        stopSim();
    }

    /**
     * Starts the simulation.
     */
    protected void startSim() {
        System.out.println("Start the simulation...");
        //Initialize element
        initializeAgent();
        initializeAnimationTimer();
        //Reset the time
        deltaStopTime = 0;
        stopTime = 0;
        //Update and reset number of found
        updateNumberOfFound();
        target.resetFounded();
        //Change the target image to the default image
        target.changeImage(targetImage);
        //Check if the animation timer is correctly initialized
        if (animationTimer != null) {
            System.out.println("Simulation started");
            animationTimer.start();
        }
    }

    /**
     * Restarts the simulation from the last stopped time.
     */
    protected void restartSim() {
        //Update number of found
        updateNumberOfFound();
        //Calculate the time during the stop period
        deltaStopTime = System.nanoTime() - stopTime;
        if (animationTimer != null) {
            animationTimer.start();
        }
    }

    /**
     * Stops the simulation.
     */
    protected void stopSim() {
        if (animationTimer != null) {
            stopTime = System.nanoTime();
            animationTimer.stop();
        }
    }

    /**
     * Resets and restarts the simulation.
     */
    protected void resetSim() {
        timeout=false;
        stopSim();
        startSim();
    }
}
