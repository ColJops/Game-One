package pl.dkupracz.legendsofunknow.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import pl.dkupracz.legendsofunknow.entities.Player;
import pl.dkupracz.legendsofunknow.render.IsometricRenderer;
import pl.dkupracz.legendsofunknow.world.GameMap;
import pl.dkupracz.legendsofunknow.input.PlayerInputController;
import pl.dkupracz.legendsofunknow.config.GameConfig;

public class GameScreen  implements Screen {

    private SpriteBatch batch;
    private BitmapFont font;

    private OrthographicCamera worldCamera;
    private Viewport worldViewport;

    private OrthographicCamera hudCamera;
    private Viewport hudViewport;

    private PlayerInputController playerInputController;
    private GameMap gameMap;
    private IsometricRenderer isometricRenderer;
    Player player;

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();

        worldCamera = new OrthographicCamera();
        worldViewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, worldCamera);
        worldViewport.apply();

        worldCamera.position.set(GameConfig.WORLD_WIDTH / 2f, GameConfig.WORLD_HEIGHT / 2f, 0f);
        worldCamera.update();

        hudCamera = new OrthographicCamera();
        hudViewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, hudCamera);
        hudViewport.apply();

        hudCamera.position.set(GameConfig.WORLD_WIDTH / 2f, GameConfig.WORLD_HEIGHT / 2f, 0f);
        hudCamera.update();

        gameMap = new GameMap(GameConfig.MAP_WIDTH, GameConfig.MAP_HEIGHT);
        player = new Player(0, 0);
        playerInputController = new PlayerInputController(player, gameMap);

        isometricRenderer = new IsometricRenderer();
    }

    @Override
    public void render(float delta) {
        playerInputController.update(delta);
        updateCamera(delta);

        clearScreen();

        renderWorld();
        renderHud();
    }

    private void renderWorld() {
        isometricRenderer.setProjectionMatrix(worldCamera.combined);
        isometricRenderer.render(
            gameMap,
            player,
            GameConfig.MAP_OFFSET_X,
            GameConfig.MAP_OFFSET_Y
        );
    }

    private void renderHud() {
        batch.setProjectionMatrix(hudCamera.combined);

        batch.begin();
        font.getData().setScale(1.5f);
        font.draw(batch, "Legends of Unknow", 30, 460);

        font.getData().setScale(1f);
        font.draw(batch, "Checkpoint 11: separate HUD camera", 30, 435);
        font.draw(batch, "Player position: " + player.getMapX() + ", " + player.getMapY(), 30, 410);
        font.draw(batch, "Move: W/A/S/D or arrows", 30, 385);
        batch.end();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0.0f, 0.025f, 0.04f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void updateCamera(float delta) {
        float playerScreenX = isometricRenderer.getScreenX(
            player.getMapX(),
            player.getMapY(),
            GameConfig.MAP_OFFSET_X
        );

        float playerScreenY = isometricRenderer.getScreenY(
            player.getMapX(),
            player.getMapY(),
            GameConfig.MAP_OFFSET_Y
        );

        worldCamera.position.x += (playerScreenX - worldCamera.position.x) * GameConfig.CAMERA_FOLLOW_SPEED * delta;
        worldCamera.position.y += (playerScreenY - worldCamera.position.y) * GameConfig.CAMERA_FOLLOW_SPEED * delta;

        worldCamera.update();
    }

    @Override
    public void resize(int width, int height) {
        worldViewport.update(width, height, true);
        hudViewport.update(width, height, true);
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
