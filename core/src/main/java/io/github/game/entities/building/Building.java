package io.github.game.entities.building;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import io.github.game.entities.Entity;
import io.github.game.entities.player.Player;
import io.github.game.ui.UserIntereface;
import io.github.game.utils.interactions.Interaction;

/**
 * Building entity that stays still and can trigger interactions
 */
public class Building extends Entity {

    private final Interaction interaction; // what happens when you interact with it
    private boolean interact = false; // flag to trigger interaction

    /**
     * Creates a building with custom hitbox
     *
     * @param name building name (used to select the dialogue and animation)
     * @param interaction what happens when player interacts
     * @param position environment position
     * @param size sprite size
     * @param hitboxOffset hitbox offset from sprite
     * @param hitboxSize size of hitbox
     * @param spriteAtlas sprite sheet for building
     */
    public Building(String name, Interaction interaction, Vector2 position, Vector2 size,
                    Vector2 hitboxOffset, Vector2 hitboxSize, TextureAtlas spriteAtlas) {

        // Buildings don't move so speed = 0 and solid = true
        super(name, position, size, hitboxOffset, hitboxSize, 0, true);

        // setting up building animations, as it doesn't have one the animation is just 1 frame
        addAnimation(name, 1f, Animation.PlayMode.LOOP, spriteAtlas);
        setSprite(name, 0);

        this.interaction = interaction;
    }

    /**
     * Simple constructor where hitbox same size as building sprite
     *
     * @param name building name (used to select the dialogue and animation)
     * @param interaction what happens when player interacts
     * @param position environment position
     * @param size size and hitbox size
     * @param spriteAtlas sprite sheet for building
     */
    public Building(String name, Interaction interaction, Vector2 position, Vector2 size,
                    TextureAtlas spriteAtlas) {

        this(name, interaction, position, size, new Vector2(0, 0), size, spriteAtlas);
    }

    /**
     * Updates the building
     *
     * @param player the player
     * @param ui UI system for showing menus or dialogue
     */
    public void update(Player player, UserIntereface ui) {
        if (!active) return;

        // activate interaction once
        if (interact && interaction != null) {
            interaction.interact(this, player, ui);
            interact = false;
        }
    }


    /**
     * Request the building to do its interaction on next update
     *
     * @param interact true = trigger interaction next frame
     */
    public void setInteract(boolean interact) {
        this.interact = interact;
    }
}
