package io.github.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Tile {
    public static final int SIZE = 16; // Tile size in pixels

    private TileType type;
    private int x, y; // world coordinates
    private Rectangle hitbox;

    public Tile(TileType type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;

        if (type.isCollidable()) {
            this.hitbox = new Rectangle(x, y, SIZE, SIZE);
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(type.getTextureRegion(), x, y, SIZE, SIZE);
    }

    public TileType getType() {
        return type;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
}
