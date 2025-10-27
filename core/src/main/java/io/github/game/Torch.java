package io.github.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class Torch {

    private final ShaderProgram shader;

    public Torch() {
        ShaderProgram.pedantic = false;
        String vertexShader = Gdx.files.internal("shaders/vertex.glsl").readString();
        String fragmentShader = Gdx.files.internal("shaders/vignette.glsl").readString();
        shader = new ShaderProgram(vertexShader, fragmentShader);
    }

    public void render(float xPos, float yPos, SpriteBatch batch) {
        shader.bind();
        shader.setUniformf("u_resolution", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shader.setUniformf("u_lightPos", xPos / Main.WORLD_WIDTH, yPos / Main.WORLD_HEIGHT);
        batch.setShader(shader);

    }

    public void dispose() {
        shader.dispose();
    }

}
