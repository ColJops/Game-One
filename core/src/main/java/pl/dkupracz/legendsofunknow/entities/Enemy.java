package pl.dkupracz.legendsofunknow.entities;

public class Enemy {

    private final String name;

    private int mapX;
    private int mapY;

    private int maxHp;
    private int currentHp;

    public Enemy(String name, int mapX, int mapY, int maxHp) {
        this.name = name;
        this.mapX = mapX;
        this.mapY = mapY;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
    }

    public String getName() {
        return name;
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

    public boolean isDead() {
        return currentHp <= 0;
    }

    public void takeDamage(int amount) {
        currentHp -= amount;

        if (currentHp < 0) {
            currentHp = 0;
        }
    }
}
