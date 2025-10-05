package io.github.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;

public class Player extends Entity {

    private boolean movingUp, movingDown, movingLeft, movingRight;
    private final Environment environment;

    public Player(float xPos, float yPos,
                  int width, int height,
                  float hitboxXOffset, float hitboxYOffset,
                  int hitboxWidth, int hitboxHeight,
                  float speed,
                  TextureAtlas spriteAtlas,
                  Environment environment) {
        super(xPos, yPos, width, height, hitboxXOffset, hitboxYOffset, hitboxWidth, hitboxHeight, speed, true, spriteAtlas);
        this.environment = environment;
        // Sets up the sprite movement map
        for (String name : new String[]{"front", "back", "left", "right"}) {
            addAnimation(name, 0.1f, Animation.PlayMode.LOOP);
        }

        setSprite("front", stateTime);
    }

    public Player(float xPos, float yPos,
                  int width, int height,
                  float speed,
                  TextureAtlas spriteAtlas,
                  Environment environment) {
        this(xPos, yPos, width, height, 0f, 0f, width, height, speed, spriteAtlas, environment);
    }

    @Override
    public void render(SpriteBatch batch) {

        batch.draw(sprite, xPos, yPos, width, height);
    }

    @Override
    public void update(float delta_t) {
        vx = 0;
        vy = 0;

        if (movingLeft) vx = -speed;
        if (movingRight) vx = speed;
        if (movingUp) vy = speed;
        if (movingDown) vy = -speed;

        xPos += vx * delta_t;
        hitbox.setXPos(xPos);

        if (xPos < 0 || xPos + width > Main.WORLD_WIDTH) {
            vx = 0;
            xPos = MathUtils.clamp(xPos, 0, Main.WORLD_WIDTH - width);

        } else {
            hitbox.setXPos(xPos);
            if (environment.checkCollision(this)) {
                xPos -= vx * delta_t;
                vx = 0;
            }
        }

        yPos += vy * delta_t;
        hitbox.setYPos(yPos);

        if (yPos < 0 || yPos + height > Main.WORLD_HEIGHT) {
            vy = 0;
            yPos = MathUtils.clamp(yPos, 0, Main.WORLD_HEIGHT - height);

        } else {
            hitbox.setYPos(yPos);
            if (environment.checkCollision(this)) {
                yPos -= vy * delta_t;
                vy = 0;
            }
        }

        stateTime += delta_t;

        updateSprite();
    }

    private void updateSprite() {
        if (vx > 0) {
            setSprite("right", stateTime);
        } else if (vx < 0) {
            setSprite("left", stateTime);
        } else if (vy > 0) {
            setSprite("back", stateTime);
        } else if (vy < 0) {
            setSprite("front", stateTime);
        }
    }

    public void setMovingUp(boolean movingUp) {
        this.movingUp = movingUp;
    }

    public void setMovingDown(boolean movingDown) {
        this.movingDown = movingDown;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }
}
