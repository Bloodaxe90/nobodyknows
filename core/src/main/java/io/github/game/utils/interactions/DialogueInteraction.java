package io.github.game.utils.interactions;

import io.github.game.entities.Entity;
import io.github.game.entities.player.Player;
import io.github.game.ui.DialogueBox;
import io.github.game.ui.UserIntereface;
import io.github.game.utils.io.DialogueLoader;

public class DialogueInteraction implements Interaction{

    private int dialogueOption;
    private boolean destroy;
    private boolean event;
    private boolean firstInteraction = true;

    public DialogueInteraction(int dialogueOption, boolean destroy, boolean event) {
        this.dialogueOption = dialogueOption;
        this.destroy = destroy;
        this.event = event;
    }

    @Override
    public void interact(Entity entity, Player player, UserIntereface ui) {
        DialogueBox dialogueBox = ui.getDialogueBox();
        player.stopMoving();
        if (!dialogueBox.isVisible()) {
            dialogueBox.showDialogue(DialogueLoader.getBlock(entity.getName(), dialogueOption));
            if (event && firstInteraction) {
                ui.getStatusBar().incrementEventCounter();
                firstInteraction = false;
            }
        }
        if (destroy) entity.setActive(false);
    }
}
