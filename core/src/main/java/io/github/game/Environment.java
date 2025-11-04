package io.github.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import io.github.game.utils.Hitbox;

/**
 * This class is the games environment
 * It loads the Tiled map and handles things like:
 * - rendering tiles
 * - collision checks
 * - manages the players spawn point
 */
public class Environment {

    private final TiledMap environmentMap;
    private final OrthogonalTiledMapRenderer environmentRenderer;
    private final Vector2 spawnPoint;

    /**
     * Makes a new environment
     * @param environmentMap the .tmx map file from Tiled
     * @param spriteBatch used to draw the environment
     * @param spawnPoint where the player should start on the environment
     */
    public Environment(TiledMap environmentMap, SpriteBatch spriteBatch, Vector2 spawnPoint) {
        this.environmentMap = environmentMap;

        // Renderer draws the Tiled environment to the screen
        this.environmentRenderer = new OrthogonalTiledMapRenderer(environmentMap, 1f, spriteBatch);

        this.spawnPoint = spawnPoint;
    }

    /**
     * Draws the environment
     * @param camera the game camera, so the renderer knows what parts of the environemnt to show
     */
    public void render(OrthographicCamera camera) {
        // Tells renderer what area the camera sees
        environmentRenderer.setView(camera);

        // draw the tiles
        environmentRenderer.render();
    }

    /**
     * Cleans up memory when closing the game
     */
    public void dispose() {
        environmentMap.dispose();
        environmentRenderer.dispose();
    }

    /**
     * Checks if a hitbox touches any rectangle in the Collisions object layer
     * @param hitbox the hitbox we want to check if it has been collided with (usually the player or enemy)
     * @return true if a collision has occurred, otherwise false
     */
    public boolean checkCollision(Hitbox hitbox) {
        // Loop through every Rectangle in the "Collision" layer of the Tiled environment
        for (MapObject object : environmentMap.getLayers().get("Collision").getObjects()) {
            if (object instanceof RectangleMapObject) {
                Rectangle mapRect = ((RectangleMapObject) object).getRectangle();

                // If the players hitbox overlaps a Rectangle a collision has occured
                if (Intersector.overlaps(hitbox.getBounds(), mapRect)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return tile size in pixels
     */
    public int getTileSize() {
        return environmentMap.getProperties().get("tilewidth", Integer.class);
    }

    /**
     * @return spawn point for the player (x,y)
     */
    public Vector2 getSpawn() {
        return spawnPoint;
    }
}
