package io.github.game.ui;

// Make sure to import ScrollPane and its style
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

public class DialogueBox extends Table {

    private final Label textLabel;
    private String fullText;
    private int visibleTextLength = 0;
    private float letterTime;
    private float textTimer = 0;
    ArrayList<String> sounds;

    private boolean isFinished = false;

    private ScrollPane scrollPane;

    public DialogueBox(float letterTime, Skin skin, TextureAtlas uiAtlas) {
        super();

        TextureRegion backgroundRegion = uiAtlas.findRegion("dialog");
        this.setBackground(new TextureRegionDrawable(backgroundRegion));
        this.setBounds(0, 0, Main.WORLD_SIZE.x, Main.WORLD_SIZE.y / 3f);

        this.letterTime = letterTime;

        textLabel = new Label("", skin);
        textLabel.setWrap(true);
        textLabel.setAlignment(Align.topLeft);

        scrollPane = new ScrollPane(textLabel, skin);
        ScrollPane.ScrollPaneStyle style = new ScrollPane.ScrollPaneStyle(scrollPane.getStyle());
        style.background = null;
        scrollPane.setStyle(style);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);

        this.add(scrollPane).expand().fill().pad(30);
    }

    public void startDialogue(String text) {
        this.fullText = addSFX(text);
        this.visibleTextLength = 0;
        this.textTimer = 0f;
        this.isFinished = false;
        this.textLabel.setText("");
        scrollPane.setScrollY(0);
    }

    // Adds SFX to a list and replaces their placeholder with an invisible character
    public String addSFX(String text) {
        sounds = new ArrayList<>();
        // Regex to find curly brackets contents
        Pattern pattern = Pattern.compile("\\{(.*?)}");
        Matcher matcher = pattern.matcher(text);

        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            sounds.add(matcher.group(1));
            matcher.appendReplacement(result, "\u200B");
        }
        matcher.appendTail(result);

        return result.toString();

    }

    public void skip() {
        if (!isFinished) {
            visibleTextLength = fullText.length();
            textLabel.setText(fullText);
            isFinished = true;
            scrollPane.layout();
            scrollPane.setScrollPercentY(1f);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (isFinished || fullText == null) {
            return;
        }

        textTimer += delta;

        int oldLength = visibleTextLength;

        while (textTimer >= letterTime && visibleTextLength < fullText.length()) {
            visibleTextLength++;
            if (fullText.charAt(visibleTextLength - 1) == '.' || fullText.charAt(visibleTextLength - 1) == ':' || fullText.charAt(visibleTextLength - 1) == '?' || fullText.charAt(visibleTextLength - 1) == '!') {
                textTimer -= letterTime;
                textTimer -= 0.5f;
            }
            else if (fullText.charAt(visibleTextLength - 1) == ',' || fullText.charAt(visibleTextLength - 1) == ';') {
                textTimer -= letterTime;
                textTimer -= 0.2f;
            }
            else if (fullText.charAt(visibleTextLength - 1) == '\u200B') {
                AudioPlayer.playSound(sounds.get(0), 1f);
                sounds.remove(0);
            }
            textTimer -= letterTime;
            AudioPlayer.playSound("speak1", 0.5f, MathUtils.random(0.9f, 1.1f));
        }

        if (oldLength != visibleTextLength) {
            textLabel.setText(fullText.substring(0, visibleTextLength));
        }

        if (visibleTextLength >= fullText.length()) {
            isFinished = true;
        }


        if (!isFinished) {
            scrollPane.layout();
            scrollPane.setScrollPercentY(1.0f);
        }
    }

    public void showDialogue(String message) {
        startDialogue(message);
        setVisible(true);

        Stage stage = this.getStage();
        if (stage != null) {
            stage.setScrollFocus(scrollPane);
        }
    }

    public void hideDialogue() {
        setVisible(false);
        Stage stage = this.getStage();
        if (stage != null && stage.getScrollFocus() == scrollPane) {
            stage.setScrollFocus(null);
        }
    }

    public boolean isFinished() {
        return isFinished;
    }
}
