package org.example.class_searchrescue.Object;

import javafx.scene.image.Image;
import org.example.class_searchrescue.App.SimController;
import java.util.ArrayList;
import java.util.Random;

/**
 * The Agent class represents an agent in the simulation.
 * Each agent has a position, detection radius, communication radius, and state.
 * Agents can search for a target, move towards a target, and communicate with other agents.
 */
public class Agent extends ObjectScheme {

    private float radiusDetection;
    private float[] goToPosition = new float[2];

    /**
     * The State enum represents the possible states of an agent.
     * - SEARCHING: The agent is searching for the target.
     * - GOTO: The agent is moving towards a specific position.
     * - FOUNDED: The agent has found the target.
     * - STANDBY: The agent is idle.
     */
    enum State {
        SEARCHING,
        GOTO,
        FOUNDED,
        STANDBY
    }

    private State state;
    private int imageSize = 100;

    private float maxWindowX;
    private float maxWindowY;

    private float directionAngle;
    private float incrementStep = 1;

    /**
     * Constructs an Agent with the specified properties.
     *
     * @param detectionRadius      the detection radius of the agent
     * @param communicationRadius  the communication radius of the agent
     * @param velocity             the velocity of the agent
     * @param image                the image representing the agent
     * @param maxWindowX           the maximum X coordinate the agent can move to
     * @param maxWindowY           the maximum Y coordinate the agent can move to
     */
    public Agent(float detectionRadius, float communicationRadius, float velocity, Image image, float maxWindowX, float maxWindowY) {
        Random random = new Random();
        this.positionX = random.nextInt(800 - imageSize);
        this.positionY = random.nextInt(600 - imageSize);
        this.directionAngle = random.nextInt(360);
        this.radiusDetection = detectionRadius;
        this.radiusCommunication = communicationRadius;
        this.velocityMagnitude = velocity;
        this.image = image;
        this.maxWindowX = maxWindowX;
        this.maxWindowY = maxWindowY;
        this.state = State.SEARCHING;
    }

    /**
     * Changes the image of the agent.
     *
     * @param image the new image for the agent
     */
    public void changeImage(Image image) {
        this.image = image;
    }

    /**
     * Changes the position of the agent.
     *
     * @param positionX the new X coordinate of the agent
     * @param positionY the new Y coordinate of the agent
     */
    @Override
    public void changePosition(float positionX, float positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    /**
     * Changes the communication radius of the agent.
     *
     * @param radius the new communication radius
     */
    @Override
    public void changeRadiusCommunication(float radius) {
        this.radiusCommunication = radius;
    }

    /**
     * Gets the current position of the agent.
     *
     * @return an array containing the X and Y coordinates of the agent
     */
    @Override
    public float[] getPosition() {
        return new float[]{this.positionX, this.positionY};
    }

    /**
     * Gets the communication radius of the agent.
     *
     * @return the communication radius
     */
    @Override
    public float radiusCommunication() {
        return this.radiusCommunication;
    }

    /**
     * Gets the detection radius of the agent.
     *
     * @return the detection radius
     */
    public float getRadiusDetection() {
        return this.radiusDetection;
    }

    /**
     * Sets the detection radius of the agent.
     *
     */
    public void setRadiusDetection(float radius) {
        this.radiusDetection = radius;
    }
    /**
     * Sets the velocity of the agent.
     *
     */
    public void setVelocity(float velocity){
        this.velocityMagnitude=velocity;
    }

    /**
     * Gets the current state of the agent.
     *
     * @return the current state
     */
    public State getState() {
        return this.state;
    }

    /**
     * Gets the image of the agent.
     *
     * @return the image of the agent
     */
    @Override
    public Image getImage() {
        return this.image;
    }

    /**
     * Determines if the agent is within communication range of another object.
     *
     * @param object the other object
     * @return true if within communication range, false otherwise
     */
    @Override
    public boolean isCommunication(ObjectScheme object) {
        return calculDistance(this.positionX, this.positionY, object.positionX, object.positionY) <= this.radiusCommunication;
    }

    /**
     * Updates the position of the agent based on its current state.
     *
     * @param target the target the agent is searching for
     * @return the new position of the agent
     */
    public float[] updatePosition(Target target) {
        float[] position = {this.positionX, this.positionY};

        //Call different position method in function of the current state
        switch (state) {
            case STANDBY:
                //Stay at the same position
                return position;
            case SEARCHING:
                //Return the standard way
                return Way();
            case FOUNDED:
                //Stay at the same position
                return position;
            case GOTO:
                //Go in ther direction of the other agent
                this.directionAngle = angleToGo();
                //Stop searching if the target is found
                if (stopGoTo()) {
                    //Target is found
                    target.newFounded();
                    this.state = State.FOUNDED;
                    return position;
                } else {
                    //Continue to go in the other agent direction
                    return Way();
                }
        }
        return new float[0];
    }

    /**
     * Calculates the new position of the agent based on its direction and velocity.
     *
     * @return the new position of the agent
     */
    private float[] Way() {
        float[] position = new float[2];
        float distanceToDo = this.incrementStep * this.velocityMagnitude;
        float deltaX;
        float deltaY;
        //The angle is changed is the agent goes into a wall
        this.directionAngle = checkWallCollision(this.directionAngle);

        //The new position is calculated in function of the direction's angle
        if (this.directionAngle >= 0 && this.directionAngle < 90) {
            deltaX = (float) (Math.cos(Math.toRadians(this.directionAngle)) * distanceToDo);
            deltaY = (float) (Math.sin(Math.toRadians(this.directionAngle)) * distanceToDo);
            position[0] = this.positionX + deltaX;
            position[1] = this.positionY - deltaY;
        } else if (this.directionAngle >= 90 && this.directionAngle < 180) {
            deltaX = (float) (Math.cos(Math.toRadians(this.directionAngle - 90)) * distanceToDo);
            deltaY = (float) (Math.sin(Math.toRadians(this.directionAngle - 90)) * distanceToDo);
            position[0] = this.positionX - deltaX;
            position[1] = this.positionY - deltaY;
        } else if (this.directionAngle >= 180 && this.directionAngle < 270) {
            deltaX = (float) (Math.cos(Math.toRadians(this.directionAngle - 180)) * distanceToDo);
            deltaY = (float) (Math.sin(Math.toRadians(this.directionAngle - 180)) * distanceToDo);
            position[0] = this.positionX - deltaX;
            position[1] = this.positionY + deltaY;
        } else if (this.directionAngle >= 270 && this.directionAngle <= 360) {
            deltaX = (float) (Math.cos(Math.toRadians(this.directionAngle - 270)) * distanceToDo);
            deltaY = (float) (Math.sin(Math.toRadians(this.directionAngle - 270)) * distanceToDo);
            position[0] = this.positionX + deltaX;
            position[1] = this.positionY + deltaY;
        }
        return position;
    }

    /**
     * Checks for wall collisions and adjusts the direction angle accordingly.
     *
     * @param angle the current direction angle
     * @return the new direction angle after collision adjustments
     */
    private float checkWallCollision(float angle) {
        //If the agent goes into a wall the opposite angle is returned
        if (this.positionY + this.imageSize > this.maxWindowY || this.positionX + this.imageSize > this.maxWindowX || this.positionY < 0 || this.positionX < 0) {
            if (angle >= 0 && angle < 90) {
                if ((this.positionX + this.imageSize) > this.maxWindowX) {
                    return 180 - angle;
                }
                if (this.positionY < 0) {
                    return 360 - angle;
                }
            } else if (angle >= 90 && angle < 180) {
                if (this.positionX < 0) {
                    return 180 - angle;
                }
                if (this.positionY < 0) {
                    return 360 - angle;
                }
            } else if (angle >= 180 && angle < 270) {
                if (this.positionX < 0) {
                    return angle + 90;
                }
                if (this.positionY + this.imageSize > this.maxWindowY) {
                    return 360 - angle;
                }
            } else if (angle >= 270 && angle < 360) {
                if (this.positionX + this.imageSize > maxWindowX) {
                    return angle - 90;
                }
                if (this.positionY + this.imageSize > maxWindowY) {
                    return 360 - angle;
                }
            }
        } else {
            return angle;
        }
        return angle;
    }

    /**
     * Calculates the angle needed to move towards another agent.
     *
     * @return the angle to move towards the goToPosition
     */
    private float angleToGo() {
        float angle = (float) Math.atan((this.goToPosition[0] - this.positionX) / (this.goToPosition[1] - this.positionY));
        angle = Math.abs(angle);
        angle = (float) Math.toDegrees(angle);

        if (this.positionX < this.goToPosition[0]) {
            if (this.positionY < this.goToPosition[1]) {
                angle = 360 - (90 - angle);
            } else {
                angle = 90 - angle;
            }
        } else {
            if (this.positionY < this.goToPosition[1]) {
                angle = 180 + (90 - angle);
            } else {
                angle = 90 + angle;
            }
        }

        return angle;
    }

    /**
     * Determines if the agent should stop moving towards the goToPosition.
     *
     * @return true if the agent should stop moving, false otherwise
     */
    private boolean stopGoTo() {
        float distanceToDo = this.incrementStep * this.velocityMagnitude;
        return calculDistance(this.positionX, this.positionY, goToPosition[0], goToPosition[1]) < distanceToDo;
    }

    /**
     * Calculates the distance between two points.
     *
     * @param position1X the X coordinate of the first point
     * @param position1Y the Y coordinate of the first point
     * @param position2X the X coordinate of the second point
     * @param position2Y the Y coordinate of the second point
     * @return the distance between the two points
     */
    private float calculDistance(float position1X, float position1Y, float position2X, float position2Y) {
        float deltaX = Math.abs(position1X - position2X);
        float deltaY = Math.abs(position1Y - position2Y);
        return (float) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
    }

    /**
     * Checks if the agent has detected the target.
     *
     * @param target the target to check
     * @return true if the target is within the detection radius, false otherwise
     */
    public boolean checkTarget(Target target) {
        if (this.state == State.SEARCHING) {
            float distance = calculDistance(this.positionX, this.positionY, target.positionX, target.positionY);
            if (distance <= this.radiusDetection) {
                state = State.FOUNDED;
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * Communicates the agent's state to other agents within communication range.
     *
     * @param agents the list of other agents
     */
    public void communication(ArrayList<Agent> agents) {
        if (this.state == State.FOUNDED || this.state == State.GOTO) {
            float distance;
            for (Agent agent : agents) {
                distance = calculDistance(this.positionX, this.positionY, agent.positionX, agent.positionY);
                if (distance <= this.radiusCommunication && agent.getState() == State.SEARCHING) {
                    if (this.state == State.FOUNDED) {
                        agent.setGoToPosition(this.getPosition());
                    } else if (this.state == State.GOTO) {
                        agent.setGoToPosition(this.goToPosition);
                    }
                }
            }
        }
    }

    /**
     * Sets the go-to position for the agent and changes its state to GOTO.
     *
     * @param position the position to move to
     */
    public void setGoToPosition(float[] position) {
        this.goToPosition = position;
        this.state = State.GOTO;
    }
}
