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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import io.github.game.entities.building.BuildingManager;
import io.github.game.entities.enemy.EnemyManager;
import io.github.game.entities.player.Player;
import io.github.game.ui.UserIntereface;
import io.github.game.utils.io.AudioPlayer;

/**
 * This class sets everything up and
 * runs the main game loop (input -> logic -> draw).
 */
public class Main extends ApplicationAdapter {

    private SpriteBatch spriteBatch;
    private FitViewport gameViewport;
    private OrthographicCamera gameCamera;

    private Environment environment;
    private Player player;
    private EnemyManager enemyManager;
    private BuildingManager buildingManager;

    public boolean playing;
    public static final Vector2 WORLD_SIZE = new Vector2(320 * 3, 240 * 3);
    public static final Vector2 WORLD_CENTER = new Vector2(WORLD_SIZE).scl(0.5f);

    private UserIntereface ui;

    @Override
    public void create() {

        spriteBatch = new SpriteBatch();

        // Camera and viewport so the world scales properly on any screen size
        gameCamera = new OrthographicCamera();
        gameViewport = new FitViewport(WORLD_SIZE.x, WORLD_SIZE.y, gameCamera);

        // Load the map made in tiled
        TiledMap environmentMap = new TmxMapLoader().load("environment/environment.tmx");
        environment = new Environment(environmentMap, spriteBatch, new Vector2(WORLD_CENTER).add(0, -100f));

        // TODO: player spawn position is temporary / messy rn
        player = new Player(
            "Player",
            environment.getSpawn(),
            new Vector2(16, 16),
            new Vector2(6, 0),
            new Vector2(6, 4),
            3f, 100,
            new TextureAtlas("atlas/character.atlas"));

        // Instantiate managers for enemies and buildings
        enemyManager = new EnemyManager(new TextureAtlas("atlas/enemies.atlas"));
        buildingManager = new BuildingManager(new TextureAtlas("atlas/buildings.atlas"));

        // UI (dialogue, hotbar...)
        ui = new UserIntereface(new TextureAtlas("ui/ui.atlas"), gameViewport);

        playing = false; // Game starts paused until player presses something

        AudioPlayer.playTrack("bgm", 0.3f);
        AudioPlayer.setMusicEnabled(false);
    }

    @Override
    public void render() {
        input(); // Check for inputs
        logic(); // Update game world
        draw();  // Draw everything
    }

    /**
     * Handles all keyboard / mouse inputs
     */
    public void input() {
        if (isGameOver()) return;

        // Press P or ESC or left-click to toggle pause
        if (Gdx.input.isKeyJustPressed(Input.Keys.P) ||
            Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) ||
            Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)
        ) {
            playing = !playing;
            AudioPlayer.setMusicEnabled(playing);
        }

        if (playing) {
            // If in dialogue mode only accept the skip key
            if (ui.getDialogueBox().isVisible()) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    if (!ui.getDialogueBox().isFinished()) {
                        ui.getDialogueBox().skip(); // Skip dialogue
                    } else {
                        ui.getDialogueBox().hideDialogue(); // Close dialogue
                    }
                }
                return;
            }

            // Handle Movement inputs from WASD or Arrow keys
            player.setMovingUp(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W));
            player.setMovingDown(Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S));
            player.setMovingLeft(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A));
            player.setMovingRight(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D));
        }
    }

    /**
     * Updates all game logic like movement and collisions
     */
    public void logic() {
        float delta_t = Gdx.graphics.getDeltaTime(); // Time since last frame

        if (playing) {
            buildingManager.update(player, ui);

            // Stop enemy moving while reading dialogue
            if (!ui.getDialogueBox().isVisible()) {
                enemyManager.update(delta_t, environment, player, ui);
            }

            player.update(delta_t, environment, buildingManager, enemyManager);
        }

        // UI always updates
        ui.update(delta_t, playing, player);
    }

    /**
     * Draws the game world and UI
     */
    public void draw() {
        ScreenUtils.clear(Color.BLACK);

        gameViewport.apply();
        environment.render(gameCamera);

        spriteBatch.setProjectionMatrix(gameViewport.getCamera().combined);
        spriteBatch.begin();

        // Draw world objects (order matters!)
        buildingManager.render(spriteBatch);
        enemyManager.render(spriteBatch);
        player.render(spriteBatch, gameViewport);

        spriteBatch.end();

        // Draw UI on top last
        ui.render();
    }

    /**
     * Checks if the game is over
     * @return true if the game is over
     */
    private boolean isGameOver() {
        // Lowercase and strip symbols to safely check text
        if (ui.getPauseMenu().getText().toLowerCase().replaceAll("[^a-z]", "").contains("gameover")) {
            playing = false;
            return true;
        }
        return false;
    }

    @Override
    public void resize(int width, int height) {
        // Makes camera adjust when window is resized
        gameViewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        // Clean up
        spriteBatch.dispose();
        environment.dispose();
        player.dispose();
        enemyManager.dispose();
        buildingManager.dispose();
        ui.dispose();
    }
}
