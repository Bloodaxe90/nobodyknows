package io.github.game;
import com.badlogic.gdx.graphics.Texture;

public class Entity {
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

    public Entity(Texture entityTexture) {
        this.entityTexture = entityTexture;
        this.hitBox = new int[] {width, height};
    }

}
