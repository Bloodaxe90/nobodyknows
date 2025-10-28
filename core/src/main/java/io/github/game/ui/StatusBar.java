package io.github.game.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align; // Import Align

public class StatusBar extends Table {

    private Label status;

    private int eventsCompleted = 0;

    public static final int MAX_EVENTS = 5;

    private float timeRemaining = 300f;

    public StatusBar(Skin skin) {
        super(skin);

        this.top().right();
        status = new Label("", skin);
        updateStatusText();

        this.add(status).align(Align.right);

    }

    public void update(float delta) {
        if (!isTimeUp()) {
            timeRemaining -= delta;
        } else {
            timeRemaining = 0;
        }

        updateStatusText();
    }

    private void updateStatusText() {
        int minutes = (int) (timeRemaining / 60);
        int seconds = (int) (timeRemaining % 60);
        status.setText("Events: " + eventsCompleted + "/" + MAX_EVENTS + "\nTime: " + minutes + ":" + seconds);
    }

    public void incrementEventCounter() {
        if (eventsCompleted < MAX_EVENTS) {
            eventsCompleted++;
        }
    }

    public String getStatusText() {
        return status.getText().toString();
    }

    public boolean isTimeUp() {
        return timeRemaining <= 0;
    }

}
