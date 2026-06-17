package pl.dkupracz.legendsofunknow.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pl.dkupracz.legendsofunknow.entities.Player;
import pl.dkupracz.legendsofunknow.render.IsometricRenderer;
import pl.dkupracz.legendsofunknow.world.GameMap;
import pl.dkupracz.legendsofunknow.input.PlayerInputController;

public class GameScreen  implements Screen {

    private SpriteBatch batch;
    private BitmapFont font;
    private PlayerInputController playerInputController;

    private GameMap gameMap;
    private IsometricRenderer isometricRenderer;
    Player player;

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();

        gameMap = new GameMap(10, 10);
        player = new Player(0, 0);
        playerInputController = new PlayerInputController(player, gameMap);

        isometricRenderer = new IsometricRenderer();
    }

    @Override
    public void render(float delta) {

        playerInputController.update(delta);
        clearScreen();

        isometricRenderer.render(gameMap, player,360, 120);

        batch.begin();
        font.getData().setScale(1.5f);
        font.draw(batch, "Legends of Unknow", 30, 460);
        font.getData().setScale(1f);
        font.draw(batch, "Checkpoint 7: input refactor", 30, 435);
        font.draw(batch, "Player position: " + player.getMapX() + ", " + player.getMapY(), 30, 410);
        font.draw(batch, "Move: W/A/S/D or arrows", 30, 385);

        batch.end();
    }


    private void clearScreen() {
        Gdx.gl.glClearColor(0.0f, 0.025f, 0.04f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
        //TO DO
    }

    @Override
    public void pause() {
        //TO DO
    }

    @Override
    public void resume() {
        //TO DO
    }

    @Override
    public void hide() {
        //TO DO
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        isometricRenderer.dispose();
    }
}
