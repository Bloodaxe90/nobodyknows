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
        inputHandler(delta_t);
    }

    public void inputHandler(float delta_t) {
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
        xPos += (vx * delta_t);
        yPos += (vy * delta_t);
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
