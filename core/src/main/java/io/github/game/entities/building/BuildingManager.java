package io.github.game.entities.building;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import io.github.game.entities.player.Player;
import io.github.game.utils.Hitbox;
import io.github.game.Main;
import io.github.game.utils.interactions.DialogueInteraction;
import io.github.game.ui.UserIntereface;
import io.github.game.utils.interactions.GameWinInteraction;
import io.github.game.utils.interactions.GiveItemInteraction;

public class BuildingManager {

    private final TextureAtlas spriteAtlas;
    private Array<Building> buildings = new Array<>();

    public BuildingManager(TextureAtlas spriteAtlas) {
        this.spriteAtlas = spriteAtlas;

        //TODO positions are messy and need to be changed
        int size = 96;
        buildings.add(new Building("greggs", new GiveItemInteraction("sausage_roll", 0, 1, false, true), (Main.WORLD_WIDTH / 2f) + 96, Main.WORLD_HEIGHT - size, size, size, spriteAtlas));
        buildings.add(new Building("pza", new DialogueInteraction(0, false, false), (Main.WORLD_WIDTH / 10f ) * 8.5f, (Main.WORLD_HEIGHT / 8f) * 5.5f, size, size, spriteAtlas));
        buildings.add(new Building("rch", new GiveItemInteraction("keycard", 0, 0, false, true), (Main.WORLD_WIDTH / 8f ) * 7, Main.WORLD_HEIGHT / 3f, size, size, spriteAtlas));
        buildings.add(new Building("halifax", new DialogueInteraction(0, false, false), Main.WORLD_WIDTH / 4f, Main.WORLD_HEIGHT - size, size, size, spriteAtlas));
        buildings.add(new Building("derwent", new DialogueInteraction(0, false, false), 48, Main.WORLD_HEIGHT - (size * 2), size, size, spriteAtlas));
        buildings.add(new Building("compsci", new DialogueInteraction(0, false, false), (Main.WORLD_WIDTH / 8f ) * 5, (Main.WORLD_HEIGHT / 8f) * 5, size, size, spriteAtlas));
        buildings.add(new Building("home", new GameWinInteraction("keycard", 0, false, true), 0, 0, size, size, spriteAtlas));
        buildings.add(new Building("central", new DialogueInteraction(0, false, false), (Main.WORLD_WIDTH / 2f ) - (size / 2f), (Main.WORLD_HEIGHT / 2f) - (size / 2f), size, size, spriteAtlas));
    }

    public void render(SpriteBatch batch) {
        if (buildings.notEmpty()) {
            for (Building building : buildings) {
                building.render(batch);
            }
        }
    }

    public void update(Player player, UserIntereface ui) {
        if (buildings.notEmpty()) {
            for (Building building : buildings) {
                building.update(player, ui);
                if (!building.isActive()) {
                    buildings.removeValue(building, false);
                }
            }
        }
    }

    public boolean checkCollision(Hitbox hitbox) {
        if (buildings.notEmpty()) {
            for (Building building : buildings) {
                if (building.getHitbox().collides(hitbox)) {
                    building.setInteract(true);
                    return true;
                }
            }
        }
        return false;
    }

    public void dispose() {
        spriteAtlas.dispose();
    }

}
