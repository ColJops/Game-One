package pl.dkupracz.legendsofunknow;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class LegendsOfUnknow extends ApplicationAdapter {

    @Override
    public void create() {
        Gdx.app.log("LegendsOfUnknow", "Game created and started");
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.03f, 0.025f, 0.04f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void dispose() {
        Gdx.app.log("LegendsOfUnknow", "Game disposed");
    }
}
