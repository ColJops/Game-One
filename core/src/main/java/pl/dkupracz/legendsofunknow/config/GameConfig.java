package pl.dkupracz.legendsofunknow.config;

public final class GameConfig {

    public static final int TILE_WIDTH = 64;
    public static final int TILE_HEIGHT = 32;

    public static final int MAP_WIDTH = 20;
    public static final int MAP_HEIGHT = 200;

    public static final float MAP_OFFSET_X = 320f;
    public static final float MAP_OFFSET_Y = 120f;

    public static final float PLAYER_MOVE_COOLDOWN = 0.15f;
    public static final int PLAYER_ATTACK_DAMAGE = 10;
    public static final float PLAYER_ATTACK_COOLDOWN = 0.3f;

    public static final int ENEMY_ATTACK_DAMAGE = 5;
    public static final float ENEMY_MOVE_COOLDOWN = 0.8f;

    public static final float WORLD_WIDTH = 800f;
    public static final float WORLD_HEIGHT = 480f;

    public static final float CAMERA_FOLLOW_SPEED = 6f;

    private GameConfig() {
        // Utility class
    }
}
