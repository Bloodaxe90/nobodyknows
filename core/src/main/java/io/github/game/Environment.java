package io.github.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Environment {

    private Tile[][] environment;
    private int width;
    private int height;
    private TextureAtlas tileAtlas;

    public Environment(int[][] environmentBlueprint, String atlas_path) {
        this.width = environmentBlueprint[0].length;
        this.height = environmentBlueprint.length;
        this.environment = new Tile[height][width];
        this.tileAtlas = new TextureAtlas("/Users/Eric/IdeaProjects/nobodyknows/assets/atlas/environment/tiles.atlas");

        TileType.loadTileTextures(tileAtlas);

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

                environment[row][col] = new Tile(type, worldX, worldY);
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                environment[row][col].render(batch);
            }
        }
    }
}
