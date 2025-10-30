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

public abstract class Entity {
    protected String name;
    protected Vector2 position;
    protected Vector2 size;

    protected float speed;
    protected Vector2 velocity = new Vector2(0f, 0f);

    protected Hitbox hitbox;
    protected boolean collidable;

    protected Map<String, Animation<TextureRegion>> animationMap = new HashMap<>();
    protected float stateTime = 0f;

    protected TextureRegion sprite;

    protected boolean active = true;

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

        //Sets hitbox to be default of
        if (this.collidable) {
            this.hitbox = new Hitbox(position, hitboxSize, hitboxOffset);
        }
    }

    public Entity(String name, Vector2 position, Vector2 size, float speed, boolean collidable) {
        //Entity with Hitbox same size as player
        this(name, position, size, new Vector2(0f, 0f), size, speed, collidable);
    }

    public void render(SpriteBatch batch) {
        if (active) {
            batch.draw(sprite, position.x, position.y, size.x, size.y);
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

    public Vector2 getPos() {
        return new Vector2(position);
    }

    public void setXPos(Float XPos) {
        position.x = XPos;
        hitbox.setXPos(XPos);
    }

    public void setYPos(Float YPos) {
        position.y = YPos;
        hitbox.setYPos(YPos);
    }


    public Vector2 getSize() {
        return size;
    }

    public float getSpeed() {
        return speed;
    }

    public Vector2 getVelocity() {
        return velocity;
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
