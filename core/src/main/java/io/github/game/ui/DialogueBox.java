package io.github.game.ui;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import io.github.game.Main;

public class DialogueBox extends Table {

    private Label dialogueBox;
    private String text;
    private int visibleTextLength = 0;
    private float letterTime;
    private float textTimer = 0f;

    private boolean isFinished = false;

    public DialogueBox(float letterTime, Skin skin, TextureAtlas uiAtlas) {
        super();

        TextureRegion dialogBox = uiAtlas.findRegion("dialog");
        this.setBackground(new TextureRegionDrawable(dialogBox));
        this.setBounds(0, 0, Main.WORLD_WIDTH, Main.WORLD_HEIGHT / 3f);

        this.letterTime = letterTime;
        dialogueBox = new Label("", skin);
        dialogueBox.setWrap(true);
        dialogueBox.setAlignment(Align.topLeft);
        this.add(dialogueBox).expand().fill().pad(15);
    }

    public void startDialogue(String text) {
        this.text = text;
        this.visibleTextLength = 0;
        this.textTimer = 0f;
        this.isFinished = false;
        this.dialogueBox.setText("");
    }

    public void skip() {
        if (!isFinished) {
            visibleTextLength = text.length();
            dialogueBox.setText(text);
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

        dialogueBox.setText(text.substring(0, visibleTextLength));

        if (visibleTextLength >= text.length()) {
            isFinished = true;
        }
    }

    public void showDialogue(String message) {
        startDialogue(message);
        setVisible(true);
    }

    public void hideDialogue() {
        setVisible(false);
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
