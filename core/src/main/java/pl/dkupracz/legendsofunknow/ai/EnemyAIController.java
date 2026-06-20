package pl.dkupracz.legendsofunknow.ai;

import com.badlogic.gdx.Gdx;

import pl.dkupracz.legendsofunknow.entities.Player;
import pl.dkupracz.legendsofunknow.config.GameConfig;
import pl.dkupracz.legendsofunknow.entities.Enemy;
import pl.dkupracz.legendsofunknow.world.GameMap;

import java.util.List;
import java.util.Random;

public class EnemyAIController {

    private final List<Enemy> enemies;
    private final GameMap gameMap;
    private final Random random;
    private final Player player;

    private float moveCooldown;

    public EnemyAIController(List<Enemy> enemies, GameMap gameMap, Player player) {
        this.enemies = enemies;
        this.gameMap = gameMap;
        this.player = player;
        this.random = new Random();
    }

    public void update(float delta) {
        moveCooldown -= delta;

        if (moveCooldown > 0f) {
            return;
        }

        moveEnemiesRandomly();
        moveCooldown = GameConfig.ENEMY_MOVE_COOLDOWN;
    }

    private void moveEnemiesRandomly() {
        for (Enemy enemy : enemies) {
            if (enemy.isDead()) {
                continue;
            }

            if (player.isDead()) {
                return;
            }

            if (isAdjacentToPlayer(enemy)) {
                attackPlayer(enemy);
            } else {
                moveEnemyRandomly(enemy);
            }
        }
    }

    private void moveEnemyRandomly(Enemy enemy) {
        int direction = random.nextInt(5);

        switch (direction) {
            case 0 -> enemy.moveBy(0, 1, gameMap);
            case 1 -> enemy.moveBy(0, -1, gameMap);
            case 2 -> enemy.moveBy(-1, 0, gameMap);
            case 3 -> enemy.moveBy(1, 0, gameMap);
            case 4 -> {
                // Enemy waits this turn.
            }
            default -> {
                // Should never happen.
            }
        }
    }

    private boolean isAdjacentToPlayer(Enemy enemy) {
        int distanceX = Math.abs(enemy.getMapX() - player.getMapX());
        int distanceY = Math.abs(enemy.getMapY() - player.getMapY());

        return distanceX + distanceY == 1;
    }

    private void attackPlayer(Enemy enemy) {
        player.takeDamage(GameConfig.ENEMY_ATTACK_DAMAGE);

        Gdx.app.log(
            "Combat",
            enemy.getName()
                + " attacked player for "
                + GameConfig.ENEMY_ATTACK_DAMAGE
                + " damage. Player HP: "
                + player.getCurrentHp()
                + "/"
                + player.getMaxHp()
        );
    }
}
