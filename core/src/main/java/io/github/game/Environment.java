package io.github.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Environment {

    private Tile[][] tiles;
    private int width;
    private int height;

    public Environment(TextureAtlas atlas, int[][] environment) {
        TileType.loadTileTextures(atlas);

        this.width = environment[0].length;
        this.height = environment.length;
        this.tiles = new Tile[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {

                int tileValue = environment[row][col];
                TileType type = TileType.values()[tileValue];

                // LibGDX renders from the bottom left so flipped the row index
                int worldX = col * Tile.SIZE;
                int worldY = (height - 1 - row) * Tile.SIZE;

                tiles[row][col] = new Tile(type, worldX, worldY);
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                tiles[row][col].render(batch);
            }
        }
    }

    public Tile getTile(int row, int col) {
        if (row < 0 || row >= height || col < 0 || col >= width) {
            return null;
        }
        return tiles[row][col];
    }
}
