package io.github.game;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Player extends Entity {

    Map<String, TextureRegion[]> playerTextures = new HashMap<>();


    public Player() {
        super(32, 32);
        hitbox = new Hitbox(xPos, yPos, width, height);
        speed = 200;

        // Sets up the texture map
        playerTextures.put("front", getTextureArray("front"));
        playerTextures.put("back", getTextureArray("back"));
        playerTextures.put("left", getTextureArray("left"));
        playerTextures.put("right", getTextureArray("right"));

        currentTexture = playerTextures.get("front")[0];
        
    }

    // Creates an array of textures for a gives direction
    private TextureRegion[] getTextureArray(String direction) {
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("player/character.atlas"));
        TextureRegion[] textureArray = new TextureRegion[4];
        for (int i = 0; i < 4; i++) {
            textureArray[i] = atlas.findRegion("" + direction + (i + 1));
        }
        return textureArray;
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

        if (vx > 0) {
            currentTexture = playerTextures.get("right")[0];
        }
        else if (vx < 0) {
            currentTexture = playerTextures.get("left")[0];
        }
         else if (vy > 0) {
            currentTexture = playerTextures.get("back")[0];
        }
        else if (vy < 0) {
            currentTexture = playerTextures.get("front")[0];
        }


    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(currentTexture, xPos, yPos, width, height);
    }

    // Runs every frame
    @Override
    public void update(float delta) {
        handleMovement(delta);
    }
}
