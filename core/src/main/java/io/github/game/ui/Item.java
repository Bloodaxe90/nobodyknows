package io.github.game.ui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Represents an item in the players inventory
 * Items are identified just by a name which must match a sprite in the hitbox's atlas.
 */
public class Item {

    private final String name; // Name of the item (used to look up its sprite)

    /**
     * Creates a new item
     *
     * @param name the name of the item texture (i.e. keycard)
     */
    public Item(String name) {
        this.name = name;
    }

    /**
     * Gets the item name
     *
     * @return the name of the item
     */
    public String getName() {
        return name;
    }
}
