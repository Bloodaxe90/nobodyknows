package io.github.game.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

/**
 * Displays the players game time and events completed
 */
public class StatusBar extends Table {

    private Label status; // Label that displays the status text

    private int eventsCompleted = 0; // How many events the player has completed so far

    public static final int MAX_EVENTS = 6; // Maximum number of events in the game

    private float timeRemaining = 300f; // Games play time


    /**
     * Creates a status bar UI element
     *
     * @param skin UI skin used for styling the label
     */
    public StatusBar(Skin skin) {
        super(skin);

        this.top().right();

        status = new Label("", skin);
        updateStatusText(); // initialize text

        this.add(status).align(Align.right);
    }

    /**
     * Updates the status bar timer and event text
     *
     * @param delta time passed since last frame
     */
    public void update(float delta) {
        // decrease timer if time is not up
        if (!isTimeUp()) {
            timeRemaining -= delta;
        } else {
            timeRemaining = 0;
        }

        updateStatusText(); // refresh displayed text
    }

    /** Updates the label text*/
    private void updateStatusText() {
        int minutes = (int) (timeRemaining / 60);
        int seconds = (int) (timeRemaining % 60);
        String formattedTime = String.format("%d:%02d", minutes, seconds);

        status.setText("Events: " + eventsCompleted + "/" + MAX_EVENTS + "\nTime: " + formattedTime);
    }

    /** Increments the counter of completed events up to the max */
    public void incrementEventCounter() {
        if (eventsCompleted < MAX_EVENTS) {
            eventsCompleted++;
        }
    }

    /**
     * Gets the text currently displayed in the status bar
     *
     * @return status text
     */
    public String getStatusText() {
        return status.getText().toString();
    }

    /**
     * Checks if the timer has run out
     *
     * @return true if timer ran out, false otherwise
     */
    public boolean isTimeUp() {
        return timeRemaining <= 0;
    }
}
