package org.example.class_searchrescue.Object;

import javafx.scene.image.Image;

public class Target extends ObjectScheme{

    private int founded;

    public Target(Image image)
    {
        this.image=image;
    }

    @Override
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

    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    public boolean isCommunication(ObjectScheme object) {
        return false;
    }

    public int getFounded()
    {
        return this.founded;
    }

    public void newFounded()
    {
        this.founded +=1;
    }
}
