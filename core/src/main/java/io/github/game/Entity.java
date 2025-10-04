package io.github.game;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import io.github.game.utils.AnimationLoader;

public abstract class Entity {
    protected float xPos, yPos;
    protected int width, height;

    protected float speed;
    protected float vx, vy;

    protected Hitbox hitbox;
    protected boolean collidable;

    protected Map<String, Animation<TextureRegion>> animationMap = new HashMap<>();
    protected float stateTime = 0f;

    protected TextureAtlas spriteAtlas;
    protected TextureRegion sprite;

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
    }

    public Entity(float xPos, float yPos, int width, int height, float speed, boolean collidable, TextureAtlas spriteAtlas) {
        //Entity if you want some initial velocity
        this(xPos, yPos, width, height, speed, 0, 0, collidable, spriteAtlas);
    }

    public abstract void render(SpriteBatch batch);

    public abstract void update(float delta_t, Environment environment);

    public void addAnimation(String name, float duration, Animation.PlayMode playMode) {
        //  NOTE: each image in the atlas for that animation should have a number at the end of
        //  its file name indication its order in the animation (starting from frame 1)
        animationMap.put(name, AnimationLoader.getAnimation(name, duration, spriteAtlas, playMode));
    }

    // Returns true if hitbox collides with any collidables
    public boolean isCollision(Environment environment) {
        Tile[] collidables = environment.getCollidables();

        for (int i = 0; i < collidables.length; i++) {
            if (hitbox.collides(collidables[i].getHitbox())) {
                return true;
            }
        }
        return false;
    }

    public void dispose() {
        spriteAtlas.dispose();
    }

    public void setSprite(String name, float stateTime) {
        sprite = new Sprite(animationMap.get(name).getKeyFrame(stateTime));
    }

}
