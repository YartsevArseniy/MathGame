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
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import math.game.MathGame;
import math.game.logic.MathTasks;
import math.game.objects.Background;
import math.game.objects.ButtonReal;
import math.game.objects.Player;

public class GameScreen implements Screen {

    final MathGame game;
    private Player player;
    private Background[] backgrounds;
    private float speedX = 0f, timeToUpdateSpeed = 0f, speed2 = 0.01f;
    private MathTasks mathTasks;
    private Texture score;
    private ButtonReal[] buttons;
    private Vector2 touchPos;
    private int cycleWrong = 0;
    private int record;


    public GameScreen(final MathGame game, int record){
        this.game = game;

        backgrounds = new Background[3];
        backgrounds[0] = new Background(new Texture("image/backgrounds/background1.png"), 0, 3);
        backgrounds[1] = new Background(new Texture("image/backgrounds/background2.png"), 10, 3);
        backgrounds[2] = new Background(new Texture("image/backgrounds/background3.png"), 20, 3);

        score = new Texture("image/score.png");

        player = new Player(new Texture("image/asd.jpg"), 1.75f, 1.75f, 4.15f, 8.5f);

        mathTasks = new MathTasks(2, -20, 20);

        buttons = new ButtonReal[4];
        for (int i = 0; i < 4; i++) {
            buttons[i] = new ButtonReal(new Texture("image/button2.png"), 2, 2, 0.375f*(i+1)+2*i, 2.5f);
            buttons[i].setText(String.valueOf(mathTasks.questions[i]));
        }

        touchPos = new Vector2();

        this.record = record;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.LIGHT_GRAY);
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        timeToUpdateSpeed+=delta;

        logic(delta);
        draw(delta);
    }

    public void logic(float delta){
        if (Gdx.input.isTouched()){
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            game.viewport.unproject(touchPos);
        }

        for (int i = 0; i < 3; i++){
            backgrounds[i].update(speedX, delta);
        }
        mathTasks.update(speedX, delta, player.getX());

        if(timeToUpdateSpeed > 15f){
            timeToUpdateSpeed = 0f;
            speedX+=10;
            /*
        for (int x : mathTasks.questions) {
            System.out.print(x + " ");
            }
        System.out.println(mathTasks.getCurrentAnswer());
             */
        }
        if(timeToUpdateSpeed > 3f && speedX < 1)
            speedX=speed2;

        for (int i = 0; i < 4; i++) {
            buttons[i].update(touchPos.x, touchPos.y, Gdx.input.justTouched());
            buttons[i].setText(String.valueOf(mathTasks.questions[i]));
        }
        for (int i = 0; i < 4; i++) {
            if(buttons[i].isJustPressed()){
                if(buttons[i].getText().equals(String.valueOf(mathTasks.getCurrentAnswer()))){
                    player.isSolved();
                    mathTasks.setTaskI(mathTasks.getTaskI()+1);
                    buttons[i].colorUpdate(true);
                    cycleWrong = 0;
                }
                else{
                    buttons[i].colorUpdate(false);
                    mathTasks.isWrong();
                    cycleWrong++;
                }
            }
        }

        if(cycleWrong >= 3 || player.isLose()){
            this.dispose();
            game.setScreen(new MainMenuScreen(game, Math.max(mathTasks.getTaskI()-2, record)));
        }
    }

    public void draw(float delta){
        game.batch.begin();
        for(int i = 0; i < 3; i++)
            backgrounds[i].draw(game.batch);
        player.update(speedX, delta, game.batch);

        mathTasks.draw(game.batch, game.font);

        game.batch.draw(score,2.5f, 16.5f, 5, 2.5f);
        game.font.draw(game.batch, String.valueOf(mathTasks.getTaskI()-2), 4.5f, 18);
        for (int i = 0; i < 4; i++) {
            buttons[i].draw(game.batch, game.font);
        }

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width,height, true);
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
        player.dispose();
        for (Background x : backgrounds) {
            x.dispose();
        }
        score.dispose();
        for (int i = 0; i < 4; i++) {
            buttons[i].dispose();
        }
    }
}
