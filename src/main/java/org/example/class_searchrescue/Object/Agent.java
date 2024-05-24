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
        this.image = image;
    }

    @Override
    public void changePosition(float positionX, float positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    @Override
    public void changeRadiusCommunication(float radius) {
        this.radiusCommunication=radius;
    }

    @Override
    public float[] getPosition() {
        float[] returnArray = new float[2];
        returnArray[0] = this.positionX;
        returnArray[1] = this.positionY;
        return returnArray;
    }

    @Override
    public float radiusCommunication() {
        return this.radiusCommunication;
    }
    public float getRadiusDetection(){
        return this.radiusDetection;
    }

    public float setRadiusDetection(){
        return this.radiusDetection;
    }

    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    public boolean isCommunication(ObjectScheme object) {
        if(calculDistance(this.positionX,this.positionY,object.positionX,object.positionY)<=this.radiusCommunication)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private float[] randomWay(){
        return new float[0];
    }
    private float[] goToWay(){
        return new float[0];
    }

    private float calculDistance(float position1X, float position1Y,float position2X, float position2Y)
    {
        float deltaX;
        float deltaY;
        deltaX = Math.abs(position1X-position2X);
        deltaY = Math.abs(position1Y-position2Y);
        return (float) Math.sqrt(Math.pow(deltaX,2)+Math.pow(deltaY,2));
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
