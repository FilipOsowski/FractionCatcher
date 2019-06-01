package com.example.rc211.fractioncatcher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class HowToPlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_play);

        ImageView tutorialImageView = findViewById(R.id.tutorialImageView);
        Glide.with(this).load(R.drawable.tutorial).into(tutorialImageView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("ON PAUSE");
        finish();
    }
}
