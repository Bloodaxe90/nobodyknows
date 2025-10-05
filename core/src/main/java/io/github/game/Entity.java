package io.github.game;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import io.github.game.utils.AnimationLoader;

import java.util.*;

public abstract class Entity {
    protected float xPos, yPos;
    protected int width, height;

    protected float speed;
    protected float vx = 0, vy = 0;

    protected Hitbox hitbox;
    protected boolean collidable;

    protected Map<String, Animation<TextureRegion>> animationMap = new HashMap<>();
    protected float stateTime = 0f;

    protected TextureAtlas spriteAtlas;
    protected TextureRegion sprite;

    public Entity(float xPos, float yPos,
                  int width, int height,
                  float hitboxXOffset, float hitboxYOffset,
                  int hitboxWidth, int hitboxHeight,
                  float speed,
                  boolean collidable,
                  TextureAtlas spriteAtlas) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.collidable = collidable;
        this.spriteAtlas = spriteAtlas;

        //Sets hitbox to be default of
        if (this.collidable) {
            this.hitbox = new Hitbox(xPos, yPos, hitboxWidth, hitboxHeight, hitboxXOffset, hitboxYOffset);
        }
    }

    public Entity(float xPos, float yPos, int width, int height, float speed, boolean collidable, TextureAtlas spriteAtlas) {
        //Entity with Hitbox same size as player
        this(xPos, yPos, width, height, 0, 0, width, height, speed, collidable, spriteAtlas);
    }

    public abstract void render(SpriteBatch batch);

    public void addAnimation(String name, float duration, Animation.PlayMode playMode) {
        //  NOTE: each image in the atlas for that animation should have a number at the end of
        //  its file name indication its order in the animation (starting from frame 1)
        animationMap.put(name, AnimationLoader.getAnimation(name, duration, spriteAtlas, playMode));
    }

    public void dispose() {
        spriteAtlas.dispose();
    }

    public void setSprite(String name, float stateTime) {
        sprite = new Sprite(animationMap.get(name).getKeyFrame(stateTime));
    }
}
