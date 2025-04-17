package math.game.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class MathTasks {
    public String[] tasks; // задачи
    public float[] xTasks; // где они находтся
    public int[] taskAnswer; // ответы на все задачи
    public int[] questions; // вопросы (один верный)
    private int[] indexWrongQuestions;

    private int taskI; // индекс задачи на которой стоит игрок (чтобы найти индекс нужно !!!taski%10!!)
    private int l, r; // границы чисел

    public MathTasks(int taskI, int l, int r){
        tasks = new String[10];
        xTasks  = new float[10];
        taskAnswer = new int[10];
        indexWrongQuestions = new int[4];

        this.l = l;
        this.r = r;

        for(int i = 0; i < 10; i++){
            xTasks[i] = i*2;
            tasks[i] = createTask(i);
        }
        this.taskI = taskI;

        questions = new int[4];
        updateQuestions(taskAnswer[taskI]);
    }

    public void update(float speedX, float delta, float playerX){
        for(int i = 0; i < 10; i++){
            if(xTasks[i] < playerX-1){
                tasks[i] = "";
            }
            xTasks[i]-=speedX*delta;
            if(xTasks[i] <= -9.9){
                xTasks[i] = 10.1f;
                tasks[i] = createTask(i);
            }
        }
    }

    public void draw(SpriteBatch batch, BitmapFont font){
        for(int i = 0; i < 10; i++){
            font.draw(batch, tasks[i], xTasks[i]+0.01f, 7.6f);
        }
    }

    public void isWrong(){
        tasks[taskI%10] = createTask(taskI);
        updateQuestions(getCurrentAnswer());
    }

    public void updateQuestions(int trueAnswer){
        int posTrueAnswer = MathUtils.random(0,4-1);

        for(int i = 0; i < 4; i++){
            questions[i] = createWrongQuestions(trueAnswer, MathUtils.random(0, 100));
        }
        for (int i = 0; i < 4; i++) {
            indexWrongQuestions[i] = 0;
        }
        questions[posTrueAnswer] = trueAnswer;
    }

    private int createWrongQuestions(int trueAns, int index){
        int a = trueAns;
        index = index%4;
        if(indexWrongQuestions[index] == 1){
            for(int i = 0; i < 4; i++){
                if(indexWrongQuestions[i] == 0){
                    index = i;
                    break;
                }
            }
        }
        if(index == 0)
            a+=MathUtils.random(1, 10);
        if(index == 1)
            a-=MathUtils.random(1,10);
        if(index == 2)
            a+=MathUtils.random(11, 20);
        if(index == 3)
            a-=MathUtils.random(11, 20);
        indexWrongQuestions[index] = 1;
        return a;
    }

    public String createTask(int posTask) {
        String s = "";

        int a = MathUtils.random(l, r), b = MathUtils.random(l, r), c = MathUtils.random(l, r);
        int x = 1000000;
        int typeTask = MathUtils.random(0, 4);

        if (typeTask == 0) { // a+b
          s += String.valueOf(a);
          s += numInStr(b, 0);

          x = a+b;
        }
        else if (typeTask == 1) { // a*b+c
            s += String.valueOf(a) + '*';
            if(b < 0)
                s+=numInStr(b,1);
            else
                s+=String.valueOf(b);
            s+=numInStr(c,0);

            x = a*b+c;
        }
        else if (typeTask == 2) { // c*(a+b)
            s += String.valueOf(c) + "*("+String.valueOf(a)+numInStr(b, 0)+")";

            x = (a+b)*c;
        }
        else if (typeTask == 3) { // a*b
            s += String.valueOf(a)+'*';
            if(b < 0)
                s+=numInStr(b,1);
            else
                s+=String.valueOf(b);
            x = a*b;
        }
        else { // a+b+c
            s += String.valueOf(a) + numInStr(b, 0) + numInStr(c, 0);

            x = a+b+c;
        }
        taskAnswer[posTask%10] = x;
        if(s.length() < 10)
            s = " " + s;

        return s;
    }

    public int getCurrentAnswer(){
        return taskAnswer[taskI%10];
    }

    private String numInStr(int a, int skoboch){
        if(a < 0 && skoboch == 0)
            return String.valueOf(a);
        if(a < 0)
            return '('+String.valueOf(a)+')';
        return '+'+String.valueOf(a);
    }


    public int getTaskI(){
        return taskI;
    }
    public void setTaskI(int taskI){
        this.taskI = taskI;
        updateQuestions(taskAnswer[taskI%10]);
    }
}
