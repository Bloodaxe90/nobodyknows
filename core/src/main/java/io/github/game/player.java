package io.github.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class player extends Entity {

    public player() {
        super(new Texture("player.png"), 128, 128);
        hitBox = new int[] {width, height};
        speed = 500;
    }

    private void handleMovement(float delta) {
        // Reset velocity of player
        vx = 0;
        vy = 0;

        // Set player velocity based on which input key(s) are pressed
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            vx -= speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            vx += speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            vy += speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            vy -= speed;
        }

        // Update player position based on velocity
        xPos += vx * delta;
        yPos += vy * delta;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(entityTexture, xPos, yPos, width, height);
    }

    // Runs every frame
    @Override
    public void update(float delta) {
        handleMovement(delta);
    }
}
