package io.github.game.ui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Item {

    private final String name;

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
