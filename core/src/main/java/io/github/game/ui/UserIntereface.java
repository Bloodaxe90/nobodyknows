package io.github.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.game.Main;
import io.github.game.entities.player.Player;

public class UserIntereface {

    private final Stage stage;
    private final Skin skin;
    private final DialogueBox dialogueBox;
    private final Hotbar hotbar;
    private final PauseMenu pauseMenu;
    private final StatusBar statusBar;
    private final TextureAtlas uiAtlas;

    public UserIntereface(float dialogLetterTime, TextureAtlas uiAtlas, Viewport uiViewport) {
        this.stage = new Stage(uiViewport);

        this.uiAtlas = uiAtlas;
        this.skin = new Skin(Gdx.files.internal("uiskin.json"));

        this.dialogueBox = new DialogueBox(dialogLetterTime, this.skin, uiAtlas);
        this.dialogueBox.setVisible(false);
        this.stage.addActor(this.dialogueBox);

        this.pauseMenu = new PauseMenu(skin);
        this.pauseMenu.setFillParent(true);
        this.pauseMenu.setVisible(false);
        this.stage.addActor(pauseMenu);

        this.hotbar = new Hotbar(uiAtlas);
        this.stage.addActor(this.hotbar);

        this.statusBar = new StatusBar(skin);
        this.statusBar.setFillParent(true);
        this.stage.addActor(statusBar);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(this.stage);
        Gdx.input.setInputProcessor(multiplexer);
    }

    public void update(float delta, boolean playing, Player player) {
        if (playing) {
            stage.act(delta);
            hotbar.updateInventory(player.getInventory());
            statusBar.update(delta);
        }
        if (statusBar.isTimeUp()) {
            setupGameOverScreen("Loss\nYou timed out");
        }
        pauseMenu.setVisible(!playing);
    }

    public void setupGameOverScreen(String text) {
        // Game over text must start with Game over
        statusBar.update(0);
        pauseMenu.setText("Game Over: " + text + "\n\n" + statusBar.getStatusText());
        statusBar.setVisible(false);
    }

    public void render() {
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
        uiAtlas.dispose();
    }

    public StatusBar getStatusBar() {
        return statusBar;
    }

    public Stage getStage() {
        return stage;
    }

    public DialogueBox getDialogueBox() {
        return dialogueBox;
    }

    public PauseMenu getPauseMenu() {
        return pauseMenu;
    }

    public TextureAtlas getUiAtlas() {
        return uiAtlas;
    }
}
