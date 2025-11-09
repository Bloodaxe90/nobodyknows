package io.github.game.entities.building;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import io.github.game.Main;
import io.github.game.entities.player.Player;
import io.github.game.ui.UserIntereface;
import io.github.game.utils.Hitbox;
import io.github.game.utils.interactions.DialogueInteraction;
import io.github.game.utils.interactions.GameWinInteraction;
import io.github.game.utils.interactions.GiveItemInteraction;

/**
 * Manages all building entities in the environment
 * Handles creating buildings, updating them, checking collisions,
 * and rendering them
 */
public class BuildingManager {

    private final TextureAtlas spriteAtlas; // Sprite sheet that holds building textures

    private Array<Building> buildings = new Array<>(); // all active buildings in the envirnoment


    /**
     * Creates and initializes all buildings in the environment
     *
     * @param spriteAtlas sprite sheet for buildings
     */
    public BuildingManager(TextureAtlas spriteAtlas) {
        this.spriteAtlas = spriteAtlas;

        // TODO: temp building placement (needs polish)
        Vector2 size = new Vector2(96, 96);

        buildings.add(new Building("greggs",
            new GiveItemInteraction("sausage_roll", 0, 1, false, true),
            new Vector2(Main.WORLD_SIZE).sub(size.x, size.y * (2f/3f)),
            new Vector2(size), spriteAtlas));

        buildings.add(new Building("pza",
            new DialogueInteraction(0, false, false),
            new Vector2(Main.WORLD_SIZE).scl(16.5f/20f, 11f/16f),
            new Vector2(size), spriteAtlas));

        buildings.add(new Building("rch",
            new GiveItemInteraction("keycard", 0, 1, false, true),
            new Vector2(Main.WORLD_SIZE).scl(7f/8f, 1f/3f),
            new Vector2(size), spriteAtlas));

        buildings.add(new Building("halifax",
            new DialogueInteraction(0, false, false),
            new Vector2(Main.WORLD_SIZE).scl(1.5f/8f, 1f).sub(0f, size.y),
            new Vector2(size),
            new Vector2(0f, 0f),
            new Vector2(size.x - 10f, size.y / 2f),
            spriteAtlas));

        buildings.add(new Building("derwent",
            new DialogueInteraction(0, false, false),
            new Vector2(Main.WORLD_SIZE).scl(0, 4f/6f),
            new Vector2(size),
            new Vector2(10f, 0f),
            new Vector2(size.x - 20f, size.y / 2f),
            spriteAtlas));

        buildings.add(new Building("compsci",
            new DialogueInteraction(0, false, false),
            new Vector2(Main.WORLD_SIZE).scl(4f/8f, 1).sub(0f, size.y * (2f/3f)),
            size, spriteAtlas));

        buildings.add(new Building("home",
            new GameWinInteraction("keycard", 0, false, true),
            new Vector2(Main.WORLD_SIZE).scl(0, 1f/10f),
            new Vector2(size),
            new Vector2(0f, 0f),
            new Vector2(size.x, size.y * (2f/3f)),
            spriteAtlas));

        buildings.add(new Building("central",
            new DialogueInteraction(0, false, false),
            new Vector2(Main.WORLD_SIZE).scl(1f/2f).add(24, 24),
            new Vector2(size),
            new Vector2(10f, 0f),
            new Vector2(size.x - 20f, size.y / 2f),
            spriteAtlas));
    }

    /**
     * Renders all buildings
     *
     * @param batch SpriteBatch used for rendering
     */
    public void render(SpriteBatch batch) {
        if (buildings.notEmpty()) {
            for (Building building : buildings) {
                building.render(batch);
            }
        }
    }

    /**
     * Updates all buildings and handles interactions
     *
     * @param player the player to check interaction with
     * @param ui the UI
     */
    public void update(Player player, UserIntereface ui) {
        if (buildings.notEmpty()) {
            for (Building building : buildings) {
                building.update(player, ui);

                // remove buildings if building isn't active
                if (!building.isActive()) {
                    buildings.removeValue(building, false);
                }
            }
        }
    }

    /**
     * Checks for collisions with buildings and interacts if so
     *
     * @param hitbox the hitbox to test colisions with
     * @return true if collision happened, false otherwise
     */
    public boolean checkCollision(Hitbox hitbox) {
        if (buildings.notEmpty()) {
            for (Building building : buildings) {
                if (building.getHitbox().collides(hitbox)) {
                    building.setInteract(true);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Clean up memory
     */
    public void dispose() {
        spriteAtlas.dispose();
    }

}
