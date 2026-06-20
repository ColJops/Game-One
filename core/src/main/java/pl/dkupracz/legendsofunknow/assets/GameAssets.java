package pl.dkupracz.legendsofunknow.assets;

import com.badlogic.gdx.graphics.Texture;

public class GameAssets {

    private final Texture playerTexture;
    private final Texture enemySkeletonTexture;

    public GameAssets() {
        this.playerTexture = new Texture("characters/player.png");
        this.enemySkeletonTexture = new Texture("characters/enemy_skeleton.png");
    }

    public Texture getPlayerTexture() {
        return playerTexture;
    }

    public Texture getEnemySkeletonTexture() {
        return enemySkeletonTexture;
    }

    public void dispose() {
        playerTexture.dispose();
        enemySkeletonTexture.dispose();
    }
}
