package io.github.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import io.github.game.entities.building.BuildingManager;
import io.github.game.entities.enemy.EnemyManager;
import io.github.game.entities.player.Player;
import io.github.game.ui.UserIntereface;

public class Main extends ApplicationAdapter {
    private SpriteBatch spriteBatch;
    private FitViewport gameViewport;
    private OrthographicCamera gameCamera;

    private Environment environment;
    private Player player;
    private EnemyManager enemyManager;
    private BuildingManager buildingManager;

    public boolean playing;
    public static final int WORLD_WIDTH = 320 * 3, WORLD_HEIGHT = 240 * 3;


    private UserIntereface ui;

    @Override
    public void create() {

        spriteBatch = new SpriteBatch();

        gameCamera = new OrthographicCamera();
        gameViewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, gameCamera);

        TiledMap environmentMap = new TmxMapLoader().load("environment/environment.tmx");
        environment = new Environment(environmentMap);

        //TODO positions are messy and need to be changed
        player = new Player(
            "Player",
            (Main.WORLD_WIDTH / 2f) - 8, (Main.WORLD_HEIGHT / 2f) - 16 - 48,
            16, 16,
            200, new TextureAtlas("atlas/character.atlas"));

        enemyManager = new EnemyManager(new TextureAtlas("atlas/enemies.atlas"));

        buildingManager = new BuildingManager(new TextureAtlas("atlas/buildings.atlas"));

        ui = new UserIntereface(0.05f, new TextureAtlas("ui/ui.atlas"), gameViewport);
        playing = false;
    }

    @Override
    public void render() {
        input();
        logic();
        draw();
    }

    public void input() {
        if (isGameOver()) return;
        if (Gdx.input.isKeyJustPressed(Input.Keys.P) ||
            Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) ||
            Gdx.input.isKeyJustPressed(Input.Keys.SPACE) ||
            Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)
        ) {
            playing = !playing;
        }

        if (playing) {
            if (ui.getDialogueBox().isVisible()) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                    if (!ui.getDialogueBox().isFinished()) {
                        ui.getDialogueBox().skip();
                    } else {
                        ui.getDialogueBox().hideDialogue();
                    }
                }
                return;
            }

            player.setMovingUp(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W));
            player.setMovingDown(Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S));
            player.setMovingLeft(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A));
            player.setMovingRight(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D));
        }

    }

    public void logic() {
        float delta_t = Gdx.graphics.getDeltaTime();

        if (ui.getStatusBar().isTimeUp()) playing = false;
        // update your player, enemies, and check for collisions
        if (playing) {
            buildingManager.update(player, ui);
            if (!ui.getDialogueBox().isVisible()) {
                enemyManager.update(delta_t, environment, player, ui);
            }
            player.update(delta_t, environment, buildingManager, enemyManager);

        }
        ui.update(delta_t, playing, player);
    }


    public void draw() {
        ScreenUtils.clear(Color.BLACK);
        gameViewport.apply();
        environment.render(gameCamera);
        spriteBatch.setProjectionMatrix(gameViewport.getCamera().combined);

        spriteBatch.begin();

        // Draw in here
        buildingManager.render(spriteBatch);
        enemyManager.render(spriteBatch);
        player.render(spriteBatch);


        spriteBatch.end();
        ui.render();
    }

    private boolean isGameOver() {
        if (ui.getPauseMenu().getText().toLowerCase().replaceAll("[^a-z]", "").contains("gameover")) {
            playing = false;
            return true;
        }
        return false;
    }


    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        environment.dispose();
        player.dispose();
        enemyManager.dispose();
        buildingManager.dispose();
        ui.dispose();
    }
}
