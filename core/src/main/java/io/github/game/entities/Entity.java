package io.github.game.entities;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import io.github.game.utils.Hitbox;
import io.github.game.utils.io.AnimationLoader;

/**
 * Base class for all entities in the game (player, enemies...)
 *
 * This gives every entity:
 * - a name
 * - a position and size on the map
 * - optional hitbox for collisions
 * - speed & velocity
 * - animation support
 *
 */
public abstract class Entity {

    protected String name;
    protected Vector2 position;
    protected Vector2 size;

    protected float speed;
    protected Vector2 velocity = new Vector2(0f, 0f);

    // What way the entity is facing (used for animations)
    protected enum Direction {UP, DOWN, LEFT, RIGHT};
    protected Direction currentDirection;

    protected Hitbox hitbox;
    protected boolean collidable;

    // Stores animations by name, like "right" and "idleRight"
    protected Map<String, Animation<TextureRegion>> animationMap = new HashMap<>();
    protected float stateTime = 0f; // time used to pick animation frame

    protected TextureRegion sprite;

    protected boolean active = true;

    /**
     * Full constructor for entities with custom hitbox size and offset
     *
     * @param name entity name
     * @param position location in the world
     * @param size size of the sprite
     * @param hitboxOffset offset so hitbox can be smaller/bigger than sprite
     * @param hitboxSize size of hitbox area
     * @param speed movement speed
     * @param collidable whether the entity should be collidable
     */
    public Entity(String name,
                  Vector2 position,
                  Vector2 size,
                  Vector2 hitboxOffset,
                  Vector2 hitboxSize,
                  float speed,
                  boolean collidable) {

        this.name = name;
        this.position = position;
        this.size = size;
        this.speed = speed;
        this.collidable = collidable;

        // Default facing direction
        currentDirection = Direction.DOWN;

        // Create hitbox only if entity is collidable
        if (this.collidable) {
            this.hitbox = new Hitbox(position, hitboxSize, hitboxOffset);
        }
    }

    /**
     * Shortcut constructor where hitbox = entity size
     * @param name entity name
     * @param position position in environment
     * @param size sprite and hitbox size
     * @param speed movement speed
     * @param collidable should it collide with things
     */
    public Entity(String name, Vector2 position, Vector2 size, float speed, boolean collidable) {
        this(name, position, size, new Vector2(0f, 0f), size, speed, collidable);
    }

    /**
     * Draws the entity sprite if it's active
     * @param batch sprite batch for rendering
     */
    public void render(SpriteBatch batch) {
        if (active) {
            // Draw the current sprite frame
            batch.draw(sprite, position.x, position.y, size.x, size.y);
        }
    };

    /**
     * Adds an animation to this entity from an atlas
     * @param name animation name (base filename)
     * @param duration frame duration
     * @param playMode how animation plays
     * @param spriteAtlas atlas file with frames
     */
    public void addAnimation(String name, float duration, Animation.PlayMode playMode, TextureAtlas spriteAtlas) {
        // Frames have to be named like walk1, walk2, walk3...
        animationMap.put(name, AnimationLoader.getAnimation(name, duration, spriteAtlas, playMode));
    }

    /**
     * Sets current sprite frame based on the animation and time
     * @param name animation name
     * @param stateTime time passed for animation frame picking
     */
    public void setSprite(String name, float stateTime) {
        sprite = new Sprite(animationMap.get(name).getKeyFrame(stateTime));
    }

    /** @return entity position */
    public Vector2 getPos() {
        return new Vector2(position);
    }

    /** Sets X coordinate and moves the hitbox too */
    public void setXPos(Float XPos) {
        position.x = XPos;
        hitbox.setXPos(XPos); // keep hitbox aligned
    }

    /** Sets Y coordinate and moves the hitbox too */
    public void setYPos(Float YPos) {
        position.y = YPos;
        hitbox.setYPos(YPos); // keep hitbox aligned
    }

    /** @return entity size */
    public Vector2 getSize() {
        return size;
    }

    /** @return movement speed */
    public float getSpeed() {
        return speed;
    }

    /** @return current velocity vector */
    public Vector2 getVelocity() {
        return velocity;
    }

    /** @return entity name */
    public String getName() {
        return name;
    }

    /** @return if the entity is active (visible/updates) */
    public boolean isActive() {
        return active;
    }

    /** Sets the activity of the entity */
    public void setActive(boolean active) {
        this.active = active;
    }

    /** @return hitbox for collisions */
    public Hitbox getHitbox() {
        return hitbox;
    }
}
