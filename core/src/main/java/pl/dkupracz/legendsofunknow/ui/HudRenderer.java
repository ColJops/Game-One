package pl.dkupracz.legendsofunknow.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pl.dkupracz.legendsofunknow.entities.Player;

public class HudRenderer {

    private final SpriteBatch batch;
    private final BitmapFont font;

    public HudRenderer(SpriteBatch batch, BitmapFont font) {
        this.batch = batch;
        this.font = font;
    }

    public void render(Player player) {
        batch.begin();

        font.getData().setScale(1.5f);
        font.draw(batch, "Legends of Unknow", 30, 460);

        font.getData().setScale(1f);
        font.draw(batch, "Checkpoint 15: player HP", 30, 435);
        font.draw(batch, "Player position: " + player.getMapX() + ", " + player.getMapY(), 30, 410);
        font.draw(batch, "HP: " + player.getCurrentHp() + " / " + player.getMaxHp(), 30, 385);
        font.draw(batch, "Move: W/A/S/D or arrows", 30, 360);
        font.draw(batch, "Debug: H damage, J heal", 30, 335);

        if (player.isDead()) {
            font.getData().setScale(1.4f);
            font.draw(batch, "PLAYER IS DEAD", 30, 300);
            font.getData().setScale(1f);
        }

        batch.end();
    }
}
