package com.example.a;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {

    ImageView splashImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        splashImageView = findViewById(R.id.logoimg);

        // Load the animation resource
        Animation fadeAnimation = AnimationUtils.loadAnimation(this, R.anim.anim);

        // Apply the animation
        splashImageView.startAnimation(fadeAnimation);
        splashImageView.setVisibility(View.VISIBLE);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, ActivityRegister.class));
                finish();
            }
        },2500);
    }
}