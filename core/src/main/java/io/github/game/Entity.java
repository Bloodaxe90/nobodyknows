package io.github.game;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import java.util.*;

public abstract class Entity {
    protected float xPos, yPos;
    protected int width, height;

    protected float speed;
    protected float vx, vy;

    protected Rectangle hitbox;
    protected boolean collidable;

    protected Map<String, Animation<TextureRegion>> animationMap = new HashMap<>();
    protected float stateTime = 0f;

    protected TextureAtlas spriteAtlas;
    protected Sprite sprite;

    public Entity(float xPos, float yPos,
                  int width, int height,
                  float speed,
                  float vx, float vy,
                  boolean collidable,
                  TextureAtlas spriteAtlas) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.vx = vx;
        this.vy = vy;
        this.collidable = collidable;
        this.spriteAtlas = spriteAtlas;

        if (this.collidable) {
            this.hitbox = new Rectangle(xPos, yPos, this.width, this.height);
        }
    }

    public Entity(float xPos, float yPos, int width, int height, float speed, boolean collidable, TextureAtlas spriteAtlas) {
        //Entity if you want some initial velocity
        this(xPos, yPos, width, height, speed, 0, 0, collidable, spriteAtlas);
    }

    public abstract void render(SpriteBatch batch);

    public abstract void update(float delta_t);

    public void addAnimation(String name, int frames, float duration) {
        // Used to add an animation,
        // name: name of the animation,
        // frames: the number of frames in the animation (1 for un-animated sprites)
        //  - NOTE: each image in the atlas for that animation should have a number in its file name
        //          indication its order in the animation
        // duration: duration of the animation (1f for un-animated sprites)

        Array<TextureRegion> textureFrames = new Array<>();
        for (int i = 0; i < frames; i++) {
            textureFrames.add(new TextureRegion(spriteAtlas.findRegion(name + (i + 1))));
        }
        animationMap.put(name, new Animation<>(duration, textureFrames, Animation.PlayMode.LOOP));
    }

    public void dispose() {
        spriteAtlas.dispose();
    }

    public void setSprite(String name, float stateTime) {
        sprite = new Sprite(animationMap.get(name).getKeyFrame(stateTime));
    }

}
