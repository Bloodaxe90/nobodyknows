package io.github.game.entities.enemy;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import io.github.game.Environment;
import io.github.game.Main;
import io.github.game.entities.player.Player;
import io.github.game.ui.UserIntereface;
import io.github.game.utils.Hitbox;
import io.github.game.utils.interactions.DialogueInteraction;
import io.github.game.utils.interactions.TakeItemInteraction;

public class EnemyManager {

    private final TextureAtlas spriteAtlas;
    private Array<Enemy> enemies = new Array<>();

    public EnemyManager(TextureAtlas spriteAtlas) {
        this.spriteAtlas = spriteAtlas;
        //TODO positions are messy and need to ne changed
        enemies.add(new Enemy("professor", new DialogueInteraction(0, true, true), new Vector2(Main.WORLD_SIZE).scl(7f/8f, 1f/3f),
            new Vector2(16, 16), -1,100, spriteAtlas));
        enemies.add(new Enemy("comp_student", new DialogueInteraction(0, false, true), new Vector2(Main.WORLD_SIZE).scl(5f/8f, 5f/8f).add(0f, -16f),
            new Vector2(16, 16), 0,0, spriteAtlas));
        enemies.add(new Enemy("child", new TakeItemInteraction("keycard", 0,0, true, true), new Vector2(48f, Main.WORLD_SIZE.y - 192f),
            new Vector2(16, 16), 16,200, spriteAtlas));
    }

    public void render(SpriteBatch batch) {
        if (enemies.notEmpty()) {
            for (Enemy enemy : enemies) {
                enemy.render(batch);
            }
        }
    }

    public void update(float delta_t, Environment environment, Player player, UserIntereface ui) {
        if (enemies.notEmpty()) {
            for (Enemy enemy : enemies) {
                enemy.update(delta_t, environment, player, ui);
                if (!enemy.isActive()) {
                    enemies.removeValue(enemy, false);
                }
            }
        }
    }

    public boolean checkCollision(Hitbox hitbox) {
        if (enemies.notEmpty()) {
            for (Enemy enemy : enemies) {
                if (enemy.getHitbox().collides(hitbox)) {
                    enemy.setInteract(true);
                    return true;
                }
            }
        }
        return false;
    }

    public void dispose() {
        spriteAtlas.dispose();
    }
}
