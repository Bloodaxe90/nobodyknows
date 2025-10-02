package io.github.game;
import com.badlogic.gdx.files.FileHandle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;


public class Player extends Entity {

    public Player(int xPos, int yPos,
                  int width, int height,
                  float speed,
                  String atlas_path) {
        super(xPos, yPos, width, height, speed, true, atlas_path);

        // Sets up the sprite movement map
        for (String name : new String[]{"front, back, left, right"}) {
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

    public void handleInputs() {
        // Reset velocity of player
        vx = 0;
        vy = 0;
        float delta = Gdx.graphics.getDeltaTime();

        // Set player velocity based on which input key(s) are pressed
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) vx -= speed;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) vx += speed;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) vy += speed;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) vy -= speed;

        // Update player position based on velocity
        xPos += (int)(vx * delta);
        yPos += (int)(vy * delta);

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
