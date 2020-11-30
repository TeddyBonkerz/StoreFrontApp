package com.example.storefrontapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class splash_screen extends AppCompatActivity {

    private static int SPLASH_SCREEN = 3000;
    Animation pageAnim;
    TextView logo, motto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //disables the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        //Animations
        pageAnim = AnimationUtils.loadAnimation(this, R.anim.splash_anim);

        //Hook animation to image or textview
        logo = findViewById(R.id.store);
        motto = findViewById(R.id.store_motto);

        logo.setAnimation(pageAnim);
        motto.setAnimation(pageAnim);

        //POSTING THE NEXT ACTIVITY AFTER SPLASH SCREEN
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(splash_screen.this, login.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);
    }
}