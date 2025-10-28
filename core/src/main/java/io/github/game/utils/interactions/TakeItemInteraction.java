package io.github.game.utils.interactions;

import io.github.game.entities.Entity;
import io.github.game.entities.player.Player;
import io.github.game.ui.DialogueBox;
import io.github.game.ui.UserIntereface;
import io.github.game.utils.io.DialogueLoader;

public class TakeItemInteraction implements Interaction {

    private String item;
    private int dialogueDefaultOption;
    private int dialogueTakeOption;
    private boolean destroy;
    private boolean event;
    private boolean firstInteraction = true;

    public TakeItemInteraction(String item, int dialogueDefaultOption, int dialogueTakeOption, boolean destroy, boolean event) {
        this.item = item;
        this.dialogueDefaultOption = dialogueDefaultOption;
        this.dialogueTakeOption = dialogueTakeOption;
        this.destroy = destroy;
        this.event = event;
    }

    @Override
    public void interact(Entity entity, Player player, UserIntereface ui) {
        DialogueBox dialogueBox = ui.getDialogueBox();
        player.stopMoving();
        if (player.hasItem(item)) {
            player.removeItem(item);
            dialogueBox.showDialogue(DialogueLoader.getBlock(entity.getName(), dialogueTakeOption));
            if (event && firstInteraction) {
                ui.getStatusBar().incrementEventCounter();
                firstInteraction = false;
            }
        } else {
            dialogueBox.showDialogue(DialogueLoader.getBlock(entity.getName(), dialogueDefaultOption));
        }
        if (destroy) entity.setActive(false);
    }
}
