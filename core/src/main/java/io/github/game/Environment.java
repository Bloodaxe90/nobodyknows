package io.github.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Environment {

    private TiledMap tiles;
    private OrthogonalTiledMapRenderer environmentRenderer;

    public Environment(String mapFileName) {
        tiles = new TmxMapLoader().load(mapFileName);
        environmentRenderer = new OrthogonalTiledMapRenderer(tiles, 1f);
    }

    public void render(OrthographicCamera camera) {
        environmentRenderer.setView(camera);
        environmentRenderer.render();
    }

    public void dispose() {
        tiles.dispose();
        environmentRenderer.dispose();
    }

    public boolean checkCollision(Hitbox hitbox) {
        for (MapObject object : tiles.getLayers().get("Collision").getObjects()) {
            if (object instanceof RectangleMapObject) {
                Rectangle mapRect = ((RectangleMapObject) object).getRectangle();
                if (Intersector.overlaps(hitbox.getBounds(), mapRect)) {
                    return true;
                }
            }
        }
        return false;
    }
}
