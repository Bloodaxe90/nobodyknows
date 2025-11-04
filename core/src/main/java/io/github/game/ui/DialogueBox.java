package io.github.game.ui;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import io.github.game.Main;
import io.github.game.utils.io.AudioPlayer;

/**
 * UI element for displaying dialogue
 *
 * Supports sfx embedded inside dialogue text
 */
public class DialogueBox extends Table {

    private final Label textLabel; // Label that for dialogue text

    private String fullText; // all dialogue text to be displayed

    private int visibleTextLength = 0; // Current length of dialogue visible on screen

    private float letterTime; // Delay between each letter appearing

    private float textTimer = 0; //Timer to track text reveal speed

    ArrayList<String> sounds; // Queue of sound effects embedded in text

    private boolean isFinished = false; // Whether the dialogue has all been displayed

    private ScrollPane scrollPane; // Scroll container for long dialogue


    /**
     * Creates the dialogue box UI
     *
     * @param letterTime time (seconds) between each character being revealed
     * @param skin UI skin for fonts/styles
     * @param uiAtlas sprite sheet for dialogue box
     */
    public DialogueBox(float letterTime, Skin skin, TextureAtlas uiAtlas) {
        super();

        // Set background image from atlas
        TextureRegion backgroundRegion = uiAtlas.findRegion("dialog");
        this.setBackground(new TextureRegionDrawable(backgroundRegion));

        // Set position at bottom third of screen
        this.setBounds(0, 0, Main.WORLD_SIZE.x, Main.WORLD_SIZE.y / 3f);

        this.letterTime = letterTime;

        // Label to display dialogue
        textLabel = new Label("", skin);
        textLabel.setWrap(true);
        textLabel.setAlignment(Align.topLeft);

        // ScrollPane for long dialogue
        scrollPane = new ScrollPane(textLabel, skin);
        ScrollPane.ScrollPaneStyle style = new ScrollPane.ScrollPaneStyle(scrollPane.getStyle());
        style.background = null; // remove background
        scrollPane.setStyle(style);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);

        this.add(scrollPane).expand().fill().pad(30);
    }

    /**
     * Starts a dialogue sequence
     *
     * @param text dialogue text to display
     */
    public void startDialogue(String text) {
        this.fullText = addSFX(text);
        this.visibleTextLength = 0;
        this.textTimer = 0f;
        this.isFinished = false;
        this.textLabel.setText("");
        scrollPane.setScrollY(0);
    }

    /**
     * Extracts SFX placeholders (i.e. {soundName}) and replaces them with
     * invisible trigger characters while storing sound names
     *
     * @param text dialogue text that may include embedded SFX
     * @return dialogue text with invisible SFX markers
     */
    public String addSFX(String text) {
        sounds = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\{(.*?)}");
        Matcher matcher = pattern.matcher(text);

        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            sounds.add(matcher.group(1));
            matcher.appendReplacement(result, "\u200B"); // invisible marker
        }
        matcher.appendTail(result);
        return result.toString();
    }

    /**
     * Reveals all dialogue
     */
    public void skip() {
        if (!isFinished) {
            visibleTextLength = fullText.length();
            textLabel.setText(fullText);
            isFinished = true;
            scrollPane.layout();
            scrollPane.setScrollPercentY(1f);
        }
    }

    /**
     * Updates the typing animation and triggers SFX timing
     *
     * @param delta time since last frame
     */
    @Override
    public void act(float delta) {
        super.act(delta);

        if (isFinished || fullText == null) return;

        textTimer += delta;
        int oldLength = visibleTextLength;

        // Reveal characters based on timer
        while (textTimer >= letterTime && visibleTextLength < fullText.length()) {
            visibleTextLength++;
            // Handles punctuation and SFX.
            char thisChar = fullText.charAt(visibleTextLength - 1);
            // Add pauses for punctuation
            switch (thisChar) {
                case '.':
                case ':':
                case '?':
                case '!':
                    textTimer -= letterTime;
                    textTimer -= 0.5f;
                    break;
                case ',':
                case ';':
                    textTimer -= letterTime;
                    textTimer -= 0.2f;
                    break;
                // Invisible SFX trigger
                case '\u200B':
                    AudioPlayer.playSound(sounds.get(0), 1f);
                    sounds.remove(0);
                default:
                    break;
            }

            textTimer -= letterTime;
            AudioPlayer.playSound("speak1", 1f, MathUtils.random(2f, 3f));
        }

        // Update visible text
        if (oldLength != visibleTextLength) {
            textLabel.setText(fullText.substring(0, visibleTextLength));
        }

        // Finished typing
        if (visibleTextLength >= fullText.length()) {
            isFinished = true;
        }

        // Scroll as text outgrows dialogue box size
        if (!isFinished) {
            scrollPane.layout();
            scrollPane.setScrollPercentY(1.0f);
        }
    }

    /**
     * Shows dialogue
     *
     * @param message dialogue to display
     */
    public void showDialogue(String message) {
        startDialogue(message);
        setVisible(true);

        Stage stage = this.getStage();
        if (stage != null) stage.setScrollFocus(scrollPane);
    }

    /**
     * Hides dialogue box and removes input foxue from scrollpane
     */
    public void hideDialogue() {
        setVisible(false);
        Stage stage = this.getStage();
        if (stage != null && stage.getScrollFocus() == scrollPane) {
            stage.setScrollFocus(null);
        }
    }

    /**
     * @return true if dialogue text has finished typing
     */
    public boolean isFinished() {
        return isFinished;
    }
}
