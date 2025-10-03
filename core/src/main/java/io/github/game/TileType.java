package io.github.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import io.github.game.utils.AnimationLoader;

public enum TileType {

    // each tile has a value based on the order they are created in this enum
    GRASS("grass", false),
    DIRT("dirt", false),
    WATER("water", true, 0.15f);

    private final String name;
    private final boolean collidable;
    private final float duration;
    private Animation<TextureRegion> animation;

    TileType(String name, boolean collidable, float duration) {
        this.name = name;
        this.collidable = collidable;
        this.duration = duration;
    }
    TileType(String name, boolean collidable) {
        this(name, collidable, 1f);
    }

    public boolean isCollidable() {
        return collidable;
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }

    public static void loadTileAnimations(TextureAtlas tileAtlas) {
        for (TileType type : TileType.values()) {
            type.animation = AnimationLoader.getAnimation(type.name, type.duration, tileAtlas, Animation.PlayMode.LOOP);
        }
    }
}
