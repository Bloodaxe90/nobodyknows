package io.github.game.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import io.github.game.utils.io.DialogueLoader;

/**
 * pause menu UI that shows pause text and optional tutorial text
 */
public class PauseMenu extends Table {

    private Label pauseText; // Label that displays the pause text


    /**
     * Creates a pause menu with default text "PAUSED" + any tutorial text
     *
     * @param skin UI skin used for styling the label
     */
    public PauseMenu(Skin skin) {
        super(skin);

        // Default pause text aligned at the center
        pauseText = new Label("PAUSED\n\n" + DialogueLoader.getDialogue("tutorial"), skin);
        pauseText.setAlignment(Align.center);

        // Add label to table and center it
        this.add(pauseText).center();
    }

    /**
     * Sets the text shown in the pause menu
     *
     * @param text new text to display
     */
    public void setText(String text) {
        pauseText.setText(text);
    }

    /**
     * Gets the current text displayed in the pause menu
     *
     * @return current pause menu text
     */
    public String getText() {
        return pauseText.getText().toString();
    }
}
