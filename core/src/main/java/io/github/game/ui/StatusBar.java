package io.github.game.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align; // Import Align

public class StatusBar extends Table {

    private Label status;

    private int eventsCompleted = 0;

    private static final int MAX_EVENTS = 5;

    private float timeRemaining = 300f;

    public StatusBar(Skin skin) {
        super(skin);

        this.top().right();
        status = new Label("", skin);
        updateStatusText();

        this.add(status).align(Align.right);

    }

    public void update(float delta) {
        if (timeRemaining > 0) {
            timeRemaining -= delta;
        } else {
            timeRemaining = 0;
        }

        updateStatusText();
    }

    private void updateStatusText() {
        int minutes = (int) (timeRemaining / 60);
        int seconds = (int) (timeRemaining % 60);
        status.setText(String.format("Events: %d / %d\nTime: %02d:%02d", eventsCompleted, MAX_EVENTS, minutes, seconds));
    }

    public void incrementEventCounter() {
        if (eventsCompleted < MAX_EVENTS) {
            eventsCompleted++;
        }
    }

    public boolean isTimeUp() {
        return timeRemaining <= 0;
    }
}
