package pl.dkupracz.legendsofunknow.entities;

public class Player {

    private int mapX;
    private int mapY;

    public Player(int mapX, int mapY) {
        this.mapX = mapX;
        this.mapY = mapY;
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
