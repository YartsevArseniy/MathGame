package math.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Background {
    private final Texture background;
    float x;
    int count; // количество фонов

    public Background(Texture background, float x, int count){
        this.background = background;
        this.x = x;
        this.count = count;
    }

    public void update(float speedX, float delta){
        x-=speedX*delta;
        if(x <= -10)
            x = 10*(count-1);
    }


    public void draw(SpriteBatch batch){
        batch.draw(background, x, 0, 10.1f, 20);
    }

    public void dispose(){
        background.dispose();
    }
}
