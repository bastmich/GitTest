package org.example.class_searchrescue.Object;

import javafx.scene.image.Image;
import org.example.class_searchrescue.Object.ObjectScheme;

public class Agent extends ObjectScheme {

    private float detectionRadius;
    public Agent(float positionX,float positionY, float detectionRadius, float communicationRadius,float velocity, Image image)
    {
        this.positionX = positionX;
        this.positionY= positionY;
        this.detectionRadius = detectionRadius;
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
}
