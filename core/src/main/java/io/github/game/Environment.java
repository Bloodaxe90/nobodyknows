package io.github.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Environment {

    private Tile[][] tiles;
    private int width;
    private int height;
    private final TextureAtlas tilesAtlas;
    private ArrayList<Tile> collidables = new ArrayList<>();

    private float stateTime = 0f;


    public Environment(int[][] environmentBlueprint, TextureAtlas tilesAtlas) {
        this.width = environmentBlueprint[0].length;
        this.height = environmentBlueprint.length;
        this.tiles = new Tile[height][width];
        this.tilesAtlas = tilesAtlas;

        TileType.loadTileAnimations(tilesAtlas);

        createEnvironment(environmentBlueprint);
    }

    private void createEnvironment(int[][] environmentBlueprint) {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {

                int tileValue = environmentBlueprint[row][col];
                TileType type = TileType.values()[tileValue];

                // LibGDX renders from the bottom left so flipped the row index
                int worldX = col * Tile.SIZE;
                int worldY = (height - 1 - row) * Tile.SIZE;
                Tile thisTile = new Tile(type, worldX, worldY);

                // Add tile to collidables if necessary
                if (thisTile.getType().isCollidable()) {
                    collidables.add(thisTile);
                }
                tiles[row][col] = thisTile;
            }
        }
    }

    public Tile[] getCollidables() {
        return collidables.toArray(new Tile[collidables.size()]);
    }

    public void render(SpriteBatch batch) {
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                tile.render(batch);
            }
        }
    }

    public void update(float delta_t) {
        stateTime += delta_t;
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                tile.update(stateTime);
            }
        }
    }

    public void dispose() {
        tilesAtlas.dispose();
    }

    public boolean checkCollision(Entity entity) {
        Hitbox hitbox = entity.hitbox;

        int startCol = (int)(hitbox.getX() / Tile.SIZE);
        int endCol = (int)((hitbox.getX() + hitbox.getWidth()) / Tile.SIZE);
        int startRow = height - (int)((hitbox.getY() + hitbox.getHeight()) / Tile.SIZE) - 1;
        int endRow = height - (int)(hitbox.getY() / Tile.SIZE);

        for (int row = startRow; row <= endRow; row++) {
            for (int col = startCol; col <= endCol; col++) {

                Tile tile = getTile(row, col);

                if (tile != null && tile.getType().isCollidable()) {
                    if (hitbox.collides(tile.getHitbox())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Tile getTile(int row, int col) {
        if (row < 0 || row >= height || col < 0 || col >= width) {
            return null;
        }
        return environment[row][col];
    }
}
