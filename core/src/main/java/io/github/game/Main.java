package io.github.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.game.utils.EnvironmentReader;

/** {@link ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch spriteBatch;
    private FitViewport viewport;

    private Environment environment;
    private Player player;
    public static final int WORLD_WIDTH = 320, WORLD_HEIGHT = 240;

    @Override
    public void create() {

        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);

        int[][] environmentBlueprint = EnvironmentReader.readEnvironment("environment/environment.txt");

        this.environment = new Environment(environmentBlueprint, new TextureAtlas("atlas/tiles.atlas"));
        player = new Player(
            0, 0,
            16, 16,
            200, new TextureAtlas("atlas/character.atlas"), environment);

    }

    @Override
    public void render() {
        input();
        logic();
        draw();
    }

    public void input() {
        player.setMovingUp(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W));
        player.setMovingDown(Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S));
        player.setMovingLeft(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A));
        player.setMovingRight(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D));
    }

    public void logic() {
        float delta_t = Gdx.graphics.getDeltaTime();
        // update your player, enemies, and check for collisions
        player.update(delta_t, environment);
        environment.update(delta_t);
    }

    public void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        spriteBatch.begin();

        // Draw in here
        environment.render(spriteBatch);
        player.render(spriteBatch);

        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        environment.dispose();
        player.dispose();
    }
    @Override
    public void pause() {}

    @Override
    public void resume() {}
}
