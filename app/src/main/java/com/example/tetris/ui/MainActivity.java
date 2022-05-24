package com.example.tetris.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tetris.R;
import com.example.tetris.data.AppDatabase;
import com.example.tetris.data.UserDao;
import com.example.tetris.user.User;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText ETlogin;
    private EditText ETpassword;
    static UserDao userDao;
    private static int score=0;
    static User new_user;
    public static int getScore() {
        return score;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ETlogin = findViewById(R.id.etLogin);
        ETpassword = findViewById(R.id.etPassword);
        AppDatabase db =  Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database")
                .allowMainThreadQueries()
                .build();
        userDao = db.userDao();
    }
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.enter) {
            User user = userDao.getByLogin(ETlogin.getText().toString());
            System.out.println("/"+ETlogin.getText().toString()+"/");

            if(ETlogin.getText().toString().equals("")||ETpassword.getText().toString().equals("")){
                Toast.makeText(MainActivity.this, "Введите имя пользователя и пароль", Toast.LENGTH_SHORT).show();
            }
            else if (user==null) {
                Toast.makeText(MainActivity.this, "Пользователь не найден", Toast.LENGTH_SHORT).show();
            } else if (user.getPassword().equals(ETpassword.getText().toString())) {
                Toast.makeText(MainActivity.this, "Авторизация успешно пройдена", Toast.LENGTH_SHORT).show();
                new_user=userDao.getByLogin(ETlogin.getText().toString());
                score=user.getScore();
                Intent intent=new Intent("com.example.tetris.ui.Game");
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "Проверьте введенный пароль", Toast.LENGTH_SHORT).show();
                List<User> users= userDao.getAll();
                for(int i=0; i<users.size(); ++i){
                    System.out.println(users.get(i).getId()+" "+ users.get(i).getPassword()+" "+ users.get(i).getLogin()+" "+ users.get(i).getScore());
                }
            }

        }
        else if (view.getId() == R.id.checkin) {
            User user =userDao.getByLogin(ETlogin.getText().toString());
            if(ETlogin.getText().toString().equals("")||ETpassword.getText().toString().equals("")){
                Toast.makeText(MainActivity.this, "Введите имя пользователя и пароль", Toast.LENGTH_SHORT).show();
            }
            else if(user==null){
                userDao.insert(new User(ETlogin.getText().toString(),ETpassword.getText().toString()));
                new_user=userDao.getByLogin(ETlogin.getText().toString());
                Toast.makeText(MainActivity.this, "Пользователь зарегистрирован", Toast.LENGTH_SHORT).show();
                score=0;
                Intent intent=new Intent("com.example.tetris.ui.Game");
                startActivity(intent);
            }
            else {
                Toast.makeText(MainActivity.this, "Пользователь уже существует", Toast.LENGTH_SHORT).show();
            }

            System.out.println(user);
        }

    }
    public static void update_result(int new_score){
        score=new_score;
        new_user.setScore(new_score);
        userDao.update(new_user);
    }

}