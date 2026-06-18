package pl.dkupracz.legendsofunknow.world;

public class GameMap {

    private final int width;
    private final int height;
    private final TileType[][] tiles;

    public GameMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new TileType[height][width];

        generateTestMap();
    }

    private void generateTestMap() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[y][x] = TileType.FLOOR;
            }
        }

        // Kilka testowych blokad/ścian
        tiles[10][10] = TileType.BLOCKED;
        tiles[10][11] = TileType.BLOCKED;
        tiles[10][12] = TileType.BLOCKED;
        tiles[11][12] = TileType.BLOCKED;
        tiles[12][12] = TileType.BLOCKED;

        tiles[15][4] = TileType.BLOCKED;
        tiles[16][4] = TileType.BLOCKED;
        tiles[17][4] = TileType.BLOCKED;

        // Punkt startowy gracza na przyszłość
        tiles[0][0] = TileType.SPAWN;
    }

    public TileType getTileType(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return TileType.BLOCKED;
        }

        return tiles[y][x];
    }

    public boolean isWalkable(int x, int y) {
        return getTileType(x, y).isWalkable();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
