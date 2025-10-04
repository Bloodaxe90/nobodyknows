package io.github.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Player extends Entity {

    private boolean movingUp, movingDown, movingLeft, movingRight;
    private Environment environment;

    public Player(float xPos, float yPos,
                  int width, int height,
                  float speed,
                  TextureAtlas spriteAtlas,
                  Environment environment) {
        super(xPos, yPos, width, height, speed, true, spriteAtlas);
        this.environment = environment;
        // Sets up the sprite movement map
        for (String name : new String[]{"front", "back", "left", "right"}) {
            addAnimation(name, 0.1f, Animation.PlayMode.LOOP);
        }

        setSprite("front", stateTime);
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
        hitbox.setX(xPos);

        if (environment.checkCollision(this)) {
            xPos -= vx * delta_t;
            vx = 0;
            hitbox.setX(xPos);
        }

        yPos += vy * delta_t;
        hitbox.setY(yPos);

        if (environment.checkCollision(this)) {
            yPos -= vy * delta_t;
            vy = 0;
            hitbox.setY(yPos);
        }

        stateTime += delta_t;

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
