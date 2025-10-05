package io.github.game.ui;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class DialogueBox extends Table {

    private Label textLabel;
    private String text;
    private int visibleTextLength = 0;
    private float letterTime;
    private float textTimer = 0f;

    private boolean isFinished = false;

    public DialogueBox(float letterTime, Stage stage, Skin skin, TextureAtlas uiAtlas) {
        super();

        TextureRegion dialogBox = uiAtlas.findRegion("dialog");
        this.setBackground(new TextureRegionDrawable(dialogBox));
        this.setBounds(0, 0, stage.getWidth(), stage.getHeight() / 3);

        this.letterTime = letterTime;
        textLabel = new Label("", skin);
        textLabel.setWrap(true);
        textLabel.setAlignment(Align.topLeft);
        this.add(textLabel).expand().fill().pad(15);
    }

    public void startDialogue(String text) {
        this.text = text;
        this.visibleTextLength = 0;
        this.textTimer = 0f;
        this.isFinished = false;
        this.textLabel.setText("");
    }

    public void skip() {
        if (!isFinished) {
            visibleTextLength = text.length();
            textLabel.setText(text);
            isFinished = true;
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (isFinished || text == null) {
            return;
        }

        textTimer += delta;

        while (textTimer >= letterTime && visibleTextLength < text.length()) {
            visibleTextLength++;
            textTimer -= letterTime;
        }

        textLabel.setText(text.substring(0, visibleTextLength));

        if (visibleTextLength >= text.length()) {
            isFinished = true;
        }
    }

    public boolean isFinished() {
        return isFinished;
    }

    public float getLetterTime() {
        return letterTime;
    }

    public void setLetterTime(float letterTime) {
        this.letterTime = letterTime;
    }
}
