package io.github.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import io.github.game.utils.Hitbox;


public class Environment {

    private final TiledMap environmentMap;
    private final OrthogonalTiledMapRenderer environmentRenderer;


    public Environment(TiledMap environmentMap, SpriteBatch spriteBatch) {
        this.environmentMap = environmentMap;
        this.environmentRenderer = new OrthogonalTiledMapRenderer(environmentMap, 1f, spriteBatch);

    }

    public void render(OrthographicCamera camera) {
        environmentRenderer.setView(camera);
        environmentRenderer.render();
    }

    public void dispose() {
        environmentMap.dispose();
        environmentRenderer.dispose();
    }

    public boolean checkCollision(Hitbox hitbox) {
        for (MapObject object : environmentMap.getLayers().get("Collision").getObjects()) {
            if (object instanceof RectangleMapObject) {
                Rectangle mapRect = ((RectangleMapObject) object).getRectangle();
                if (Intersector.overlaps(hitbox.getBounds(), mapRect)) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getTileSize() {
        return environmentMap.getProperties().get("tilewidth", Integer.class);
    }

}
