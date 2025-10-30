package io.github.game.ui;

// Make sure to import ScrollPane and its style
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import io.github.game.Main;

public class DialogueBox extends Table {

    private final Label textLabel;
    private String fullText;
    private int visibleTextLength = 0;
    private float letterTime;
    private float textTimer = 0;

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
        this.fullText = text;
        this.visibleTextLength = 0;
        this.textTimer = 0f;
        this.isFinished = false;
        this.textLabel.setText("");
        scrollPane.setScrollY(0);
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
            textTimer -= letterTime;
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
