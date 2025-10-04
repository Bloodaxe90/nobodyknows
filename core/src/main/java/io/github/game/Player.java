package io.github.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Player extends Entity {

    private boolean movingUp, movingDown, movingLeft, movingRight;

    public Player(float xPos, float yPos,
                  int width, int height,
                  float speed,
                  TextureAtlas spriteAtlas) {
        super(xPos, yPos, width, height, speed, true, spriteAtlas);
                // Sets player hitbox to lower half of player

        hitbox = new Hitbox(xPos, yPos, (int)(width * 0.5), (int)(height * 0.25), (int)(width * 0.25), 0);

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
    public void update(float delta_t, Environment environment) {
        inputHandler(delta_t, environment);
    }

    public void inputHandler(float delta_t, Environment environment) {
        vx = 0;
        vy = 0;
        stateTime += delta_t;

        if (movingUp) {
            vy += speed;
            setSprite("back", stateTime);
        }
        if (movingDown) {
            vy -= speed;
            setSprite("front", stateTime);
        }
        if (movingLeft) {
            vx -= speed;
            setSprite("left", stateTime);
        }
        if (movingRight) {
            vx += speed;
            setSprite("right", stateTime);
        }

        // Move player if hitbox won't collide
        hitbox.update((xPos + vx * delta_t), (yPos + vy * delta_t));
        if (isCollision(environment)) {
            hitbox.update(xPos, yPos);

            // If trying to move diagonally, try single directions
            if (vx != 0 && vy !=0) {
                // Try moving horizontally
                hitbox.update((xPos + vx * delta_t), yPos);
                if (isCollision(environment)) {
                    hitbox.update(xPos, yPos);
                }
                else {
                    xPos += vx * delta_t;
                }
                // Try moving vertically
                hitbox.update(xPos, (yPos + vy * delta_t));
                if (isCollision(environment)) {
                    hitbox.update(xPos, yPos);
                }
                else {
                    yPos += vy * delta_t;
                }
            }
        }
        else {
            xPos += vx * delta_t;
            yPos += vy * delta_t;
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
