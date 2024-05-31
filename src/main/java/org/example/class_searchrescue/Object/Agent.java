package org.example.class_searchrescue.Object;

import javafx.scene.image.Image;
import org.example.class_searchrescue.App.SimController;
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

    private float maxWindowX;
    private float maxWindowY;

    private float directionAngle ;
    private float incrementStep =1;
    public Agent(float detectionRadius, float communicationRadius,float velocity,Image image,float maxWindowX, float maxWindowY)
    {
        Random random = new Random();
        this.positionX = random.nextInt(800-imageSize);
        this.positionY= random.nextInt(600-imageSize);
        this.directionAngle = random.nextInt(360);
        this.radiusDetection = detectionRadius;
        this.radiusCommunication = communicationRadius;
        this.velocityMagnitude = velocity;
        this.image = image;
        this.maxWindowX = maxWindowX;
        this.maxWindowY = maxWindowY;
        this.state = State.SEARCHING;

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

    public State getState(){return this.state;}

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
        float[] position = new float[2];
        position[0] = this.positionX;
        position[1] = this.positionY;

        switch (state) {
            case State.STANDBY:
                return position;
            case SEARCHING:
                return Way();
            case FOUNDED:
                return position;
            case GOTO:
                this.directionAngle = angleToGo();
                if (stopGoTo())
                {
                    this.state=State.FOUNDED;
                }
                else
                {
                    return Way();
                }

        }
        return new float[0];
    }
    private float[] Way(){

        float[] position = new float[2];
        float distanceToDo = this.incrementStep*this.velocityMagnitude;
        float deltaX;
        float deltaY;
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
                    return 180-angle;
                }
                //Collision Y
                if (this.positionY<0)
                {
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
    private float angleToGo(){
        float angle;

        angle = (float) Math.atan((this.goToPosition[0]-this.positionX)/(this.goToPosition[1]-this.positionY));
        angle = (float) (2*Math.PI-angle);

        return (float) Math.toDegrees(angle);
    }

    private boolean stopGoTo()
    {
        float distanceToDo = this.incrementStep*this.velocityMagnitude;

        if(calculDistance(this.positionX,this.positionY,goToPosition[0],goToPosition[1])<distanceToDo)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private float calculDistance(float position1X, float position1Y,float position2X, float position2Y)
    {
        float deltaX;
        float deltaY;
        deltaX = Math.abs(position1X-position2X);
        deltaY = Math.abs(position1Y-position2Y);
        return (float) Math.sqrt(Math.pow(deltaX,2)+Math.pow(deltaY,2));
    }

    public boolean checkTarget(Target target)
    {
        if (this.state==State.SEARCHING) {
            float distance = calculDistance(this.positionX, this.positionY, target.positionX, target.positionY);
            if (distance <= this.radiusDetection) {
                state = State.FOUNDED;
                return true;
            }
            else
            {
                return false;
            }
        }
        return false;
    }

    public void communication(ArrayList<Agent> agents)
    {
        if(this.state == State.FOUNDED)
        {
            float distance;
            for(int i = 0;i<agents.size();i++)
            {
                distance = calculDistance(this.positionX,this.positionY,agents.get(i).positionX,agents.get(i).positionY);

                if(distance<=this.radiusCommunication && agents.get(i).getState()==State.SEARCHING)
                {
                    agents.get(i).setGoToPosition(this.getPosition());
                }
            }
        }
    }

    public void setGoToPosition(float[] position)
    {
        this.goToPosition = position;
        this.state = State.GOTO;
    }
}
