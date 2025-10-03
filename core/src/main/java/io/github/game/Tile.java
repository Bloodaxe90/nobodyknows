package io.github.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Tile {
    public static final int SIZE = 16; // Tile size in pixels

    private final TileType type;
    private final int xPos, yPos; // world coordinates
    private Rectangle hitbox;

    private TextureRegion sprite;
    public Tile(TileType type, int xPos, int yPos) {
        this.type = type;
        this.xPos = xPos;
        this.yPos = yPos;

        if (type.isCollidable()) {
            this.hitbox = new Rectangle(xPos, yPos, SIZE, SIZE);
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(sprite, xPos, yPos, SIZE, SIZE);
    }

    public void update(float stateTime) {
        this.sprite = type.getAnimation().getKeyFrame(stateTime);
    }

    public TileType getType() {
        return type;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
}
