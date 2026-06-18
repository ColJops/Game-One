package pl.dkupracz.legendsofunknow.entities;

import pl.dkupracz.legendsofunknow.world.GameMap;

public class Player {

    private int mapX;
    private int mapY;

    private int maxHp;
    private int currentHp;

    public Player(int mapX, int mapY) {
        this.mapX = mapX;
        this.mapY = mapY;
        this.maxHp = 100;
        this.currentHp = maxHp;
    }

    public void moveBy(int dx, int dy, GameMap gameMap) {
        int newX = mapX + dx;
        int newY = mapY + dy;

        if (gameMap.isWalkable(newX, newY)) {
            mapX = newX;
            mapY = newY;
        }
    }

    public void takeDamage(int amount) {
        currentHp -= amount;

        if (currentHp < 0) {
            currentHp = 0;
        }
    }

    public void heal(int amount) {
        currentHp += amount;

        if (currentHp > maxHp) {
            currentHp = maxHp;
        }
    }

    public boolean isDead() {
        return currentHp <= 0;
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

    public int getMaxHp() {
        return maxHp;
    }

    public int getCurrentHp() {
        return currentHp;
    }
}
