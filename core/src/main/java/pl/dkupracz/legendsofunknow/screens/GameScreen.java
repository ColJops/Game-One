package pl.dkupracz.legendsofunknow.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input;

import pl.dkupracz.legendsofunknow.entities.Player;
import pl.dkupracz.legendsofunknow.render.IsometricRenderer;
import pl.dkupracz.legendsofunknow.world.GameMap;

public class GameScreen  implements Screen {

    private SpriteBatch batch;
    private BitmapFont font;
    private float moveCooldown;

    private GameMap gameMap;
    private IsometricRenderer isometricRenderer;
    Player player;

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();

        gameMap = new GameMap(10, 10);
        player = new Player(0, 0);
        isometricRenderer = new IsometricRenderer();
    }

    @Override
    public void render(float delta) {

        handleInput(delta);
        clearScreen();

        isometricRenderer.render(gameMap, player,360, 120);

        batch.begin();
        font.getData().setScale(1.5f);
        font.draw(batch, "Legends of Unknow", 30, 460);
        font.getData().setScale(1f);
        font.draw(batch, "Checkpoint 6: player movement", 30, 435);
        font.draw(batch, "Player position: " + player.getMapX() + ", " + player.getMapY(), 30, 410);
        font.draw(batch, "Move: W/A/S/D or arrows", 30, 385);

        batch.end();
    }

    private void handleInput(float delta) {
        moveCooldown -= delta;

        if (moveCooldown > 0f) {
            return;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player.moveBy(0, 1, gameMap);
            moveCooldown = 0.15f;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player.moveBy(0, - 1, gameMap);
            moveCooldown = 0.15f;
        } else if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.moveBy(-1, 0, gameMap);
            moveCooldown = 0.15f;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.moveBy(1, 0, gameMap);
            moveCooldown = 0.15f;
        }
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
