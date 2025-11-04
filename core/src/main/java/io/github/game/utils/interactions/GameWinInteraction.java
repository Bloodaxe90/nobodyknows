package io.github.game.utils.interactions;

import io.github.game.entities.Entity;
import io.github.game.entities.player.Player;
import io.github.game.ui.DialogueBox;
import io.github.game.ui.UserIntereface;
import io.github.game.utils.io.DialogueLoader;

/**
 * Interaction that ends the game with a win if the player has a specific item and
 * shows different dialogues depending on whether the player has the required item
 */
public class GameWinInteraction implements Interaction {

    private String item; // item required to win the game
    private int dialogueNoKeycardOption; // dialogue block if player does not have the required item
    private boolean destroy; // Should the entity be deactivated after interacting
    private boolean event; // Should the event counter be incremented
    private boolean firstInteraction = true; // flag for if this is the first interaction

    /**
     * Creates a GameWinInteraction
     *
     * @param item the item required for the player to win
     * @param dialogueNoKeycardOption dialogue block if player does not have the required item
     * @param destroy whether to deactivate the entity after interacting
     * @param event whether to increment the event counter on first interaction
     */
    public GameWinInteraction(String item, int dialogueNoKeycardOption, boolean destroy, boolean event) {
        this.item = item;
        this.dialogueNoKeycardOption = dialogueNoKeycardOption;
        this.destroy = destroy;
        this.event = event;
    }

    /**
     * Executes the interaction when the player interacts with the entity
     * Stops the player, shows appropriate dialogue, triggers game win if the player
     * has the required item, optionally increments the event counter, and can deactivate the entity
     *
     * @param entity the entity being interacted with
     * @param player the player interacting with the entity
     * @param ui the UI to show dialogue, status, and trigger a game win
     */
    @Override
    public void interact(Entity entity, Player player, UserIntereface ui) {
        DialogueBox dialogueBox = ui.getDialogueBox();
        player.stopMoving(); // stop player movement while interacting

        if (player.hasItem(item)) {
            // Player has the required item trigger game win
            if (event && firstInteraction) {
                ui.getStatusBar().incrementEventCounter();
                firstInteraction = false;
            }
            ui.setupGameOverScreen("Win\nYou made it home in time");
        } else {
            // Player does not have the item show dialogue indicating missing item
            dialogueBox.showDialogue(DialogueLoader.getBlock(entity.getName(), dialogueNoKeycardOption));
        }

        if (destroy) entity.setActive(false); // deactivate entity if destroy is true
    }
}
