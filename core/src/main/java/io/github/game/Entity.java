package io.github.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;

import java.util.HashMap;
import java.util.Map;

public abstract class Entity {
    protected int xPos, yPos;
    protected int width, height;

    protected float speed;
    protected float vx, vy;

    protected Rectangle hitbox;
    protected boolean collidable;

    protected Map<String, Sprite[]> spriteMap = new HashMap<>();
    protected TextureAtlas spriteAtlas;
    protected Sprite sprite;

    public Entity(int xPos, int yPos,
                  int width, int height,
                  float speed,
                  float vx, float vy,
                  boolean collidable,
                  String atlas_path) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.vx = vx;
        this.vy = vy;
        this.collidable = collidable;
        this.spriteAtlas = new TextureAtlas("/Users/Eric/IdeaProjects/nobodyknows/assets/atlas/player/character.atlas");

        if (this.collidable) {
            this.hitbox = new Rectangle(xPos, yPos, this.width, this.height);
        }
    }

    public Entity(int xPos, int yPos, int width, int height, float speed, boolean collidable, String atlas_path) {
        //Entity if you want some initial velocity
        this(xPos, yPos, width, height, speed, 0, 0, collidable, atlas_path);
    }

    public abstract void render(SpriteBatch batch);

    public abstract void update();


    public void addSprite(String name, int types) {
        Sprite[] sprites = new Sprite[types];
        for (int i = 0; i < types; i++) {
            sprites[i] = new Sprite(spriteAtlas.findRegion(name + (i + 1)));
        }
        spriteMap.put(name, sprites);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(String name, int type) {
        sprite = spriteMap.get(name)[type];
    }

    public boolean isCollidable() {
        return collidable;
    }

    public void setCollidable(boolean collidable) {
        this.collidable = collidable;
    }

    public float getVy() {
        return vy;
    }

    public void setVy(float vy) {
        this.vy = vy;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }

    public float getVx() {
        return vx;
    }

    public void setVx(float vx) {
        this.vx = vx;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
