package io.github.game.utils;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * A class representing a hitbox for an entity. Allows for the
 * handling of collisions.
 */
public class Hitbox {
    private Vector2 offset;
    private Rectangle bounds;

    /**
     * Creates a new hitbox object with offset
     * 
     * @param position A Vector2 holding the coordinates of the entity it is linked with
     * @param size A Vector2 holding the width and height of the hitbox
     * @param offset A Vector2 holding the x and y offset which will be added to the initial
     *      position. The offset allows for the hitbox to have it's actual position in a
     *      different position from the entity it is linked with.
     */
    public Hitbox(Vector2 position, Vector2 size, Vector2 offset) {

        this.bounds = new Rectangle(position.x + offset.x, position.y + offset.y, size.x, size.y);
        this.offset = new Vector2(offset);
    }

    /**
     * Creates a new hitbox object without offset
     * 
     * @param position A Vector2 holding the hitbox's coordinates
     * @param size A Vector2 holding the width and height of the hitbox
     */
    public Hitbox(Vector2 position, Vector2 size) {
        this(position, size, new Vector2(0f, 0f));
    }

    /**
     * Checks if this hitbox collides with another hitbox
     * 
     * @param obstacle The hitbox to check against
     * @return A boolean which is true for collides, and false for does not collide
     */
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
