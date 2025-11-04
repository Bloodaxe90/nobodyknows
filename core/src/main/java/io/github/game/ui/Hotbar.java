package io.github.game.ui;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

import io.github.game.Main;

/**
 * UI bar that shows the players inventory
 */
public class Hotbar extends Table {

    public static final int NUM_SLOTS = 5; // Number of item slots in the hotbar

    private Array<Image> itemIcons; // Stores icon images for each slot

    private final TextureAtlas itemAtlas; // Atlas containing item textures


    /**
     * Creates a hotbar UI element
     *
     * @param uiAtlas sprite sheet for hotbar
     */
    public Hotbar(TextureAtlas uiAtlas) {
        super();

        this.itemAtlas = uiAtlas;
        this.itemIcons = new Array<>(NUM_SLOTS);

        // Background image of the hotbar
        TextureRegion hotbar = new TextureRegion(uiAtlas.findRegion("hotbar"));
        this.setBackground(new TextureRegionDrawable(hotbar));

        // TODO: Positioning math is trash, needs proper scaling system later
        int width = hotbar.getRegionWidth();
        int height = hotbar.getRegionHeight();
        this.setSize(width, height);

        // Padding from screen edge
        float padding = 5f;

        // Place hotbar near top of screen
        float yPos = Main.WORLD_SIZE.y - getHeight() - padding;
        this.setPosition(padding, yPos);

        // Slot placement variables
        float iconsY = 2f;
        float slotWidth = width / padding;
        int iconSize = (width - 46) / (int) padding;
        float firstIconX = padding;

        // Create empty image slots (hidden until item in inventory)
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

    /**
     * Updates hotbar based on the inventory.
     * Shows icon if item is in inventory
     *
     * @param inventory inventory
     */
    public void updateInventory(Array<Item> inventory) {
        for (int i = 0; i < NUM_SLOTS; i++) {
            Image icon = itemIcons.get(i);

            // Slot contains an item
            if (i < inventory.size && inventory.get(i) != null) {
                Item item = inventory.get(i);
                icon.setDrawable(new TextureRegionDrawable(itemAtlas.findRegion(item.getName())));
                icon.setVisible(true);
            }
            // Slot empty
            else {
                icon.setVisible(false);
            }
        }
    }
}
