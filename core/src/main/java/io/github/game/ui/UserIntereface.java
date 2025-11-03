package io.github.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.game.entities.player.Player;

/**
 * Manages all UI elements
 *
 * Contains the dialogue box, hotbar, pause menu and status bar
 */
public class UserIntereface {

    private final Stage stage; // The stage where all UI elements are
    private final Skin skin; // Skin for styling UI elements
    private final DialogueBox dialogueBox; // displays dialogues
    private final Hotbar hotbar; // Player hotbar
    private final PauseMenu pauseMenu; // Pause menu overlay
    private final StatusBar statusBar; // Status info like events and time
    private final TextureAtlas uiAtlas; // sprite sheet for UI elements

    /**
     * Creates a UI interface
     *
     * @param uiAtlas sprite for UI elements
     * @param uiViewport viewport for UI stage
     */
    public UserIntereface(TextureAtlas uiAtlas, Viewport uiViewport) {
        this.stage = new Stage(uiViewport);

        this.uiAtlas = uiAtlas;
        this.skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Setup dialogue box
        this.dialogueBox = new DialogueBox(0.05f, this.skin, uiAtlas);
        this.dialogueBox.setVisible(false);
        this.stage.addActor(this.dialogueBox);

        // Setup pause menu
        this.pauseMenu = new PauseMenu(skin);
        this.pauseMenu.setFillParent(true);
        this.pauseMenu.setVisible(false);
        this.stage.addActor(pauseMenu);

        // Setup hotbar
        this.hotbar = new Hotbar(uiAtlas);
        this.stage.addActor(this.hotbar);

        // Setup status bar
        this.statusBar = new StatusBar(skin);
        this.statusBar.setFillParent(true);
        this.stage.addActor(statusBar);

        // Input handling
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(this.stage);
        Gdx.input.setInputProcessor(multiplexer);
    }

    /**
     * Updates all UI elements
     *
     * @param delta time elapsed since last frame
     * @param playing whether the game is currently running
     * @param player the player
     */
    public void update(float delta, boolean playing, Player player) {
        if (playing) {
            stage.act(delta); // update all actors
            hotbar.updateInventory(player.getInventory()); // refresh inventory icons
            statusBar.update(delta); // update time/events
        }

        // Check if time ran out and if so change pause manu to game over loss text
        if (statusBar.isTimeUp()) {
            playing = false;
            setupGameOverScreen("Loss\nYou timed out");
        }

        pauseMenu.setVisible(!playing); // show pause menu if game paused
    }

    /**
     * Sets up the game over screen
     *
     * @param text message describing game over reason
     */
    public void setupGameOverScreen(String text) {
        statusBar.update(0);
        pauseMenu.setText("Game Over: " + text + "\n\n" + statusBar.getStatusText());
        statusBar.setVisible(false);
    }

    /** Renders the UI stage */
    public void render() {
        stage.draw();
    }

    /** Clean up for ui elements */
    public void dispose() {
        stage.dispose();
        skin.dispose();
        uiAtlas.dispose();
    }

    /** @return Gets the status bar */
    public StatusBar getStatusBar() {
        return statusBar;
    }

    /** @return Gets the stage */
    public Stage getStage() {
        return stage;
    }

    /** @return Gets the dialogue box */
    public DialogueBox getDialogueBox() {
        return dialogueBox;
    }

    /** @return Gets the pause menu */
    public PauseMenu getPauseMenu() {
        return pauseMenu;
    }

    /** @return Gets the UI texture atlas */
    public TextureAtlas getUiAtlas() {
        return uiAtlas;
    }
}
