package io.github.game.utils.interactions;

import io.github.game.entities.Entity;
import io.github.game.entities.player.Player;
import io.github.game.ui.DialogueBox;
import io.github.game.ui.UserIntereface;
import io.github.game.utils.io.DialogueLoader;

/**
 * Interaction that removes an item from the player when interacting with an entity and
 * shows different dialogues depending on whether the player has the item or not
 */
public class TakeItemInteraction implements Interaction {

    private String item; // item to remove from the player
    private int dialogueDefaultOption; // dialogue block if player doesn't have the item
    private int dialogueTakeOption; // dialogue block if player has the item
    private boolean destroy; // Should the entity be deactivated after interacting
    private boolean event; // Should the event counter be incremented
    private boolean firstInteraction = true; // flag for if this is the first interaction

    /**
     * Creates a TakeItemInteraction
     *
     * @param item name of the item to take from the player
     * @param dialogueDefaultOption dialogue block if player doesn't have the item
     * @param dialogueTakeOption dialogue block if player has the item
     * @param destroy whether to deactivate the entity after interacting
     * @param event whether to increment the event counter on first interaction
     */
    public TakeItemInteraction(String item, int dialogueDefaultOption, int dialogueTakeOption, boolean destroy, boolean event) {
        this.item = item;
        this.dialogueDefaultOption = dialogueDefaultOption;
        this.dialogueTakeOption = dialogueTakeOption;
        this.destroy = destroy;
        this.event = event;
    }

    /**
     * Executes the interaction when the player interacts with the entity
     * Stops the player, shows dialogue, optionally removes the item,
     * triggers the event counter and can deactivate the entity
     *
     * @param entity the entity being interacted with
     * @param player the player interacting with the entity
     * @param ui the UI to show dialogue and status
     */
    @Override
    public void interact(Entity entity, Player player, UserIntereface ui) {
        DialogueBox dialogueBox = ui.getDialogueBox();
        player.stopMoving(); // stop player movement while interacting

        if (player.hasItem(item)) {
            // Player has the item remove it and show take dialogue
            player.removeItem(item);
            dialogueBox.showDialogue(DialogueLoader.getBlock(entity.getName(), dialogueTakeOption));

            if (event && firstInteraction) {
                ui.getStatusBar().incrementEventCounter();
                firstInteraction = false;
            }
        } else {
            // Player does not have the item show default dialogue
            dialogueBox.showDialogue(DialogueLoader.getBlock(entity.getName(), dialogueDefaultOption));
        }

        if (destroy) entity.setActive(false); // deactivate the entity if destroy is true
    }
}
