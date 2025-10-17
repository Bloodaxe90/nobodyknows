package io.github.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;

public class TorchEffect extends ApplicationAdapter {

    private float time;
    private SpriteBatch batch;
    private String vertexShader;
    private String fragmentShader;
    private ShaderProgram shaderProgram;

    public void create(SpriteBatch batch) {
        this.batch = batch;
        vertexShader = Gdx.files.internal("shaders/vertex.glsl").readString();
        fragmentShader = Gdx.files.internal("shaders/vignette.glsl").readString();
        shaderProgram = new ShaderProgram(vertexShader, fragmentShader);
        shaderProgram.pedantic = false;

        batch.setShader(shaderProgram);

        time = 0;
    }

    public void render(float xPos, float yPos) {

        Vector2 v = new Vector2(xPos, yPos);
        
        shaderProgram.setUniformf("u_resolution", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    }

    @Override
    public void dispose() {
        shaderProgram.dispose();
    }

}
