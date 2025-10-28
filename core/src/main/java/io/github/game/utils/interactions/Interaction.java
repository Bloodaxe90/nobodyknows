package io.github.game.utils.interactions;

import io.github.game.entities.Entity;
import io.github.game.entities.player.Player;
import io.github.game.ui.UserIntereface;

public interface Interaction {

    void interact(Entity entity, Player player, UserIntereface ui);
}
