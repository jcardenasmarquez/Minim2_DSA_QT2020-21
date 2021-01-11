package com.jcardenas.example.minim2_qt2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DURATION = 3000;
    private SharedPreferences preferences;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        loadPreferences();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                check();
            }
        }, SPLASH_DURATION);
    }

    private void check(){
        username = preferences.getString("userLogin",null);
        if (username!=null){
            goInicioWithUser();
        }
        else{
            goInicioNormal();
        }
    }

    private void loadPreferences(){
        preferences = getSharedPreferences("username", Context.MODE_PRIVATE);
    }

    private void goInicioWithUser(){
        Intent intent = new Intent(getApplicationContext(), InicioActivity.class);
        intent.putExtra("preference", username);
        startActivity(intent);
    }

    private void goInicioNormal(){
        Intent intent = new Intent(getApplicationContext(), InicioActivity.class);
        startActivity(intent);
    }
}