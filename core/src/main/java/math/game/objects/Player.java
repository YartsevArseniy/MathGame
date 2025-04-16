package math.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
    private final Sprite player;

    public Player(Texture texture, float width, float height, float x, float y){
        this.player = new Sprite(texture);
        player.setSize(width, height);
        player.setPosition(x, y);
    }

    public void update(float speedX, float delta, SpriteBatch batch){
        player.setX(player.getX()-speedX*delta);
        draw(batch);
    }

    public float getX(){
        return player.getX();
    }

    public boolean isLose(){
        return player.getX() < -player.getWidth();
    }

    // если задача решена
    public void isSolved(){
        player.setX(player.getX()+2f);
    }

    public void draw(SpriteBatch batch){
        player.draw(batch);
    }

    public void dispose(){
        player.getTexture().dispose();
    }
}
