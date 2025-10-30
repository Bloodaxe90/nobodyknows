package io.github.game.utils;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Hitbox {
    private Vector2 offset;
    private Rectangle bounds;


    public Hitbox(Vector2 position, Vector2 size, Vector2 offset) {
        this.bounds = new Rectangle(position.x + offset.x, position.y + offset.y, size.x, size.y);
        this.offset = offset;
    }

    public Hitbox(Vector2 position, Vector2 size) {
        this(position, size, new Vector2(0f, 0f));
    }

    public boolean collides(Hitbox obstacle) {
        return this.bounds.overlaps(obstacle.getBounds());
    }

    public Vector2 getSize() {
        return new Vector2(this.bounds.getWidth(), this.bounds.getHeight());
    }

    public void setSize(Vector2 size) {
        this.bounds.setWidth(size.x);
        this.bounds.setHeight(size.y);
    }

    public float getX() {
        return this.bounds.getX();
    }

    public float getY() {
        return this.bounds.getY();
    }

    public void setXPos(float XPos) {
        this.bounds.setX(XPos + offset.x);
    }

    public void setYPos(float YPos) {
        this.bounds.setY(YPos + offset.y);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setOffset(Vector2 offset) {
        this.offset.x = offset.x;
        this.offset.y = offset.y;
    }
}
