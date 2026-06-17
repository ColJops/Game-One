package pl.dkupracz.legendsofunknow.entities;

import pl.dkupracz.legendsofunknow.world.GameMap;

public class Player {

    private int mapX;
    private int mapY;

    public Player(int mapX, int mapY) {
        this.mapX = mapX;
        this.mapY = mapY;
    }

    public void moveBy(int dx, int dy, GameMap gameMap) {
        int newX = mapX + dx;
        int newY = mapY + dy;

        if (gameMap.isWalkable(newX, newY)) {
            mapX = newX;
            mapY = newY;
        }
    }

    public int getMapX() {
        return mapX;
    }

    public int getMapY() {
        return mapY;
    }

    public void setPosition(int mapX, int mapY) {
        this.mapX = mapX;
        this.mapY = mapY;
    }
}
