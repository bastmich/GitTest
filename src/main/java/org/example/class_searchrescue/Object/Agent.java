package org.example.class_searchrescue.Object;

import javafx.scene.image.Image;
import org.example.class_searchrescue.Object.ObjectScheme;

import java.util.ArrayList;
import java.util.Random;

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
    private int imageSize = 100;

    private float maxWindowX=800;
    private float maxWindowY=600;

    private float directionAngle ;
    private float incrementStep =1;
    public Agent(float detectionRadius, float communicationRadius,float velocity,Image image)
    {
        Random random = new Random();
        this.positionX = random.nextInt(800-imageSize);
        this.positionY= random.nextInt(600-imageSize);
        this.directionAngle = random.nextInt(360);
        this.radiusDetection = detectionRadius;
        this.radiusCommunication = communicationRadius;
        this.velocityMagnitude = velocity;
        this.image = image;

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

    public float[] updatePosition()
    {
        return randomWay();
    }
    private float[] randomWay(){

        float[] position = new float[2];
        float distanceToDo = this.incrementStep*this.velocityMagnitude;
        float deltaX;
        float deltaY;
        System.out.print("X : "+this.positionX);
        System.out.println("   Y : "+this.positionY);
        this.directionAngle= checkWallCollision(this.directionAngle);
        //First cadran
        if(this.directionAngle >= 0 && this.directionAngle < 90)
        {
            //Calcul of deltaX and deltaY
            deltaX = (float) (Math.cos(Math.toRadians(this.directionAngle))*distanceToDo);
            deltaY = (float) (Math.sin(Math.toRadians(this.directionAngle))*distanceToDo);

            //Update position
            position[0]= this.positionX+deltaX;
            position[1]= this.positionY-deltaY;
        }
        //Second cadran
        else if(this.directionAngle >= 90 && this.directionAngle < 180)
        {
            //Calcul of deltaX and deltaY
            deltaX = (float) (Math.cos(Math.toRadians(this.directionAngle-90))*distanceToDo);
            deltaY = (float) (Math.sin(Math.toRadians(this.directionAngle-90))*distanceToDo);

            //Update position
            position[0]= this.positionX-deltaX;
            position[1]= this.positionY-deltaY;
        }
        //Third cadran
        else if(this.directionAngle >= 180 && this.directionAngle < 270)
        {
            //Calcul of deltaX and deltaY
            deltaX = (float) (Math.cos(Math.toRadians(this.directionAngle-180))*distanceToDo);
            deltaY = (float) (Math.sin(Math.toRadians(this.directionAngle-180))*distanceToDo);

            //Update position
            position[0]= this.positionX-deltaX;
            position[1]= this.positionY+deltaY;
        }
        //Fourth cadran
        else if(this.directionAngle >= 270 && this.directionAngle < 360)
        {
            //Calcul of deltaX and deltaY
            deltaX = (float) (Math.cos(Math.toRadians(this.directionAngle-270))*distanceToDo);
            deltaY = (float) (Math.sin(Math.toRadians(this.directionAngle-270))*distanceToDo);

            //Update position
            position[0]= this.positionX+deltaX;
            position[1]= this.positionY+deltaY;
        }
        return position;
    }
//
    private float checkWallCollision(float angle)
    {
        if (this.positionY+this.imageSize > this.maxWindowY ||this.positionX+this.imageSize > this.maxWindowX||this.positionY <0||this.positionX <0)
        {

            //First cadran
            if(angle >= 0 && angle < 90)
            {
                //Collision X

                if ((this.positionX+this.imageSize)>this.maxWindowX)
                {
                    System.out.println("Depasse X");
                    return 180-angle;
                }
                //Collision Y
                if (this.positionY<0)
                {
                    System.out.println("Depasse Y");
                    return 360-angle;

                }
            }
            //Second cadran
            else if(angle >= 90 && angle < 180)
            {
                //Collision X
                if (this.positionX<0)
                {
                    return 180-angle;
                }
                //Collision Y
                if (this.positionY<0)
                {
                    return 360-angle;
                }
            }
            //Third cadran
            else if(angle >= 180 && angle < 270)
            {
                //Collision X
                if (this.positionX<0)
                {
                    return angle+90;
                }
                //Collision Y
                if (this.positionY+this.imageSize>this.maxWindowY)
                {
                    return 360-angle;
                }
            }
            //Fourth cadran
            else if(angle >= 270 && angle < 360)
            {
                //Collision X
                if (this.positionX+this.imageSize>maxWindowX)
                {
                    return angle-90;
                }
                //Collision Y
                if (this.positionY+this.imageSize>maxWindowY)
                {
                    return 360-angle;
                }
            }
        }
        else
        {
            return angle;
        }
        return angle;
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
