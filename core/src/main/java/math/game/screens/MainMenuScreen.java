package math.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import math.game.MathGame;
import math.game.objects.ButtonReal;

public class MainMenuScreen implements Screen {

    final MathGame game;
    private Texture background;
    private ButtonReal button1;
    private ButtonReal button2;
    private Vector2 touchPos;
    private int record;


    public MainMenuScreen(final MathGame game, int record){
        this.game = game;
        background = new Texture("image/backgrounds/menubackground.png");

        button1 = new ButtonReal(new Texture("image/button1.png"), 4, 2, 3f, 11f, game.glyphLayout, game.font);
        button1.setText("PLAY");

        button2 = new ButtonReal(new Texture("image/button1.png"), 3, 1.5f, 3.5f, 2, game.glyphLayout, game.font);
        button2.setText("EXIT");

        touchPos = new Vector2();

        this.record = record;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        if (Gdx.input.isTouched()){
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            game.viewport.unproject(touchPos);
        }

        button1.update(touchPos.x, touchPos.y, Gdx.input.justTouched());
        button2.update(touchPos.x, touchPos.y, Gdx.input.justTouched());

        game.batch.begin();

        //game.font.draw(game.batch, "asd", 5, 1.5f);
        //game.font.draw(game.batch, "asd", 1, 1);
        game.batch.draw(background,0,0,10,20);

        game.font.getData().setScale(0.02f);
        button1.draw(game.batch, game.font);
        button2.draw(game.batch, game.font);
        game.font.getData().setScale(0.0105f);

        game.font.draw(game.batch, "RECORD: " + String.valueOf(record), 4, 19);

        game.batch.end();

        if(button1.isJustPressed()){
            dispose();
            game.setScreen(new GameScreen(game, record));
        }
        if(button2.isJustPressed()){
            dispose();
            Gdx.app.exit();
        }
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        background.dispose();
        button1.dispose();
        button2.dispose();
    }
}
