package io.github.game.utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import io.github.game.Main;

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
        // TODO issue where torch effect scales incorrectly when window width changes
        shader.setUniformf("u_resolution", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shader.setUniformf("u_lightPos", xPos / Gdx.graphics.getWidth(), yPos / Gdx.graphics.getHeight());
        batch.setShader(shader);

    }

    public ShaderProgram getShader() {
        return shader;
    }

    public void dispose() {
        shader.dispose();
    }

}
