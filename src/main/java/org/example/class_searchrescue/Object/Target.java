package org.example.class_searchrescue.Object;

import javafx.scene.image.Image;

public class Target extends ObjectScheme{

    private int Founded;

    public Target(float positionX,float positionY,float radius, Image image)
    {
        this.positionX = positionX;
        this.positionY=positionY;
        this.radiusCommunication=radius;
        this.image=image;
    }

    @Override
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

    public int getFounded()
    {
        return this.Founded;
    }
}
