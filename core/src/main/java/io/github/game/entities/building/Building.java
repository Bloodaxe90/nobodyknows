package io.github.game.entities.building;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import io.github.game.entities.Entity;
import io.github.game.entities.player.Player;
import io.github.game.utils.interactions.Interaction;
import io.github.game.ui.UserIntereface;

public class Building extends Entity {

    private final Interaction interaction;
    private boolean interact = false;

    public Building(String name, Interaction interaction, float xPos, float yPos, int width, int height, float hitboxXOffset, float hitboxYOffset, int hitboxWidth, int hitboxHeight, TextureAtlas spriteAtlas) {
        super(name, xPos, yPos, width, height, hitboxXOffset, hitboxYOffset, hitboxWidth, hitboxHeight, 0, true);
        addAnimation(name, 1f, Animation.PlayMode.LOOP, spriteAtlas);
        setSprite(name, 0);
        this.interaction = interaction;
    }

    public Building(String name,Interaction interaction, float xPos, float yPos, int width, int height, TextureAtlas spriteAtlas) {
        this(name, interaction, xPos, yPos, width, height, 0, 0, width, height, spriteAtlas);
    }

    public void update(Player player, UserIntereface ui) {
        if (!active) {
            return;
        }
        if (interact && interaction != null) {
            interaction.interact(this, player, ui);
            interact = false;
        }
    }

    public boolean isInteract() {
        return interact;
    }

    public void setInteract(boolean interact) {
        this.interact = interact;
    }

}
