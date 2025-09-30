package io.github.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public enum TileType {

    GRASS("grass", false),
    DIRT("dirt", false),
    WATER("water", true);

    private String name;
    private boolean collidable;
    private TextureRegion textureRegion;

    TileType(String name, boolean collidable) {
        this.name = name;
        this.collidable = collidable;
    }

    public boolean isCollidable() {
        return collidable;
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    public static void loadTileTextures(TextureAtlas atlas) {
        for (TileType type : TileType.values()) {
            type.textureRegion = atlas.findRegion(type.name);
        }
    }
}
