package io.github.game.entities.enemy;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import io.github.game.Environment;
import io.github.game.Main;
import io.github.game.entities.player.Player;
import io.github.game.ui.UserIntereface;
import io.github.game.utils.Hitbox;
import io.github.game.utils.interactions.DialogueInteraction;
import io.github.game.utils.interactions.TakeItemInteraction;

/**
 * Manages all enemy in the environment
 * Handles creating enemies, updating them, rendering them, and checking collisions
 */
public class EnemyManager {

    private final TextureAtlas spriteAtlas; // Sprite sheet that holds enemy textures

    private Array<Enemy> enemies = new Array<>(); // all active enemies in the world

    /**
     * Creates and initialises all enemies in the environment
     *
     * @param spriteAtlas sprite sheet for buildings
     */
    public EnemyManager(TextureAtlas spriteAtlas) {
        this.spriteAtlas = spriteAtlas;

        Vector2 size = new Vector2(16, 16);
        // TODO: rough enemy placement, can be organized later
        enemies.add(new Enemy("professor",
            new DialogueInteraction(0, true, true),
            new Vector2(Main.WORLD_SIZE).scl(7f/8f, 1f/3f),
            new Vector2(size),
            -1,
            100,
            spriteAtlas));

        enemies.add(new Enemy("comp_student",
            new DialogueInteraction(0, false, true),
            new Vector2(Main.WORLD_SIZE).scl(1f/2f, 9f/10f),
            new Vector2(size),
            0,
            0,
            spriteAtlas));

        enemies.add(new Enemy("child",
            new TakeItemInteraction("keycard", 0, 0, true, true),
            new Vector2(0, Main.WORLD_SIZE.y - 192f),
            new Vector2(size),
            12,
            200,
            spriteAtlas));
    }

    /**
     * render all enemies
     *
     * @param batch SpriteBatch used for rendering
     */
    public void render(SpriteBatch batch) {
        if (enemies.notEmpty()) {
            for (Enemy enemy : enemies) {
                enemy.render(batch);
            }
        }
    }

    /**
     * Updates all enemies and handles interaction
     *
     * @param delta_t time passed since last update
     * @param environment envirnoment to check for collisions with
     * @param player the player to check for collisions with
     * @param ui the UI
     */
    public void update(float delta_t, Environment environment, Player player, UserIntereface ui) {
        if (enemies.notEmpty()) {
            for (Enemy enemy : enemies) {
                enemy.update(delta_t, environment, player, ui);

                // Remove enemy if inactive
                if (!enemy.isActive()) {
                    enemies.removeValue(enemy, false);
                }
            }
        }
    }

    /**
     * Checks for collisions with enemies and interacts if so
     *
     * @param hitbox the hitbox to test colisions with
     * @return true if collision happened, false otherwise
     */
    public boolean checkCollision(Hitbox hitbox) {
        if (enemies.notEmpty()) {
            for (Enemy enemy : enemies) {
                if (enemy.getHitbox().collides(hitbox)) {
                    enemy.setInteract(true);
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
