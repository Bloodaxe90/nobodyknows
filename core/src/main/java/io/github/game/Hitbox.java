package io.github.game;

public class Hitbox {

    int xPos;
    int yPos;
    int width;
    int height;
    int xOffset;
    int yOffset;

    public Hitbox(int xPos, int yPos, int width, int height, int xOffset, int yOffsetit ) {

        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
    }

    // Returns whether this collides with the other hitbox
    public boolean collides(Hitbox obstacle) {
        return xPos < obstacle.xPos + obstacle.width &&
        xPos + width > obstacle.xPos &&
        yPos < obstacle.yPos + obstacle.height &&
        yPos + height > obstacle.yPos;
    }

}
