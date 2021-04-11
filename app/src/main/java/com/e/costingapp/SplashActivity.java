package com.e.costingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    private static  int SPLASH_SCREEN =3000;

    Animation top_anm, bottom_anm;
    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        //Animation
        top_anm = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottom_anm = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        imageView =findViewById(R.id.logo);
        textView =findViewById(R.id.textView);

        imageView.setAnimation(top_anm);
        textView.setAnimation(bottom_anm);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, Project_Home.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);
    }
}