package io.github.game.utils.interactions;

import io.github.game.entities.Entity;
import io.github.game.entities.player.Player;
import io.github.game.ui.DialogueBox;
import io.github.game.ui.UserIntereface;
import io.github.game.utils.io.DialogueLoader;

/**
 * Interaction implementation that shows a dialogue when the player interacts with an entity
 */
public class DialogueInteraction implements Interaction {

    private int dialogueOption; // block of dialogue to show
    private boolean destroy; // Should the entity be deactivated after interacting
    private boolean event; // Should the event counter be incremented
    private boolean firstInteraction = true; // flag for if this is the first interaction

    /**
     * Creates a DialogueInteraction
     *
     * @param dialogueOption which dialogue block to show from the entitys dialogue file
     * @param destroy whether to deactivate the entity after interaction
     * @param event whether to increment the event counter on interaction
     */
    public DialogueInteraction(int dialogueOption, boolean destroy, boolean event) {
        this.dialogueOption = dialogueOption;
        this.destroy = destroy;
        this.event = event;
    }

    /**
     * Executes the interaction when the player interacts with the entity
     * Stops the player, shows dialogue, optionally triggers events counter increment
     * and can deactivate the entity
     *
     * @param entity the entity being interacted with
     * @param player the player interacting with the entity
     * @param ui the UI to show dialogue and status
     */
    @Override
    public void interact(Entity entity, Player player, UserIntereface ui) {
        DialogueBox dialogueBox = ui.getDialogueBox();
        player.stopMoving(); // stop player movement while dialogue is active

        if (!dialogueBox.isVisible()) {
            // Show dialogue for the entity
            dialogueBox.showDialogue(DialogueLoader.getBlock(entity.getName(), dialogueOption));

            // Increment event counter if this is the first time and event is triggered
            if (event && firstInteraction) {
                ui.getStatusBar().incrementEventCounter();
                firstInteraction = false;
            }
        }

        if (destroy) entity.setActive(false); // deactivates the entity if destroy is true
    }
}
