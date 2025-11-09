package io.github.game.entities.player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

import io.github.game.Environment;
import io.github.game.Main;
import io.github.game.entities.Entity;
import io.github.game.entities.building.BuildingManager;
import io.github.game.entities.enemy.EnemyManager;
import io.github.game.ui.Hotbar;
import io.github.game.ui.Item;
import io.github.game.utils.Torch;
import io.github.game.utils.io.AudioPlayer;

/**
 * Controls movement, animations, inventory items and collisions
 * Extends Entity
 */
public class Player extends Entity {

    // Keeps track of which movement keys are pressed
    private boolean movingUp, movingDown, movingLeft, movingRight;

    private final Array<Item> inventory; // list of items the player has
    private final Torch torch; // light aura for the player
    private final TextureAtlas spriteAtlas; // where player sprites come from

    private float footstepTimer = 0; // controls footstep sound timing
    private float footstepTimeout;

    /**
     * Creates a Player with custom hitbox
     *
     * @param name player name
     * @param position starting world position
     * @param size sprite size
     * @param hitboxOffset offset for hitbox
     * @param hitboxSize hitbox dimensions
     * @param footstepFrequency how often footstep sounds play
     * @param speed movement speed
     * @param spriteAtlas sprite sheet
     */
    public Player(String name,
                  Vector2 position,
                  Vector2 size,
                  Vector2 hitboxOffset,
                  Vector2 hitboxSize,
                  float footstepFrequency,
                  float speed,
                  TextureAtlas spriteAtlas) {

        super(name, position, size, hitboxOffset, hitboxSize, speed, true);

        this.spriteAtlas = spriteAtlas;
        this.inventory = new Array<>(Hotbar.NUM_SLOTS);
        this.footstepTimeout = 1 / footstepFrequency;

        // load all walking animations and idle animations
        for (String str : new String[]{"front", "back", "left", "right"}) {
            addAnimation(str, 0.1f, Animation.PlayMode.LOOP, spriteAtlas);
        }
        for (String str : new String[]{"idlefront", "idleback", "idleleft", "idleright"}) {
            addAnimation(str, 1f, Animation.PlayMode.LOOP, spriteAtlas);
        }

        this.torch = new Torch();
        addItem("keycard"); // gives starting item

        setSprite("front", stateTime); // default animation
    }

    /**
     * Simplified constructor with hitbox set to sprite size
     *
     * @param name player name
     * @param position starting position
     * @param size size + hitbox size
     * @param footstepFrequency footstep frequency
     * @param speed movement speed
     * @param spriteAtlas sprite sheet atlas
     */
    public Player(String name,
                  Vector2 position,
                  Vector2 size,
                  float footstepFrequency,
                  float speed,
                  TextureAtlas spriteAtlas) {

        // call the other constructor but without custom hitbox
        this(name, position, size, new Vector2(0f, 0f), size, footstepFrequency, speed, spriteAtlas);
    }

    /**
     * Draws the player and torch if active
     *
     * @param batch sprite batch used to draw graphics
     */
    public void render(SpriteBatch batch, FitViewport viewport) {
        super.render(batch);
        if (active) {
            // draw torch centered on player
            Vector2 screenPosition = viewport.project(new Vector2(position.x + (size.x / 2f), position.y + (size.y / 2f)));
            torch.render(screenPosition.x, screenPosition.y, batch);
        }
    }

    /**
     * Updates movement, collision, sounds, and animations.
     *
     * @param delta_t time since last frame
     * @param environment the environment
     * @param buildingManager building manager
     * @param enemyManager enemy handler
     */
    public void update(float delta_t, Environment environment, BuildingManager buildingManager, EnemyManager enemyManager) {
        if (!active) return;

        velocity.set(0f, 0f); // reset velocity each frame

        // apply movement based on keys pressed
        if (movingLeft) velocity.x = -speed;
        if (movingRight) velocity.x = speed;
        if (movingUp) velocity.y = speed;
        if (movingDown) velocity.y = -speed;

        // X axis movement and collisions
        position.x += velocity.x * delta_t;
        hitbox.setXPos(position.x);

        // Make it so the player can't go out of frame
        if (position.x < 0 || position.x + size.x > Main.WORLD_SIZE.x) {
            velocity.x = 0;
            position.x = MathUtils.clamp(position.x, 0, Main.WORLD_SIZE.x - size.x);
        } else if (environment.checkCollision(hitbox) || buildingManager.checkCollision(hitbox) || enemyManager.checkCollision(hitbox)) {
            // undo the move if a collision has occured
            position.x -= velocity.x * delta_t;
            velocity.x = 0;
            hitbox.setXPos(position.x);
        }

        // Y axis movement and collisions
        position.y += velocity.y * delta_t;
        hitbox.setYPos(position.y);

        if (position.y < 0 || position.y + size.y > Main.WORLD_SIZE.y) {
            velocity.y = 0;
            position.y = MathUtils.clamp(position.y, 0, Main.WORLD_SIZE.y - size.y);
        } else if (environment.checkCollision(hitbox) || buildingManager.checkCollision(hitbox) || enemyManager.checkCollision(hitbox)) {
            // undo the move if a collision has occured
            position.y -= velocity.y * delta_t;
            velocity.y = 0;
            hitbox.setYPos(position.y);
        }

        updateSprite();
        updateSFX(delta_t);

        stateTime += delta_t; // for animation frame timing
    }

    /**
     * Handles sfx
     *
     * @param delta_t time since last frame
     */
    private void updateSFX(float delta_t) {

        // no sound when standing still
        if (velocity.isZero()) {
            footstepTimer = 0;
        } else {
            // if enough time passed, play a random footstep
            if (footstepTimer > footstepTimeout || footstepTimer == 0) {
                AudioPlayer.playSound("footstep" + MathUtils.random(1, 3), 0.5f, MathUtils.random(0.5f, 3f));
                footstepTimer = 0;
            }
            footstepTimer += delta_t;
        }
    }

    /**
     * Chooses which sprite animation to show based on movement direction
     */
    private void updateSprite() {

        if (velocity.x > 0) {
            setSprite("right", stateTime);
            currentDirection = Direction.RIGHT;
        } else if (velocity.x < 0) {
            setSprite("left", stateTime);
            currentDirection = Direction.LEFT;
        } else if (velocity.y > 0) {
            setSprite("back", stateTime);
            currentDirection = Direction.UP;
        } else if (velocity.y < 0) {
            setSprite("front", stateTime);
            currentDirection = Direction.DOWN;
        } else {
            // idle animation depends on which way we last moved
            switch (currentDirection) {
                case UP -> setSprite("idleback", stateTime);
                case DOWN -> setSprite("idlefront", stateTime);
                case LEFT -> setSprite("idleleft", stateTime);
                case RIGHT -> setSprite("idleright", stateTime);
            }
        }
    }

    /**
     * Adds an item to inventory
     *
     * @param itemName item name to add (must be the same as the sprite files name)
     */    public void addItem(String itemName) {
        if (inventory.size < Hotbar.NUM_SLOTS) {
            inventory.add(new Item(itemName));
        }
    }

    /**
     * Checks if player has a certain item
     *
     * @param itemName name to search for (must be the same as the sprite files name)
     * @return true if player has the item
     */    public boolean hasItem(String itemName) {
        for (Item item : inventory) {
            if (item.getName().equals(itemName)) return true;
        }
        return false;
    }

    /**
     * Removes a specific item if it exists (must be the same as the sprite files name)
     *
     * @param itemName name of item to remove
     */    public void removeItem(String itemName) {
        for (Item item : inventory) {
            if (item.getName().equals(itemName)) inventory.removeValue(item, false);
        }
    }

    // setters for movement flags
    public void setMovingUp(boolean movingUp) { this.movingUp = movingUp; }
    public void setMovingDown(boolean movingDown) { this.movingDown = movingDown; }
    public void setMovingLeft(boolean movingLeft) { this.movingLeft = movingLeft; }
    public void setMovingRight(boolean movingRight) { this.movingRight = movingRight; }

    /** @return players inverntory */
    public Array<Item> getInventory() { return inventory; }

    /** @return players torch effect */
    public Torch getTorch() { return torch; }

    /** stops all player movement*/
    public void stopMoving() {
        movingLeft = movingRight = movingUp = movingDown = false;
    }

    /** clean up memory */
    public void dispose() {
        spriteAtlas.dispose();
        torch.dispose();
    }
}
