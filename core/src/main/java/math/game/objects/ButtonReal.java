package math.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class ButtonReal {
    private Rectangle button;
    private Texture textureButton;
    private String str;
    private boolean isPressed;
    private Color color;
    private float textX = 0f;

    public ButtonReal(Texture textureButton, float width, float height, float x, float y){
        this.textureButton = textureButton;

        button =  new Rectangle(x, y, width, height);

        isPressed = false;
        str = "asd";

        color = Color.GREEN;
    }

    public void update(float xMouse, float yMouse, boolean isTouched){

        if(button.contains(xMouse, yMouse)){
            if (isTouched)
                isPressed = true;
            else
                isPressed = false;
        }
        else{
            isPressed = false;
        }
    }

    public void draw(SpriteBatch batch, BitmapFont font){
        if(isPressed)
            batch.setColor(color);
        batch.draw(textureButton, button.x, button.y, button.width, button.height);
        batch.setColor(color);
        font.draw(batch, str, button.x+textX, button.y+button.height/2);
        batch.setColor(Color.WHITE);
    }

    public void setText(String s){
        textX = (button.width*5/2-(s.length()/2+s.length()%2-1))*0.2f-0.2f;
        //textX = 0;
        str = s;
    }
    public String getText(){
        return str;
    }

    public void colorUpdate(boolean isCorrect){
        if(isCorrect)
            color = Color.GREEN;
        else
            color = Color.RED;
    }

    public boolean isJustPressed(){
        return isPressed;
    }

    public void dispose(){
        textureButton.dispose();
    }
}
