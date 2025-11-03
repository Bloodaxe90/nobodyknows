package io.github.game.utils.io;

import com.badlogic.gdx.Gdx;

/**
 * Loads dialogue text files from the dialogue folder
 * Each file can have multiple "blocks" of dialogue with each separated by "---"
 * This class essentially just returns the block of text as a string
 */
public final class DialogueLoader {

    // Folder where dialogue text files are stored
    public final static String PATH = "dialogue/";

    // Used inside the .txt files to split different dialogue blocks
    private final static String BLOCK_SEPERATOR = "---";

    /**
     * Reads from text file and returns a section of dialogue
     *
     * @param filename name of the .txt file
     * @param optionNumber which block to get (0 = first, 1 = second...)
     * @return the chosen block of dialogue text
     */
    public static String getBlock(String filename, int optionNumber) {
        if (!filename.endsWith(".txt")) filename += ".txt";
        // Read the entire .txt file as one big string
        String text = Gdx.files.internal(PATH + filename).readString();

        // Splits the text into chunks about the BLOCK_SEPERATOR
        String[] blocks = text.split(BLOCK_SEPERATOR);

        // Cleans up text
        for (int i = 0; i < blocks.length; i++) {
            blocks[i] = blocks[i].trim();
        }

        // Return the chosen dialogue block
        return blocks[optionNumber];
    }

    /**
     * Shortcut to get the first block of dialogue.
     *
     * @param filename name of the .txt file
     * @return the first dialogue block
     */
    public static String getDialogue(String filename) {
        return getBlock(filename, 0);
    }
}
