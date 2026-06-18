package pl.dkupracz.legendsofunknow.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import pl.dkupracz.legendsofunknow.entities.Player;
import pl.dkupracz.legendsofunknow.world.GameMap;
import pl.dkupracz.legendsofunknow.config.GameConfig;

public class PlayerInputController {

    private final Player player;
    private final GameMap gameMap;

    private float moveCooldown;

    private float debugCooldown;

    public PlayerInputController(Player player, GameMap gameMap) {
        this.player = player;
        this.gameMap = gameMap;
    }

    public void update(float delta) {
        moveCooldown -= delta;

        debugCooldown -= delta;
        handleDebugInput();

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
