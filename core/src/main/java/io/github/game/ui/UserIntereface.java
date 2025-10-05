package io.github.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.game.Player;

public class UserIntereface {

    private Stage stage;
    private Skin skin;

    private DialogueBox dialogueBox;
    private PauseMenu pauseMenu;

    private TextureAtlas uiAtlas;

    public UserIntereface(float dialogLetterTime, TextureAtlas uiAtlas, Viewport uiViewport) {
        this.stage = new Stage(uiViewport);

        this.uiAtlas = uiAtlas;
        this.skin = new Skin(Gdx.files.internal("uiskin.json"));

        this.dialogueBox = new DialogueBox(dialogLetterTime, stage, this.skin, uiAtlas);
        this.dialogueBox.setVisible(false);
        this.stage.addActor(this.dialogueBox);

        pauseMenu = new PauseMenu(skin);
        pauseMenu.setFillParent(true);
        pauseMenu.setVisible(false);
        stage.addActor(pauseMenu);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(this.stage);
        Gdx.input.setInputProcessor(multiplexer);
    }

    public void update(float delta, boolean playing) {
        stage.act(delta);
        pauseMenu.setVisible(!playing);
    }

    public void render() {
        stage.draw();
    }

    public void showDialogue(String message) {
        dialogueBox.startDialogue(message);
        dialogueBox.setVisible(true);
    }

    public void hideDialogue() {
        dialogueBox.setVisible(false);
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
        uiAtlas.dispose();
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
