package io.github.game.entities.building;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import io.github.game.entities.Entity;
import io.github.game.entities.player.Player;
import io.github.game.ui.UserIntereface;
import io.github.game.utils.interactions.Interaction;

public class Building extends Entity {

    private final Interaction interaction;
    private boolean interact = false;

    public Building(String name, Interaction interaction, Vector2 position, Vector2 size, Vector2 hitboxOffset, Vector2 hitboxSize, TextureAtlas spriteAtlas) {
        super(name, position, size, hitboxOffset, hitboxSize, 0, true);
        addAnimation(name, 1f, Animation.PlayMode.LOOP, spriteAtlas);
        setSprite(name, 0);
        this.interaction = interaction;
    }

    public Building(String name,Interaction interaction, Vector2 position, Vector2 size, TextureAtlas spriteAtlas) {
        this(name, interaction, position, size, new Vector2(0, 0), size, spriteAtlas);
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
