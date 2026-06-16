package pl.dkupracz.legendsofunknow.world;

public class GameMap {

    private final int width;
    private final int height;

    public GameMap(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
