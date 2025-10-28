package io.github.game.utils.io;

import com.badlogic.gdx.Gdx;

public final class DialogueLoader {

    public static String getBlock(String filename, int optionNumber) {
        String text = Gdx.files.internal("dialogue/" + filename + ".txt").readString();

        String[] blocks = text.split("---");

        for (int i = 0; i < blocks.length; i++) {
            blocks[i] = blocks[i].trim();
        }

        return blocks[optionNumber];
    }

    public static String getDialogue(String filename) {
        return getBlock(filename, 0);
    }
}

