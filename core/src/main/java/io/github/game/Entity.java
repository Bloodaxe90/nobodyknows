package io.github.game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Entity {
    TextureRegion currentTexture;
    int health;
    int damage;
    Hitbox hitbox;
    int speed;
    int width;
    int height;
    int xPos;
    int yPos;
    int vx;
    int vy;

    public Entity(int width, int height) {
        xPos = 0;
        yPos = 0;
        this.width = width;
        this.height = height;
    }

    public abstract void update(float delta);
    public abstract void render(SpriteBatch batch);

}
