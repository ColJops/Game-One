package pl.dkupracz.legendsofunknow.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import pl.dkupracz.legendsofunknow.entities.Player;
import pl.dkupracz.legendsofunknow.world.GameMap;

public class PlayerInputController {

    private static final float MOVE_COOLDOWN_TIME = 0.15f;

    private final Player player;
    private final GameMap gameMap;

    private float moveCooldown;

    public PlayerInputController(Player player, GameMap gameMap) {
        this.player = player;
        this.gameMap = gameMap;
    }

    public void update(float delta) {
        moveCooldown -= delta;

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

    private void resetCooldown() {
        moveCooldown = MOVE_COOLDOWN_TIME;
    }
}
