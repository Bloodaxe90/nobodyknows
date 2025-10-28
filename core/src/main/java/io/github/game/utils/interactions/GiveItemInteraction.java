package io.github.game.utils.interactions;

import io.github.game.entities.Entity;
import io.github.game.entities.player.Player;
import io.github.game.ui.DialogueBox;
import io.github.game.ui.UserIntereface;
import io.github.game.utils.io.DialogueLoader;

public class GiveItemInteraction implements Interaction {

    private String item;
    private int dialogueDefaultOption;
    private int dialogueGiveOption;
    private boolean destroy;
    private boolean event;
    private boolean firstInteraction = true;

    public GiveItemInteraction(String item, int dialogueDefaultOption, int dialogueGiveOption, boolean destroy, boolean event) {
        this.item = item;
        this.dialogueDefaultOption = dialogueDefaultOption;
        this.dialogueGiveOption = dialogueGiveOption;
        this.destroy = destroy;
        this.event = event;
    }

    @Override
    public void interact(Entity entity, Player player, UserIntereface ui) {
        DialogueBox dialogueBox = ui.getDialogueBox();
        player.stopMoving();
        if (!player.hasItem(item)) {
            player.addItem(item);
            dialogueBox.showDialogue(DialogueLoader.getBlock(entity.getName(), dialogueGiveOption));
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
