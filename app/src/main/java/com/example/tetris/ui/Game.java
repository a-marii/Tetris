package com.example.tetris.ui;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tetris.R;


public class Game extends AppCompatActivity implements View.OnClickListener {
    Intent i;
    GameView gameView;
    public static int width;
    public static int height;
    private static Game instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
        setContentView(R.layout.activity_game);
        gameView =  findViewById(R.id.gameView);

    }
    public static Game getInstance() {
        return instance;
    }

    public void Message() {
        Button right =  findViewById(R.id.right);
        right.setClickable(false);
        Button down =  findViewById(R.id.down);
        down.setClickable(false);
        Button left =  findViewById(R.id.left);
        left.setClickable(false);
        Button turn =  findViewById(R.id.turn);
        turn.setClickable(false);
        Toast toast=Toast.makeText(Game.this, "Игра окончена.\nВы набрали "+ GameView.getScore()+" баллов", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.left)
                gameView.left();
        else if(view.getId()==R.id.right)
                gameView.right();
        else if(view.getId()==R.id.turn)
                gameView.turn();
        else if(view.getId()==R.id.down)
                gameView.down();
        else if(view.getId()==R.id.exit) {
            GameView.setScore(0);
            gameView.t.cancel();
            i = new Intent(Game.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);

        }
        else if(view.getId()==R.id.retry) {
            GameView.setScore(0);
            gameView.t.cancel();
            i = new Intent(Game.this, Game.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);

        }
        gameView.invalidate();
    }



}
