package io.github.game.utils.interactions;

import io.github.game.entities.Entity;
import io.github.game.entities.player.Player;
import io.github.game.ui.UserIntereface;

/**
 * Functional Interface for custom interactions between game entities and the player
 * Classes implementing this define what happens when a player interacts with an entity
 */
public interface Interaction {

    /**
     * Called when the player interacts with an entity
     *
     * @param entity the entity being interacted with
     * @param player the player interacting with the entity
     * @param ui the UI for updating UI if needed
     */
    void interact(Entity entity, Player player, UserIntereface ui); // defines behavior on interaction
}
