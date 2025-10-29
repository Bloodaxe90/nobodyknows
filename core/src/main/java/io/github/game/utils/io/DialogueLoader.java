package io.github.game.utils.io;

import com.badlogic.gdx.Gdx;

public final class DialogueLoader {

    public final static String PATH = "dialogue/";
    private final static String BLOCK_SEPERATOR = "---";

    public static String getBlock(String filename, int optionNumber) {
        String text = Gdx.files.internal(PATH + filename + ".txt").readString();

        // in each .txt file a blocks are seperated by "---"
        String[] blocks = text.split(BLOCK_SEPERATOR);

        for (int i = 0; i < blocks.length; i++) {
            blocks[i] = blocks[i].trim();
        }

        return blocks[optionNumber];
    }

    public static String getDialogue(String filename) {
        return getBlock(filename, 0);
    }
}

