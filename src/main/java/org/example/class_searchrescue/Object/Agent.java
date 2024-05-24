package org.example.class_searchrescue.Object;

import javafx.scene.image.Image;
import org.example.class_searchrescue.Object.ObjectScheme;

import java.util.ArrayList;

public class Agent extends ObjectScheme {

    private float radiusDetection;
    private float[] goToPosition = new float[2];
    enum State{
        SEARCHING,
        GOTO,
        FOUNDED,
        STANDBY
    }

    private State state;
    public Agent(float positionX,float positionY, float detectionRadius, float communicationRadius,float velocity, Image image)
    {
        this.positionX = positionX;
        this.positionY= positionY;
        this.radiusDetection = detectionRadius;
        this.radiusCommunication = communicationRadius;
        this.velocityMagnitude = velocity;
        this.image=image;
    }
    public void changeImage(Image image) {

    }

    @Override
    public void changePosition(float positionX, float positionY) {

    }

    @Override
    public void changeRadiusCommunication(float radius) {

    }

    @Override
    public float[] getPosition() {
        return new float[0];
    }

    @Override
    public float radiusCommunication() {
        return 0;
    }

    @Override
    public Image getImage() {
        return null;
    }

    @Override
    public boolean isCommunication(ObjectScheme object) {
        return false;
    }

    private float[] randomWay(){
        return new float[0];
    }
    private float[] goToWay(){
        return new float[0];
    }

    private float calculDistance(float position1X, float position1Y,float position2X, float position2Y)
    {
        return position1X;
    }

    public void checkTarget(Target target)
    {

    }

    public void communication(ArrayList<Agent> agents)
    {

    }

    public void setGoToPosition(float[] position)
    {

    }
}
