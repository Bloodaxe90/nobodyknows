package io.github.game.utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class Torch {

    /**
     * A class for the torchlight shader
     */
    private final ShaderProgram shader;

    /**
     * Creates a new torch shader
     */
    public Torch() {
        ShaderProgram.pedantic = false;
        String vertexShader = Gdx.files.internal("shaders/vertex.glsl").readString();
        String fragmentShader = Gdx.files.internal("shaders/vignette.glsl").readString();
        shader = new ShaderProgram(vertexShader, fragmentShader);
    }

    /**
     * Renders the shader to the screen
     * 
     * @param xPos The x position for the centre of the light
     * @param yPos The y position for the centre of the light
     * @param batch The spritebatch to render the shader to
     */
    public void render(float xPos, float yPos, SpriteBatch batch) {
        shader.bind();
        // TODO issue where torch effect scales incorrectly when window width changes

        // Sets the resolution and lightPos variables of the shader according to the window size and the
        // desired centre of the light.
        shader.setUniformf("u_resolution", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shader.setUniformf("u_lightPos", xPos / Gdx.graphics.getWidth(), yPos / Gdx.graphics.getHeight());
        batch.setShader(shader);

    }

    public ShaderProgram getShader() {
        return shader;
    }

    /** 
    * Clean up memory 
    */
    public void dispose() {
        shader.dispose();
    }

}
