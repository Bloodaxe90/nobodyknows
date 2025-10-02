package io.github.game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Entity {
    Texture entityTexture;
    int health;
    int damage;
    int[] hitBox;
    int speed;
    int width;
    int height;
    int xPos;
    int yPos;
    int vx;
    int vy;

    public Entity(Texture entityTexture, int width, int height) {
        xPos = 0;
        yPos = 0;
        this.entityTexture = entityTexture;
        this.width = width;
        this.height = height;
    }

    public abstract void update(float delta);
    public abstract void render(SpriteBatch batch);

}
