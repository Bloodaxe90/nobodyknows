package io.github.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import io.github.game.ui.UserIntereface;

public class Main extends ApplicationAdapter {
    private SpriteBatch spriteBatch;
    private FitViewport gameViewport;
    private AudioPlayer audioPlayer;

    private Environment environment;
    private Player player;
    public static final int WORLD_WIDTH = 320, WORLD_HEIGHT = 240;
    public boolean playing = true;
    public static Vector2 actualWorldSize;

    private OrthographicCamera gameCamera;

    private UserIntereface ui;

    @Override
    public void create() {

        spriteBatch = new SpriteBatch();

        gameCamera = new OrthographicCamera();
        gameViewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, gameCamera);

        environment = new Environment("environment/environment.tmx", spriteBatch);
        actualWorldSize =  new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        player = new Player(
            0, 0,
            16, 16,
            200, new TextureAtlas("atlas/character.atlas"));

        ui = new UserIntereface(0.05f, new TextureAtlas("ui/ui.atlas"), gameViewport);
        audioPlayer = new AudioPlayer();
        audioPlayer.playTrack("bgm");
    }

    @Override
    public void render() {
        input();
        logic();
        draw();
    }

    public void input() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.P) ||
            Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) ||
            Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)
        ) {
            playing = !playing;
            audioPlayer.setMusicEnabled(!audioPlayer.musicEnabled);
        }

        if (playing) {
            if (ui.getDialogueBox().isVisible()) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                    if (!ui.getDialogueBox().isFinished()) {
                        ui.getDialogueBox().skip();
                    } else {
                        ui.getDialogueBox().hideDialogue();
                    }
                }
                return;
            }


            // TODO Remove later, testing stuff
            if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
                ui.getDialogueBox().showDialogue("Joe:\n Sigma sigma on the wall who's the fairest of them all?");
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
                player.addItem("keycard");
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
                ui.getStatusBar().incrementEventCounter();
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
            player.update(delta_t, environment);
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
        player.render(spriteBatch);

        spriteBatch.end();
        ui.render();
    }


    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height, true);
        // Calculates the new actual displayed size of the game world in pixels
        float aspectRatio = 4 / 3;
        if (width / height > aspectRatio) { 
            actualWorldSize.y = height;
            actualWorldSize.x = aspectRatio  * height;
        }
        else {
            actualWorldSize.x = width;
            actualWorldSize.y = aspectRatio * height;
        }
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        environment.dispose();
        player.dispose();
        ui.dispose();
    }
}
