package pl.dkupracz.legendsofunknow.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import pl.dkupracz.legendsofunknow.entities.Enemy;
import pl.dkupracz.legendsofunknow.entities.Player;
import pl.dkupracz.legendsofunknow.world.GameMap;
import pl.dkupracz.legendsofunknow.config.GameConfig;

import java.util.List;

public class PlayerInputController {

    private final Player player;
    private final GameMap gameMap;
    private final List<Enemy> enemies;

    private float moveCooldown;
    private float debugCooldown;
    private float attackCooldown;

    public PlayerInputController(Player player, GameMap gameMap, List<Enemy> enemies) {
        this.player = player;
        this.gameMap = gameMap;
        this.enemies = enemies;
    }

    public void update(float delta) {
        moveCooldown -= delta;
        debugCooldown -= delta;
        attackCooldown -= delta;

        handleDebugInput();
        handleAttackInput();

        if (moveCooldown > 0f) {
            return;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player.moveBy(0, 1, gameMap);
            resetCooldown();
        } else if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player.moveBy(0, -1, gameMap);
            resetCooldown();
        } else if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.moveBy(-1, 0, gameMap);
            resetCooldown();
        } else if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.moveBy(1, 0, gameMap);
            resetCooldown();
        }
    }

    private void handleAttackInput() {
        if (attackCooldown > 0f) {
            return;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            attackAdjacentEnemy();
            attackCooldown = GameConfig.PLAYER_ATTACK_COOLDOWN;
        }
    }

    private void attackAdjacentEnemy() {
        for (Enemy enemy : enemies) {
            if (enemy.isDead()) {
                continue;
            }

            int distanceX = Math.abs(enemy.getMapX() - player.getMapX());
            int distanceY = Math.abs(enemy.getMapY() - player.getMapY());

            boolean isAdjacent = distanceX + distanceY == 1;

            if (isAdjacent) {
                enemy.takeDamage(GameConfig.PLAYER_ATTACK_DAMAGE);
                Gdx.app.log(
                    "Combat",
                    "Player attacked " + enemy.getName()
                        + " for " + GameConfig.PLAYER_ATTACK_DAMAGE
                        + " damage. Enemy HP: "
                        + enemy.getCurrentHp() + "/" + enemy.getMaxHp()
                );
                return;
            }
        }

        Gdx.app.log("Combat", "No enemy in range.");
    }

    private void handleDebugInput() {
        if (debugCooldown > 0f) {
            return;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.H)) {
            player.takeDamage(10);
            debugCooldown = 0.2f;
        } else if (Gdx.input.isKeyPressed(Input.Keys.J)) {
            player.heal(10);
            debugCooldown = 0.2f;
        }
    }

    private void resetCooldown() {
        moveCooldown = GameConfig.PLAYER_MOVE_COOLDOWN;
    }
}
