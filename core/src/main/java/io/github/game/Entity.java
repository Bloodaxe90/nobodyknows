package io.github.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;

import java.util.*;

public abstract class Entity {
    protected float xPos, yPos;
    protected int width, height;

    protected float speed;
    protected float vx, vy;
    protected static final List<String> ACTIONS = List.of("UP", "DOWN", "LEFT", "RIGHT");


    protected Rectangle hitbox;
    protected boolean collidable;

    protected Map<String, Sprite[]> spriteMap = new HashMap<>();
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

    public abstract void update();

    public void move(String action) {
        vx = 0;
        vy = 0;
        float delta = Gdx.graphics.getDeltaTime();

        if (ACTIONS.contains(action)) {
            if (action.equals("UP")) {
                vy += speed;
            }
            if (action.equals("DOWN")) {
                vy -= speed;
            }
            if (action.equals("LEFT")) {
                vx -= speed;
            }
            if (action.equals("RIGHT")) {
                vx += speed;
            }
        }

        xPos += (vx * delta);
        yPos += (vy * delta);
    }

    public void addSprite(String name, int types) {
        Sprite[] sprites = new Sprite[types];
        for (int i = 0; i < types; i++) {
            sprites[i] = new Sprite(spriteAtlas.findRegion("" + name + (i + 1)));
        }
        spriteMap.put(name, sprites);
    }

    public void dispose() {
        spriteAtlas.dispose();
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

    public float getxPos() {
        return xPos;
    }

    public void setxPos(float xPos) {
        this.xPos = xPos;
    }

    public float getyPos() {
        return yPos;
    }

    public void setyPos(float yPos) {
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
