package com.example.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splashactivity extends AppCompatActivity {
    ImageView logo;
    TextView slogan, title;
    Animation topanimation, leftanimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_activity);
        logo = (ImageView) findViewById(R.id.logo);
        slogan = (TextView) findViewById(R.id.slogan);
        title = (TextView) findViewById(R.id.title);

        topanimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);


        logo.setAnimation(topanimation);


        int time = 4300;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Splashactivity.this, LoginActivity.class));
                finish();
                ;

            }
        }, time);

    }
}