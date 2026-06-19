package pl.dkupracz.legendsofunknow.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import java.util.List;

import pl.dkupracz.legendsofunknow.entities.Enemy;
import pl.dkupracz.legendsofunknow.entities.Player;
import pl.dkupracz.legendsofunknow.world.GameMap;
import pl.dkupracz.legendsofunknow.world.TileType;
import pl.dkupracz.legendsofunknow.config.GameConfig;



public class IsometricRenderer {

    private final ShapeRenderer shapeRenderer;

    public IsometricRenderer() {
        this.shapeRenderer = new ShapeRenderer();
    }

    public void render(GameMap map, Player player, List<Enemy> enemies, float offsetX, float offsetY) {
        renderFilledTiles(map, offsetX, offsetY);
        renderTileOutlines(map, offsetX, offsetY);
        renderEnemies(enemies, offsetX, offsetY);
        renderPlayer(player, offsetX, offsetY);
    }

    private void renderEnemies(List<Enemy> enemies, float offsetX, float offsetY) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (Enemy enemy : enemies) {
            if (!enemy.isDead()) {
                drawEnemy(enemy, offsetX, offsetY);
            }
        }

        shapeRenderer.end();
    }

    private void drawEnemy(Enemy enemy, float offsetX, float offsetY) {
        float screenX = toScreenX(enemy.getMapX(), enemy.getMapY(), offsetX);
        float screenY = toScreenY(enemy.getMapX(), enemy.getMapY(), offsetY);

        shapeRenderer.setColor(new Color(0.65f, 0.10f, 0.10f, 1f));
        shapeRenderer.circle(screenX, screenY + 12, 8);

        shapeRenderer.setColor(new Color(0.25f, 0.04f, 0.04f, 1f));
        shapeRenderer.rect(screenX - 5, screenY - 2, 10, 14);
    }

    private void renderFilledTiles(GameMap map, float offsetX, float offsetY) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                drawFilledTile(x, y, map.getTileType(x, y), offsetX, offsetY);
            }
        }

        shapeRenderer.end();
    }

    private void renderTileOutlines(GameMap map, float offsetX, float offsetY) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                drawTileOutline(x, y, offsetX, offsetY);
            }
        }

        shapeRenderer.end();
    }

    private void renderPlayer(Player player, float offsetX, float offsetY) {
        float screenX = toScreenX(player.getMapX(), player.getMapY(), offsetX);
        float screenY = toScreenY(player.getMapX(), player.getMapY(), offsetY);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(new Color(0.85f, 0.78f, 0.45f, 1f));
        shapeRenderer.circle(screenX, screenY + 12, 8);

        shapeRenderer.setColor(new Color(0.35f, 0.28f, 0.18f, 1f));
        shapeRenderer.rect(screenX - 5, screenY - 2, 10, 14);

        shapeRenderer.end();
    }

    private void drawFilledTile(int mapX, int mapY, TileType tileType, float offsetX, float offsetY) {
        float[] points = calculateTilePoints(mapX, mapY, offsetX, offsetY);

        shapeRenderer.setColor(getTileColor(tileType));

        shapeRenderer.triangle(
            points[0], points[1],
            points[2], points[3],
            points[4], points[5]
        );

        shapeRenderer.triangle(
            points[0], points[1],
            points[4], points[5],
            points[6], points[7]
        );
    }

    private void drawTileOutline(int mapX, int mapY, float offsetX, float offsetY) {
        float[] points = calculateTilePoints(mapX, mapY, offsetX, offsetY);

        shapeRenderer.setColor(Color.GRAY);

        shapeRenderer.line(points[0], points[1], points[2], points[3]);
        shapeRenderer.line(points[2], points[3], points[4], points[5]);
        shapeRenderer.line(points[4], points[5], points[6], points[7]);
        shapeRenderer.line(points[6], points[7], points[0], points[1]);
    }

    private float[] calculateTilePoints(int mapX, int mapY, float offsetX, float offsetY) {
        float centerX = toScreenX(mapX, mapY, offsetX);
        float centerY = toScreenY(mapX, mapY, offsetY);

        float topY = centerY + GameConfig.TILE_HEIGHT / 2f;
        float rightX = centerX + GameConfig.TILE_WIDTH / 2f;
        float bottomY = centerY - GameConfig.TILE_HEIGHT / 2f;
        float leftX = centerX - GameConfig.TILE_WIDTH / 2f;

        return new float[]{
            centerX, topY,
            rightX, centerY,
            centerX, bottomY,
            leftX, centerY
        };
    }

    private float toScreenX(int mapX, int mapY, float offsetX) {
        return (mapX - mapY) * GameConfig.TILE_WIDTH / 2f + offsetX;
    }

    private float toScreenY(int mapX, int mapY, float offsetY) {
        return (mapX + mapY) * GameConfig.TILE_HEIGHT / 2f + offsetY;
    }

    private Color getTileColor(TileType tileType) {
        return switch (tileType) {
            case FLOOR -> new Color(0.10f, 0.10f, 0.12f, 1f);
            case BLOCKED -> new Color(0.22f, 0.08f, 0.08f, 1f);
            case SPAWN -> new Color(0.08f, 0.18f, 0.10f, 1f);
        };
    }

    public float getScreenX(int mapX, int mapY, float offsetX) {
        return toScreenX(mapX, mapY, offsetX);
    }

    public float getScreenY(int mapX, int mapY, float offsetY) {
        return toScreenY(mapX, mapY, offsetY);
    }

    public void setProjectionMatrix(Matrix4 projectionMatrix) {
        shapeRenderer.setProjectionMatrix(projectionMatrix);
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}
