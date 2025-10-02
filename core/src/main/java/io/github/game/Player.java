package io.github.game;
import com.badlogic.gdx.files.FileHandle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;


public class Player extends Entity {

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
    public void update() {

    }

    public void move(String action) {
        super.move(action);

        if (vx > 0) {
            setSprite("right", 0);
        }
        else if (vx < 0) {
            setSprite("left", 0);
        }
        else if (vy > 0) {
            setSprite("back", 0);
        }
        else if (vy < 0) {
            setSprite("front", 0);
        }
    }
}
