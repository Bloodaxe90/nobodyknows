package io.github.game;
public class Hitbox {
    int xPos;
    int yPos;
    int width;
    int height;
    int xOffset;
    int yOffset;

    public Hitbox(int xPos, int yPos, int width, int height, int xOffset, int yOffset) {

        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    // Returns whether this collides with the other hitbox
    public boolean collides(Hitbox obstacle) {
        return xPos < obstacle.xPos + obstacle.width &&
        xPos + width > obstacle.xPos &&
        yPos < obstacle.yPos + obstacle.height &&
        yPos + height > obstacle.yPos;
    }

    // Updates the position of the hitbox
    public void update(int xPos, int yPos) {
        this.xPos = xPos + xOffset;
        this.yPos = yPos + yOffset;
    }

}