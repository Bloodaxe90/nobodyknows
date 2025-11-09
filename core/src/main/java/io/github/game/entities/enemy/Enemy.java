package io.github.game.entities.enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import io.github.game.Environment;
import io.github.game.entities.Entity;
import io.github.game.entities.player.Player;
import io.github.game.ui.UserIntereface;
import io.github.game.utils.interactions.Interaction;

/**
 * Enemy class that moves toward the player when in range
 * and can trigger an interaction
 */
public class Enemy extends Entity {

    private float range; // how far enemy can detect the player in tiles (-1 = infinite range)
    private final Interaction interaction; // what happens when you interact with it
    private boolean interact = false; // flag to trigger interaction

    /**
     * Creates an enemy with custom hitbox
     *
     * @param name enemy name (used to select the dialogue and animation)
     * @param interaction what happens when enemy interacts (i.e. dialog)
     * @param position starting position in the environment
     * @param size size sprite
     * @param range attack range (in tiles)
     * @param hitboxOffset offset for hitbox
     * @param hitboxSize hitbox size
     * @param speed movement speed
     * @param spriteAtlas sprite sheet
     */
    public Enemy(String name, Interaction interaction, Vector2 position, Vector2 size,
                 float range, Vector2 hitboxOffset, Vector2 hitboxSize,
                 float speed, TextureAtlas spriteAtlas) {

        super(name, position, size, hitboxOffset, hitboxSize, speed, true);

        // load movement and idle animations
        for (String str : new String[]{"front", "back", "left", "right"}) {
            addAnimation(name + "_" + str, 0.1f, Animation.PlayMode.LOOP, spriteAtlas);
        }
        for (String str : new String[]{"idlefront", "idleback", "idleleft", "idleright"}) {
            addAnimation(name + "_" + str, 1f, Animation.PlayMode.LOOP, spriteAtlas);
        }

        this.range = range;
        this.interaction = interaction;

        // default sprite when starting
        setSprite(name + "_front", stateTime);
    }

    /**
     * Simplified constructor with hitbox same size as sprite
     *
     * @param name enemy name (used to select the dialogue and animation)
     * @param interaction what happens when enemy interacts (i.e. dialog)
     * @param position starting position in environment
     * @param size sprite size and hitbox size
     * @param range attack range (in tiles)
     * @param speed movement speed
     * @param spriteAtlas animation atlas
     */
    public Enemy(String name, Interaction interaction, Vector2 position, Vector2 size,
                 float range, float speed, TextureAtlas spriteAtlas) {

        this(name, interaction, position, size, range,
            new Vector2(0f, 0f), size, speed, spriteAtlas);
    }

    /**
     * Updates enemy movement, collision, animations and interaction.
     *
     * @param delta_t time since last update frame
     * @param environment environment
     * @param player the player to chase
     * @param ui UI
     */
    public void update(float delta_t, Environment environment, Player player, UserIntereface ui) {
        if (!active) return;

        // direction vector pointing toward player
        Vector2 dir = player.getPos().sub(this.position);

        // distance to player
        float distanceToPlayer = (float) Math.sqrt(dir.x * dir.x + dir.y * dir.y);

        // if close enough OR unlimited range move toward player
        if (distanceToPlayer <= range * environment.getTileSize() || range < 0) {
            this.velocity.x = (dir.x / distanceToPlayer) * this.speed;
            this.velocity.y = (dir.y / distanceToPlayer) * this.speed;
        } else {
            // stop moving if player too far
            this.velocity = new Vector2(0f, 0f);
        }

        // move x axis and check for collisions
        position.x += velocity.x * delta_t;
        hitbox.setXPos(position.x);
        hitbox.setYPos(position.y);
        if (environment.checkCollision(hitbox)) {
            position.x -= velocity.x * delta_t; // undo move
        }

        // move y axis and check for collisions
        position.y += velocity.y * delta_t;
        hitbox.setXPos(position.x);
        hitbox.setYPos(position.y);
        if (environment.checkCollision(hitbox)) {
            position.y -= velocity.y * delta_t; // undo move
        }

        hitbox.setXPos(position.x);
        hitbox.setYPos(position.y);
        stateTime += delta_t;

        // update sprite
        updateSprite(false);

        // activate interaction once
        if (interact && interaction != null) {
            interaction.interact(this, player, ui);
            interact = false; // reset
        }
    }

    /**
     * Updates the sprite
     *
     * @param isIdle true = idle animation, false = walking animation
     */
    private void updateSprite(boolean isIdle) {
        String prefix = name + (isIdle ? "_idle" : "_");

        // decide sprite based on movement direction
        if (velocity.x > 0) {
            setSprite(prefix + "right", stateTime);
        } else if (velocity.x < 0) {
            setSprite(prefix + "left", stateTime);
        } else if (velocity.y > 0) {
            setSprite(prefix + "back", stateTime);
        } else if (velocity.y < 0) {
            setSprite(prefix + "front", stateTime);
        }
    }

    /**
     * Request the enemy to do its interaction on next update
     *
     * @param interact true = trigger interaction next frame
     */
    public void setInteract(boolean interact) {
        this.interact = interact;
    }
}
