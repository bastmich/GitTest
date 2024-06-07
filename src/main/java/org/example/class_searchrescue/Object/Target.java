package org.example.class_searchrescue.Object;

import javafx.scene.image.Image;

/**
 * The Target class represents a target in the simulation.
 * The target has a position and can track how many times it has been found.
 */
public class Target extends ObjectScheme {

    private int founded;
    private int imageSize = 100;

    /**
     * Constructs a Target with the specified image.
     *
     * @param image the image representing the target
     */
    public Target(Image image) {
        this.image = image;
    }

    /**
     * Changes the image of the target.
     *
     * @param image the new image for the target
     */
    @Override
    public void changeImage(Image image) {
        this.image = image;
    }

    /**
     * Changes the position of the target.
     *
     * @param positionX the new X coordinate of the target
     * @param positionY the new Y coordinate of the target
     */
    @Override
    public void changePosition(float positionX, float positionY) {
        // Center the target image on the specified coordinates
        this.positionX = positionX - (imageSize / 2);
        this.positionY = positionY - (imageSize / 2);
    }

    /**
     * Changes the communication radius of the target.
     *
     * @param radius the new communication radius
     */
    @Override
    public void changeRadiusCommunication(float radius) {
        this.radiusCommunication = radius;
    }

    /**
     * Gets the current position of the target.
     *
     * @return an array containing the X and Y coordinates of the target
     */
    @Override
    public float[] getPosition() {
        // Return the current position as an array
        return new float[]{this.positionX, this.positionY};
    }

    /**
     * Gets the X coordinate of the target.
     *
     * @return the X coordinate
     */
    public float getPositionX() {
        return this.positionX;
    }

    /**
     * Gets the Y coordinate of the target.
     *
     * @return the Y coordinate
     */
    public float getPositionY() {
        return this.positionY;
    }

    /**
     * Gets the communication radius of the target.
     *
     * @return the communication radius
     */
    @Override
    public float radiusCommunication() {
        return this.radiusCommunication;
    }

    /**
     * Gets the image of the target.
     *
     * @return the image of the target
     */
    @Override
    public Image getImage() {
        return this.image;
    }

    /**
     * Determines if the target is within communication range of another object.
     * This implementation always returns false as targets do not communicate.
     *
     * @param object the other object
     * @return always false
     */
    @Override
    public boolean isCommunication(ObjectScheme object) {
        return false;
    }

    /**
     * Gets the number of times the target has been found.
     *
     * @return the number of times the target has been found
     */
    public int getFounded() {
        return this.founded;
    }

    /**
     * Increments the count of how many times the target has been found.
     */
    public void newFounded() {
        this.founded += 1;
    }

    /**
     * Resets the count of how many times the target has been found to zero.
     */
    public void resetFounded() {
        this.founded = 0;
    }

}
