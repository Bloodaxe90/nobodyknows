package io.github.game.entities.enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import io.github.game.Environment;
import io.github.game.entities.Entity;
import io.github.game.entities.player.Player;
import io.github.game.ui.UserIntereface;
import io.github.game.utils.interactions.Interaction;

public class Enemy extends Entity {

    private float range; // -1 for infinite range
    private final Interaction interaction;
    private boolean interact = false;


    public Enemy(String name, Interaction interaction, Vector2 position, Vector2 size, float range, Vector2 hitboxOffset, Vector2 hitboxSize, float speed, TextureAtlas spriteAtlas) {
        super(name, position, size, hitboxOffset, hitboxSize, speed, true);
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

    public Enemy(String name, Interaction interaction, Vector2 position, Vector2 size, float range, float speed, TextureAtlas spriteAtlas) {
        this(name, interaction, position, size, range, new Vector2(0f, 0f), size, speed, spriteAtlas);
    }

    public void update(float delta_t, Environment environment, Player player, UserIntereface ui) {
        if (!active) {
            return;
        }

        Vector2 dir = player.getPos().sub(this.position);

        float distanceToPlayer = (float) Math.sqrt(dir.x * dir.x + dir.y * dir.y);

        if (distanceToPlayer <= range * environment.getTileSize() || range < 0) {
            this.velocity.x = (dir.x / distanceToPlayer) * this.speed;
            this.velocity.y = (dir.y / distanceToPlayer) * this.speed;

        } else {
            this.velocity = new Vector2(0f, 0f);
        }

        position.x += velocity.x * delta_t;
        hitbox.setXPos(position.x);
        hitbox.setYPos(position.y);
        if (environment.checkCollision(hitbox)) {
            position.x -= velocity.x * delta_t;
        }

        position.y += velocity.y * delta_t;
        hitbox.setXPos(position.x);
        hitbox.setYPos(position.y);
        if (environment.checkCollision(hitbox)) {
            position.y -= velocity.y * delta_t;
        }

        hitbox.setXPos(position.x);
        hitbox.setYPos(position.y);
        stateTime += delta_t;
        //TODO make it so sprites represent the enemies movement properly like the players
        updateSprite(false);

        if (interact && interaction != null) {
            interaction.interact(this, player, ui);
            interact = false;
        }
    }

    private void updateSprite(boolean isIdle) {
        String prefix = name + (isIdle ? "_idle" : "_");
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

    public void setInteract(boolean interact) {
        this.interact = interact;
    }
}
