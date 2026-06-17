package pl.dkupracz.legendsofunknow.world;

public enum TileType {
    FLOOR(true),
    BLOCKED(false),
    SPAWN(true);

    private final boolean walkable;

    TileType(boolean walkable) {
        this.walkable = walkable;
    }

    public boolean isWalkable() {
        return walkable;
    }
}
