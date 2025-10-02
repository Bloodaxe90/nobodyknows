package io.github.game;
import com.badlogic.gdx.files.FileHandle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
            addSprite(name, 4);
        }

        setSprite("front", 0);

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

        if (movingUp) {
            vy += speed;
            setSprite("front", 0);
        }
        if (movingDown) {
            vy -= speed;
            setSprite("back", 0);
        }
        if (movingLeft) {
            vx -= speed;
            setSprite("left", 0);
        }
        if (movingRight) {
            vx += speed;
            setSprite("right", 0);

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
