package io.github.game.utils;

import com.badlogic.gdx.math.Rectangle;

public class Hitbox {
    private float xOffset, yOffset;
    private Rectangle bounds;


    public Hitbox(float xPos, float yPos, int width, int height, float xOffset, float yOffset) {
        this.bounds = new Rectangle(xPos + xOffset, yPos + yOffset, width, height);
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public Hitbox(float xPos, float yPos, int width, int height) {
        this(xPos, yPos, width, height, 0, 0);
    }

    public boolean collides(Hitbox obstacle) {
        return this.bounds.overlaps(obstacle.getBounds());
    }

    public float getWidth() {
        return this.bounds.getWidth();
    }

    public float getHeight() {
        return this.bounds.getHeight();
    }

    public void setWidth(float width) {
        this.bounds.setWidth(width);
    }

    public void setHeight(float height) {
        this.bounds.setHeight(height);
    }

    public float getX() {
        return this.bounds.getX();
    }

    public float getY() {
        return this.bounds.getY();
    }

    public void setXPos(float xPos) {
        this.bounds.setX(xPos + xOffset);
    }

    public void setYPos(float yPos) {
        this.bounds.setY(yPos + yOffset);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setXOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    public void setYOffset(float yOffset) {
        this.yOffset = yOffset;
    }
}
