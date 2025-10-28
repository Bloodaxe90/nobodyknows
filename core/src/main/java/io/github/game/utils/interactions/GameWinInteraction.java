package io.github.game.utils.interactions;

import io.github.game.entities.Entity;
import io.github.game.entities.player.Player;
import io.github.game.ui.DialogueBox;
import io.github.game.ui.UserIntereface;
import io.github.game.utils.io.DialogueLoader;

public class GameWinInteraction implements Interaction {

    private String item;
    private int dialogueNoKeycardOption;
    private boolean destroy;
    private boolean event;
    private boolean firstInteraction = true;

    public GameWinInteraction(String item, int dialogueNoKeycardOption, boolean destroy, boolean event) {
        this.item = item;
        this.dialogueNoKeycardOption = dialogueNoKeycardOption;
        this.destroy = destroy;
        this.event = event;
    }

    @Override
    public void interact(Entity entity, Player player, UserIntereface ui) {
        DialogueBox dialogueBox = ui.getDialogueBox();
        player.stopMoving();
        if (player.hasItem(item)) {
            if (event && firstInteraction) {
                ui.getStatusBar().incrementEventCounter();
                firstInteraction = false;
            }
            ui.setupGameOverScreen("Win\nYou made it home in time");
        } else {
            dialogueBox.showDialogue(DialogueLoader.getBlock(entity.getName(), dialogueNoKeycardOption));
        }
        if (destroy) entity.setActive(false);
    }
}
