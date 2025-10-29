package io.github.game.entities.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import io.github.game.Environment;
import io.github.game.Main;
import io.github.game.entities.Entity;
import io.github.game.entities.building.BuildingManager;
import io.github.game.entities.enemy.EnemyManager;
import io.github.game.ui.Item;
import io.github.game.utils.Torch;
import io.github.game.utils.io.AudioPlayer;
import io.github.game.ui.Hotbar;
import io.github.game.ui.Item;


public class Player extends Entity {

    private boolean movingUp, movingDown, movingLeft, movingRight;
    private final Array<Item> inventory;
    private final Torch torch;
    private final TextureAtlas spriteAtlas;
    private float footstepTimer = 0;
    private float footstepTimeout;

    public Player(String name,
                  float xPos, float yPos,
                  int width, int height,
                  float hitboxXOffset, float hitboxYOffset,
                  int hitboxWidth, int hitboxHeight,
                  float footsetTimeout,
                  float speed,
                  TextureAtlas spriteAtlas) {
        super(name, xPos, yPos, width, height, hitboxXOffset, hitboxYOffset, hitboxWidth, hitboxHeight, speed, true);

        this.spriteAtlas = spriteAtlas;
        this.inventory = new Array<>(Hotbar.NUM_SLOTS);
        this.footstepTimeout = footsetTimeout;
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
                  float xPos, float yPos,
                  int width, int height,
                  float footsetTimeout,
                  float speed,
                  TextureAtlas spriteAtlas) {
        this(name, xPos, yPos, width, height, 0f, 0f, width, height, footsetTimeout, speed, spriteAtlas);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        if (active) {
            torch.render(xPos + (width / 2f), yPos + (height / 2f), batch);
        }
    }

    public void update(float delta_t, Environment environment, BuildingManager buildingManager, EnemyManager enemyManager) {
        if (!active) {
            return;
        }

        vx = 0;
        vy = 0;

        if (movingLeft) vx = -speed;
        if (movingRight) vx = speed;
        if (movingUp) vy = speed;
        if (movingDown) vy = -speed;

        xPos += vx * delta_t;
        hitbox.setXPos(xPos);

        if (xPos < 0 || xPos + width > Main.WORLD_WIDTH) {
            vx = 0;
            xPos = MathUtils.clamp(xPos, 0, Main.WORLD_WIDTH - width);
        } else if (environment.checkCollision(hitbox) || buildingManager.checkCollision(hitbox) || enemyManager.checkCollision(hitbox)) {
            xPos -= vx * delta_t;
            updateSprite(true);
            vx = 0;
            hitbox.setXPos(xPos);
        }

        yPos += vy * delta_t;
        hitbox.setYPos(yPos);


        if (yPos < 0 || yPos + height > Main.WORLD_HEIGHT) {
            vy = 0;
            yPos = MathUtils.clamp(yPos, 0, Main.WORLD_HEIGHT - height);
        } else if (environment.checkCollision(hitbox) || buildingManager.checkCollision(hitbox) || enemyManager.checkCollision(hitbox)) {
            yPos -= vy * delta_t;
            updateSprite(true);
            vy = 0;
            hitbox.setYPos(yPos);
        }

        updateSprite(false);
        updateSFX(delta_t);
        stateTime += delta_t;


    }

    private void updateSFX(float delta_t) {
        if (vy == 0 && vx == 0) {
            footstepTimer = 0;
        } else {
            if (footstepTimer > footstepTimeout || footstepTimer == 0) {
                AudioPlayer.playSound("footstep" + MathUtils.random(1, 3), 0.5f, MathUtils.random(0.9f, 1.1f));
                footstepTimer = 0;
            }
            footstepTimer += delta_t;
        }
    }

    private void updateSprite(boolean isIdle) {
        String prefix = isIdle ? "idle" : "";

        if (vx > 0) {
            setSprite(prefix + "right", stateTime);
        } else if (vx < 0) {
            setSprite(prefix + "left", stateTime);
        } else if (vy > 0) {
            setSprite(prefix + "back", stateTime);
        } else if (vy < 0) {
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
