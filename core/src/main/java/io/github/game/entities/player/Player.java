package io.github.game.entities.player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import io.github.game.Environment;
import io.github.game.Main;
import io.github.game.entities.Entity;
import io.github.game.entities.building.BuildingManager;
import io.github.game.entities.enemy.EnemyManager;
import io.github.game.ui.Hotbar;
import io.github.game.ui.Item;
import io.github.game.utils.Torch;
import io.github.game.utils.io.AudioPlayer;


public class Player extends Entity {

    private boolean movingUp, movingDown, movingLeft, movingRight;
    private final Array<Item> inventory;
    private final Torch torch;
    private final TextureAtlas spriteAtlas;
    private float footstepTimer = 0;
    private float footstepTimeout;

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
        // Sets up the sprite movement map
        for (String str : new String[]{"front", "back", "left", "right"}) {
            addAnimation(str, 0.1f, Animation.PlayMode.LOOP, spriteAtlas);
        }
        for (String str : new String[]{"idlefront", "idleback", "idleleft", "idleright"}) {
            addAnimation(str, 1f, Animation.PlayMode.LOOP, spriteAtlas);
        }

        this.torch = new Torch();
        addItem("keycard");
        setSprite("front", stateTime);
    }

    public Player(String name,
                  Vector2 position,
                  Vector2 size,
                  float footstepFrequency,
                  float speed,
                  TextureAtlas spriteAtlas) {
        this(name, position, size, new Vector2(0f, 0f), size, footstepFrequency, speed, spriteAtlas);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        if (active) {
            torch.render(position.x + (size.x / 2f), position.y + (size.y / 2f), batch);
        }
    }

    public void update(float delta_t, Environment environment, BuildingManager buildingManager, EnemyManager enemyManager) {
        if (!active) {
            return;
        }

        velocity.set(0f, 0f);

        if (movingLeft) velocity.x = -speed;
        if (movingRight) velocity.x = speed;
        if (movingUp) velocity.y = speed;
        if (movingDown) velocity.y = -speed;

        position.x += velocity.x * delta_t;
        hitbox.setXPos(position.x);

        if (position.x < 0 || position.x + size.x > Main.WORLD_SIZE.x) {
            velocity.x = 0;
            position.x = MathUtils.clamp(position.x, 0, Main.WORLD_SIZE.x - size.x);
        } else if (environment.checkCollision(hitbox) || buildingManager.checkCollision(hitbox) || enemyManager.checkCollision(hitbox)) {
            position.x -= velocity.x * delta_t;
            updateSprite(true);
            velocity.x = 0;
            hitbox.setXPos(position.x);
        }

        position.y += velocity.y * delta_t;
        hitbox.setYPos(position.y);


        if (position.y < 0 || position.y + size.y > Main.WORLD_SIZE.y) {
            velocity.y = 0;
            position.y = MathUtils.clamp(position.y, 0, Main.WORLD_SIZE.y - size.y);
        } else if (environment.checkCollision(hitbox) || buildingManager.checkCollision(hitbox) || enemyManager.checkCollision(hitbox)) {
            position.y -= velocity.y * delta_t;
            updateSprite(true);
            velocity.y = 0;
            hitbox.setYPos(position.y);
        }

        updateSprite(false);
        updateSFX(delta_t);
        stateTime += delta_t;


    }

    private void updateSFX(float delta_t) {
        if (velocity.isZero()) {
            footstepTimer = 0;
        } else {
            if (footstepTimer > footstepTimeout || footstepTimer == 0) {
                AudioPlayer.playSound("footstep" + MathUtils.random(1, 3), 0.5f);
                footstepTimer = 0;
            }
            footstepTimer += delta_t;
        }
    }

    private void updateSprite(boolean isIdle) {
        String prefix = isIdle ? "idle" : "";

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

    public void addItem(String itemName) {
        if (inventory.size < Hotbar.NUM_SLOTS) {
            inventory.add(new Item(itemName));
        }
    }

    public boolean hasItem(String itemName) {
        for (Item item : inventory) {
            if (item.getName().equals(itemName)) {
                return true;
            }
        }
        return false;
    }

    public void removeItem(String itemName) {
        for (Item item : inventory) {
            if (item.getName().equals(itemName)) inventory.removeValue(item, false);
        }
    }

    public void setMovingUp(boolean movingUp) {
        this.movingUp = movingUp;
    }

    public void setMovingDown(boolean movingDown) {
        this.movingDown = movingDown;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public Array<Item> getInventory() {
        return inventory;
    }

    public Torch getTorch() {
        return torch;
    }

    public void stopMoving() {
        movingLeft = false;
        movingRight = false;
        movingUp = false;
        movingDown = false;
    }


    public void dispose() {
        spriteAtlas.dispose();
        torch.dispose();
    }

}
