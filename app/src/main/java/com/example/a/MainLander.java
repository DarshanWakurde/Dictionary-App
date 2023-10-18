package com.example.a;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

public class MainLander extends AppCompatActivity {


    CardView english,translate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lander);


        english=findViewById(R.id.card_learn_english);
        translate=findViewById(R.id.card_translate);
        applySlideInFromLeftAnimation(english);
        applySlideInFromRightAnimation(translate);

        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainLander.this,MainActivity.class));
                Toast.makeText(MainLander.this, "English", Toast.LENGTH_SHORT).show();
            }
        });

        translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainLander.this,Translator.class));
                Toast.makeText(MainLander.this, "Translator", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void applySlideInFromLeftAnimation(View view) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.card);

        view.startAnimation(animation);
    }

    private void applySlideInFromRightAnimation(View view) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.card2);

        view.startAnimation(animation);


    }
}