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
    WATER("water", true),
    WATER_ANIMATED("water_animated", true, 0.25f),
    WATER_corner_NW("water_cornerNW", false),
    WATER_N("waterN", false),
    WATER_corner_NE("water_cornerNE", false),
    WATER_W("waterW", false),
    WATER_E("waterE", false),
    WATER_corner_SW("water_cornerSW", false),
    WATER_S("waterS", false),
    WATER_corner_SE("water_cornerSE", false),
    WATER_point_NW("water_pointNW", false),
    WATER_point_NE("water_pointNE", false),
    WATER_point_SW("water_pointSW", false),
    WATER_point_SE("water_pointSE", false);

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
