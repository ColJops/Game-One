package pl.dkupracz.legendsofunknow.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import pl.dkupracz.legendsofunknow.world.GameMap;

public class IsometricRenderer {

    private static final int TILE_WIDTH = 64;
    private static final int TILE_HEIGHT = 32;

    private final ShapeRenderer shapeRenderer;

    public IsometricRenderer() {
        this.shapeRenderer = new ShapeRenderer();
    }

    public void render(GameMap map, float offsetX, float offsetY) {

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                drawTile(x, y, offsetX, offsetY);
            }
        }

        shapeRenderer.end();

    }

    private void drawTile(int mapX, int mapY, float offsetX, float offsetY) {

        float screenX = (mapX - mapY) * TILE_WIDTH / 2f + offsetX;
        float screenY = (mapY - mapX) * TILE_HEIGHT / 2f + offsetY;

        shapeRenderer.setColor(Color.GRAY);

        float topX = screenX;
        float topY = screenY + TILE_HEIGHT / 2f;

        float bottomX = screenX;
        float bottomY = screenY - TILE_HEIGHT / 2f;

        float rightX = screenX + TILE_WIDTH / 2f;
        float rightY = screenY;

        float leftX = screenX - TILE_WIDTH / 2f;
        float leftY = screenY;

        shapeRenderer.line(topX, topY, rightX, rightY);
        shapeRenderer.line(rightX, rightY, bottomX, bottomY);
        shapeRenderer.line(bottomX, bottomY, leftX, leftY);
        shapeRenderer.line(leftX, leftY, rightX, rightY);
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}
