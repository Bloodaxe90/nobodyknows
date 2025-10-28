package io.github.game.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import io.github.game.utils.io.DialogueLoader;


public class PauseMenu extends Table {

    private Label pauseText;

    public PauseMenu(Skin skin) {
        super(skin);
        pauseText = new Label("PAUSED\n\n" + DialogueLoader.getDialogue("tutorial"), skin);
        pauseText.setAlignment(Align.center);

        this.add(pauseText).center();

    }

    public void setText(String text) {
        pauseText.setText(text);
    }

    public String getText() {
        return pauseText.getText().toString();
    }
}
