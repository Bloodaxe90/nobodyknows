package io.github.game.utils.interactions;

import io.github.game.entities.Entity;
import io.github.game.entities.player.Player;
import io.github.game.ui.DialogueBox;
import io.github.game.ui.UserIntereface;
import io.github.game.utils.io.DialogueLoader;

/**
 * Interaction that gives an item to the player when interacting with an entity and
 * shows different dialogues depending on whether the player already has the item or not
 */
public class GiveItemInteraction implements Interaction {

    private String item; // item to give to the player
    private int dialogueDefaultOption; // dialogue block if player already has the item
    private int dialogueGiveOption; // dialogue block if item is given
    private boolean destroy; // Should the entity be deactivated after interacting
    private boolean event; // Should the event counter be incremented
    private boolean firstInteraction = true; // flag for if this is the first interaction

    /**
     * Creates a GiveItemInteraction
     *
     * @param item name of the item to give to the player
     * @param dialogueDefaultOption dialogue block if player already has the item
     * @param dialogueGiveOption dialogue block if item is given
     * @param destroy whether to deactivate the entity after interacting
     * @param event whether to increment the event counter on first interaction
     */
    public GiveItemInteraction(String item, int dialogueDefaultOption, int dialogueGiveOption, boolean destroy, boolean event) {
        this.item = item;
        this.dialogueDefaultOption = dialogueDefaultOption;
        this.dialogueGiveOption = dialogueGiveOption;
        this.destroy = destroy;
        this.event = event;
    }

    /**
     * Executes the interaction when the player interacts with the entity
     * Stops the player, shows dialogue, optionally gives the item,
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

        if (!player.hasItem(item)) {
            // Player does not have the item give it and show give dialogue
            player.addItem(item);
            dialogueBox.showDialogue(DialogueLoader.getBlock(entity.getName(), dialogueGiveOption));

            if (event && firstInteraction) {
                ui.getStatusBar().incrementEventCounter();
                firstInteraction = false;
            }
        } else {
            // Player already has the item show default dialogue
            dialogueBox.showDialogue(DialogueLoader.getBlock(entity.getName(), dialogueDefaultOption));
        }

        if (destroy) entity.setActive(false); // deactivate entity if destroy is true
    }
}
