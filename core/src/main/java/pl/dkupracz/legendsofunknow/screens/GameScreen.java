package pl.dkupracz.legendsofunknow.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import pl.dkupracz.legendsofunknow.entities.Enemy;
import pl.dkupracz.legendsofunknow.entities.Player;
import pl.dkupracz.legendsofunknow.render.IsometricRenderer;
import pl.dkupracz.legendsofunknow.world.GameMap;
import pl.dkupracz.legendsofunknow.input.PlayerInputController;
import pl.dkupracz.legendsofunknow.config.GameConfig;
import pl.dkupracz.legendsofunknow.ui.HudRenderer;
import pl.dkupracz.legendsofunknow.ai.EnemyAIController;
import pl.dkupracz.legendsofunknow.assets.GameAssets;

import java.util.ArrayList;
import java.util.List;

public class GameScreen  implements Screen {

    private SpriteBatch batch;
    private BitmapFont font;

    private OrthographicCamera worldCamera;
    private Viewport worldViewport;

    private OrthographicCamera hudCamera;
    private Viewport hudViewport;

    private PlayerInputController playerInputController;
    private EnemyAIController enemyAIController;

    private GameMap gameMap;
    private IsometricRenderer isometricRenderer;
    private HudRenderer hudRenderer;
    private GameAssets gameAssets;

    private float cameraMinX;
    private float cameraMaxX;
    private float cameraMinY;
    private float cameraMaxY;

    private Player player;
    private List<Enemy> enemies;

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();

        gameAssets = new GameAssets();
        hudRenderer = new HudRenderer(batch, font);

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

        enemies = new ArrayList<>();
        enemies.add(new Enemy("Skeleton", 5, 5, 30));
        enemies.add(new Enemy("Ghoul", 8, 3, 40));
        enemies.add(new Enemy("Cultist", 8, 10, 50));

        playerInputController = new PlayerInputController(player, gameMap, enemies);
        enemyAIController = new EnemyAIController(enemies, gameMap , player);

        isometricRenderer = new IsometricRenderer(gameAssets);

        calculateCameraBounds();
    }

    private void calculateCameraBounds() {
        float x0 = isometricRenderer.getScreenX(0, 0, GameConfig.MAP_OFFSET_X);
        float y0 = isometricRenderer.getScreenY(0, 0, GameConfig.MAP_OFFSET_Y);

        float x1 = isometricRenderer.getScreenX(gameMap.getWidth() - 1, 0, GameConfig.MAP_OFFSET_X);
        float y1 = isometricRenderer.getScreenY(gameMap.getWidth() - 1, 0, GameConfig.MAP_OFFSET_Y);

        float x2 = isometricRenderer.getScreenX(0, gameMap.getHeight() - 1, GameConfig.MAP_OFFSET_X);
        float y2 = isometricRenderer.getScreenY(0, gameMap.getHeight() - 1, GameConfig.MAP_OFFSET_Y);

        float x3 = isometricRenderer.getScreenX(gameMap.getWidth() - 1, gameMap.getHeight() - 1, GameConfig.MAP_OFFSET_X);
        float y3 = isometricRenderer.getScreenY(gameMap.getWidth() - 1, gameMap.getHeight() - 1, GameConfig.MAP_OFFSET_Y);

        cameraMinX = Math.min(Math.min(x0, x1), Math.min(x2, x3));
        cameraMaxX = Math.max(Math.max(x0, x1), Math.max(x2, x3));

        cameraMinY = Math.min(Math.min(y0, y1), Math.min(y2, y3));
        cameraMaxY = Math.max(Math.max(y0, y1), Math.max(y2, y3));
    }

    @Override
    public void render(float delta) {
        handleRestartInput();

        if (!player.isDead()) {
            playerInputController.update(delta);
            enemyAIController.update(delta);
        }

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
            enemies,
            GameConfig.MAP_OFFSET_X,
            GameConfig.MAP_OFFSET_Y
        );
    }

    private void renderHud() {
        batch.setProjectionMatrix(hudCamera.combined);
        hudRenderer.render(player, enemies);
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

        worldCamera.position.x += (playerScreenX - worldCamera.position.x)
            * GameConfig.CAMERA_FOLLOW_SPEED * delta;

        worldCamera.position.y += (playerScreenY - worldCamera.position.y)
            * GameConfig.CAMERA_FOLLOW_SPEED * delta;

        clampWorldCamera();

        worldCamera.update();
    }

    private void clampWorldCamera() {
        worldCamera.position.x = Math.clamp(worldCamera.position.x,
            cameraMinX, cameraMaxX);

        worldCamera.position.y = Math.clamp(worldCamera.position.y,
            cameraMinY, cameraMaxY);
    }

    private void handleRestartInput() {
        if (!player.isDead()) {
            return;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            restartGame();
        }
    }

    private void restartGame() {
        player.setPosition(0, 0);
        player.restoreFullHp();

        enemies.clear();
        enemies.add(new Enemy("Skeleton", 5, 5, 30));
        enemies.add(new Enemy("Ghoul", 8, 3, 40));
        enemies.add(new Enemy("Cultist", 8, 10, 50));

        Gdx.app.log("Game", "Game restarted");
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
        gameAssets.dispose();
    }
}
