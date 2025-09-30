package io.github.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;

public class player extends Entity {

    public player() {
        super(new Texture("player.png"));
        width = 128;
        height = 128;
        speed = 500;
    }

    // Runs every frame
    public void update(float delta) {
        // Reset velocity of player each frame
        vx = 0;
        vy = 0;

        // Handle movement
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            vx = -speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            vx = speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            vy = speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            vy = -speed;
        }
        // Update player position
        xPos += vx * delta;
        yPos += vy * delta;

    }
}
