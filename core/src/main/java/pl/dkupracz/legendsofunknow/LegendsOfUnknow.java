package pl.dkupracz.legendsofunknow;

import com.badlogic.gdx.Game;
import pl.dkupracz.legendsofunknow.screens.GameScreen;

public class LegendsOfUnknow extends Game {

    @Override
    public void create() {
        setScreen(new GameScreen());
    }
}
