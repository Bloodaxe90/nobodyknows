package io.github.game.entities.enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import io.github.game.Environment;
import io.github.game.entities.Entity;
import io.github.game.entities.player.Player;
import io.github.game.utils.interactions.Interaction;
import io.github.game.ui.UserIntereface;

public class Enemy extends Entity {

    private float range; // -1 for infinite range
    private final Interaction interaction;
    private boolean interact = false;


    public Enemy(String name, Interaction interaction, float xPos, float yPos, int width, int height, float range, float hitboxXOffset, float hitboxYOffset, int hitboxWidth, int hitboxHeight, float speed, TextureAtlas spriteAtlas) {
        super(name, xPos, yPos, width, height, hitboxXOffset, hitboxYOffset, hitboxWidth, hitboxHeight, speed, true);
        for (String str : new String[]{"front", "back", "left", "right"}) {
            addAnimation(name + "_" + str, 0.1f, Animation.PlayMode.LOOP, spriteAtlas);
        }
        for (String str : new String[]{"idlefront", "idleback", "idleleft", "idleright"}) {
            addAnimation(name + "_" + str, 1f, Animation.PlayMode.LOOP, spriteAtlas);
        }
        this.range = range;

        setSprite(name + "_front", stateTime);
        this.interaction = interaction;
    }

    public Enemy(String name, Interaction interaction, float xPos, float yPos, int width, int height, float range, float speed, TextureAtlas spriteAtlas) {
        this(name, interaction, xPos, yPos, width, height, range,0, 0, width, height, speed, spriteAtlas);
    }

    public void update(float delta_t, Environment environment, Player player, UserIntereface ui) {
        if (!active) {
            return;
        }

        float dirX = player.getXPos() - this.xPos;
        float dirY = player.getYPos() - this.yPos;

        float distanceToPlayer = (float) Math.sqrt(dirX * dirX + dirY * dirY);

        if (distanceToPlayer <= range * environment.getTileSize() || range < 0) {
            this.vx = (dirX / distanceToPlayer) * this.speed;
            this.vy = (dirY / distanceToPlayer) * this.speed;

        } else {
            this.vx = 0;
            this.vy = 0;
        }

        xPos += vx * delta_t;
        hitbox.setXPos(xPos);
        hitbox.setYPos(yPos);
        if (environment.checkCollision(hitbox)) {
            xPos -= vx * delta_t;
        }

        yPos += vy * delta_t;
        hitbox.setXPos(xPos);
        hitbox.setYPos(yPos);
        if (environment.checkCollision(hitbox)) {
            yPos -= vy * delta_t;
        }

        hitbox.setXPos(xPos);
        hitbox.setYPos(yPos);
        stateTime += delta_t;

        if (interact && interaction != null) {
            interaction.interact(this, player, ui);
            interact = false;
        }
    }

    public void setInteract(boolean interact) {
        this.interact = interact;
    }
}
