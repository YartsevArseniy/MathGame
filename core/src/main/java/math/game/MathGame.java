package math.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import math.game.screens.MainMenuScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MathGame extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    public FitViewport viewport;
    private  float scale;
    private Texture fontTexture;

    @Override
    public void create() {
        fontTexture = new Texture(Gdx.files.internal("font.png"), true);
        fontTexture.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.Linear);

        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("font.fnt"), new TextureRegion(fontTexture), false);
        viewport = new FitViewport(10, 20);

        scale = Gdx.graphics.getHeight()/Gdx.graphics.getWidth() >= 2
            ? viewport.getWorldWidth()/Gdx.graphics.getWidth()
            : viewport.getWorldHeight()/Gdx.graphics.getHeight();

        font.setUseIntegerPositions(false);

        font.getData().setScale(0.0105f);

        this.setScreen(new MainMenuScreen(this, 0));
    }
    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
