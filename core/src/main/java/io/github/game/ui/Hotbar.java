package io.github.game.ui;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import io.github.game.Main;
import io.github.game.ui.Item;

public class Hotbar extends Table {

    public static final int NUM_SLOTS = 5;

    private Array<Image> itemIcons;

    private TextureAtlas itemAtlas;

    public Hotbar(TextureAtlas uiAtlas) {
        super();

        this.itemAtlas = uiAtlas;
        this.itemIcons = new Array<>(NUM_SLOTS);

        TextureRegion hotbar = new TextureRegion(uiAtlas.findRegion("hotbar"));
        this.setBackground(new TextureRegionDrawable(hotbar));

        // TODO positioning code a bit janky needs to be sorted more universal
        int scaleFactor = 2;
        int width = (int)(hotbar.getRegionWidth() / scaleFactor);
        int height = (int)(hotbar.getRegionHeight() / scaleFactor);

        this.setSize(width, height);

        float padding = 5f;
        float yPos = Main.WORLD_HEIGHT - getHeight() - padding;
        this.setPosition(
            padding,
            yPos
        );

        float iconsY = 2;
        float slotWidth = width / padding;
        int iconSize = (width - (44 / scaleFactor)) / (int)padding;
        float firstIconX = padding - (padding / scaleFactor);
        for (int i = 0; i < NUM_SLOTS; i++) {
            Image itemIcon = new Image();
            itemIcon.setVisible(false);
            float iconX = firstIconX + (i * slotWidth);
            itemIcon.setPosition(iconX, iconsY);
            itemIcon.setSize(iconSize, iconSize);

            itemIcons.add(itemIcon);
            this.addActor(itemIcon);
        }
    }

    public void updateInventory(Array<Item> inventory) {
        for (int i = 0; i < NUM_SLOTS; i++) {
            Image icon = itemIcons.get(i);

            if (i < inventory.size && inventory.get(i) != null) {
                Item item = inventory.get(i);
                icon.setDrawable(new TextureRegionDrawable(itemAtlas.findRegion(item.getName())));
                icon.setVisible(true);
            } else {
                icon.setVisible(false);
            }
        }
    }
}
