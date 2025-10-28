package io.github.game.entities;
import com.badlogic.gdx.graphics.g2d.*;
import io.github.game.utils.Hitbox;
import io.github.game.utils.io.AnimationLoader;

import java.util.*;

public abstract class Entity {
    protected String name;
    protected float xPos, yPos;
    protected int width, height;

    protected float speed;
    protected float vx = 0, vy = 0;

    protected Hitbox hitbox;
    protected boolean collidable;

    protected Map<String, Animation<TextureRegion>> animationMap = new HashMap<>();
    protected float stateTime = 0f;

    protected TextureRegion sprite;

    protected boolean active = true;

    public Entity(String name,
                  float xPos, float yPos,
                  int width, int height,
                  float hitboxXOffset, float hitboxYOffset,
                  int hitboxWidth, int hitboxHeight,
                  float speed,
                  boolean collidable) {
        this.name = name;
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.collidable = collidable;

        //Sets hitbox to be default of
        if (this.collidable) {
            this.hitbox = new Hitbox(xPos, yPos, hitboxWidth, hitboxHeight, hitboxXOffset, hitboxYOffset);
        }
    }

    public Entity(String name, float xPos, float yPos, int width, int height, float speed, boolean collidable) {
        //Entity with Hitbox same size as player
        this(name, xPos, yPos, width, height, 0, 0, width, height, speed, collidable);
    }

    public void render(SpriteBatch batch) {
        if (active) {
            batch.draw(sprite, xPos, yPos, width, height);
        }
    };

    public void addAnimation(String name, float duration, Animation.PlayMode playMode, TextureAtlas spriteAtlas) {
        //  NOTE: each image in the atlas for that animation should have a number at the end of
        //  its file name indication its order in the animation (starting from frame 1)
        animationMap.put(name, AnimationLoader.getAnimation(name, duration, spriteAtlas, playMode));
    }

    public void setSprite(String name, float stateTime) {
        sprite = new Sprite(animationMap.get(name).getKeyFrame(stateTime));
    }

    public float getXPos() {
        return xPos;
    }

    public float getYPos() {
        return yPos;
    }

    public void setXPos(float xPos) {
        this.xPos = xPos;
        hitbox.setXPos(xPos);
    }

    public void setYPos(float yPos) {
        this.yPos = yPos;
        hitbox.setYPos(yPos);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getSpeed() {
        return speed;
    }

    public float getVx() {
        return vx;
    }

    public float getVy() {
        return vy;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Hitbox getHitbox() {
        return hitbox;
    }
}
