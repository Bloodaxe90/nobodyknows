package io.github.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import io.github.game.ui.Item;
import io.github.game.ui.Hotbar;


public class Player extends Entity {

    private boolean movingUp, movingDown, movingLeft, movingRight;
    private Array<Item> inventory;

    public Player(float xPos, float yPos,
                  int width, int height,
                  float hitboxXOffset, float hitboxYOffset,
                  int hitboxWidth, int hitboxHeight,
                  float speed,
                  TextureAtlas spriteAtlas) {
        super(xPos, yPos, width, height, hitboxXOffset, hitboxYOffset, hitboxWidth, hitboxHeight, speed, true, spriteAtlas);

        this.inventory = new Array<>(Hotbar.NUM_SLOTS);
        // Sets up the sprite movement map
        for (String name : new String[]{"front", "back", "left", "right"}) {
            addAnimation(name, 0.1f, Animation.PlayMode.LOOP);
        }
        for (String name : new String[]{"idlefront", "idleback", "idleleft", "idleright"}) {
            addAnimation(name, 1f, Animation.PlayMode.LOOP);
        }

        setSprite("front", stateTime);
    }

    public Player(float xPos, float yPos,
                  int width, int height,
                  float speed,
                  TextureAtlas spriteAtlas) {
        this(xPos, yPos, width, height, 0f, 0f, width, height, speed, spriteAtlas);
    }

    @Override
    public void render(SpriteBatch batch) {

        batch.draw(sprite, xPos, yPos, width, height);
    }

    public void update(float delta_t, Environment environment) {
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
        } else if (environment.checkCollision(this)) {
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
        } else if (environment.checkCollision(this)) {
            yPos -= vy * delta_t;
            updateSprite(true);
            vy = 0;
            hitbox.setYPos(yPos);
        }

        updateSprite(false);
        stateTime += delta_t;

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
}
