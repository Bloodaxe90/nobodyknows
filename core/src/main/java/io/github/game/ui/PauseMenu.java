package io.github.game.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;


public class PauseMenu extends Table {

    private Label pauseText;

    public PauseMenu(Skin skin) {
        super(skin);

        pauseText = new Label("PAUSED", skin);
        this.add(pauseText).expandX().center();
    }
}
