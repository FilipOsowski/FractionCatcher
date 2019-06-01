package com.example.rc211.fractioncatcher;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    PlayerBlock lesserBlock;
    PlayerBlock greaterBlock;

    FractionBlock playerFractionBlock;
    Runnable createFractionBlock;
    final Handler handler = new Handler();
    ArrayList<FractionBlock> activeFractionBlocks = new ArrayList<>();

    TextView scoreTextView;

    ArrayList<PlayerBlock> playerBlocks = new ArrayList<>();
    float lastPosition = 0;
    final static int SCREENWIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;

    int delay = 5000;

    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ConstraintLayout constraintLayout = findViewById(R.id.myLayout);

        scoreTextView = new TextView(this);
        scoreTextView.setBackgroundColor(Color.GRAY);
        scoreTextView.setText("Score: " + score);
        scoreTextView.setTextSize(30);
        scoreTextView.setHeight(150);
        scoreTextView.setGravity(Gravity.CENTER);
        scoreTextView.setTypeface(Typeface.DEFAULT_BOLD);
        scoreTextView.setY(Resources.getSystem().getDisplayMetrics().heightPixels - 245);
        constraintLayout.addView(scoreTextView);

        lesserBlock = new PlayerBlock(this);
        lesserBlock.setX((float) (Resources.getSystem().getDisplayMetrics().widthPixels/2 - PlayerBlock.width - FractionBlock.width/2 - 2));
        lesserBlock.setY((float) (Resources.getSystem().getDisplayMetrics().heightPixels * 0.75));
        //lesserBlock.setBackgroundColor(Color.rgb(255, 89, 100));
		lesserBlock.setBackgroundResource(R.drawable.red_bucket);
        constraintLayout.addView(lesserBlock);

        greaterBlock = new PlayerBlock(this);
        greaterBlock.role = 1;
        greaterBlock.setX((float) (Resources.getSystem().getDisplayMetrics().widthPixels/2 + FractionBlock.width/2 + 2));
        greaterBlock.setY((float) (Resources.getSystem().getDisplayMetrics().heightPixels * 0.75));
        //greaterBlock.setBackgroundColor(Color.rgb(51, 153, 137));
		greaterBlock.setBackgroundResource(R.drawable.green_bucket);
        constraintLayout.addView(greaterBlock);

        playerBlocks.add(lesserBlock);
        playerBlocks.add(greaterBlock);

        playerFractionBlock = new FractionBlock(this, playerBlocks);
        playerFractionBlock.speed = 0;
        playerFractionBlock.runnable = null;
        playerFractionBlock.setX((lesserBlock.getX() + (greaterBlock.getX() + PlayerBlock.width))/2 - FractionBlock.width/2);
        playerFractionBlock.setY((float) (Resources.getSystem().getDisplayMetrics().heightPixels * 0.735));
        playerFractionBlock.numerator.setBackgroundColor(Color.rgb(250,250,250));
        playerFractionBlock.numerator.setText("3");
        playerFractionBlock.denominator.setBackgroundColor(Color.rgb(250,250,250));
        playerFractionBlock.denominator.setText("2");
        constraintLayout.addView(playerFractionBlock);

        createFractionBlock = new Runnable() {
            @Override
            public void run() {
                FractionBlock fractionBlock = new FractionBlock(GameActivity.this, playerBlocks);
                activeFractionBlocks.add(fractionBlock);
                constraintLayout.addView(fractionBlock);
                handler.postDelayed(this, delay);
                if (delay > 2000) {
                    delay -= 75;
                }
            }
        };

        handler.postDelayed(createFractionBlock, 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        moveLesserBlock(event);
        greaterBlock.setX(lesserBlock.getX() + PlayerBlock.width + FractionBlock.width + 4);
        playerFractionBlock.setX((lesserBlock.getX() + (greaterBlock.getX() + PlayerBlock.width))/2 - FractionBlock.width/2);
        return super.onTouchEvent(event);
    }

    public void moveLesserBlock(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            lastPosition = motionEvent.getX();
        }
        else {
            float offset = motionEvent.getX() - lastPosition;
            float futurePosition = lesserBlock.getX() + offset;
            if (futurePosition < 0) {
                futurePosition = 0;
            }
            else if (futurePosition + 2 * PlayerBlock.width + FractionBlock.width + 4 > SCREENWIDTH) {
                futurePosition = SCREENWIDTH - 2 * PlayerBlock.width - FractionBlock.width - 4;
            }

            lesserBlock.setX(futurePosition);
            lastPosition = motionEvent.getX();
        }
    }

    public void endGameActivity(String fraction, String decimal, String condition) {
        Context context = getBaseContext();
        Intent gameOverIntent = new Intent(context, GameOverActivity.class);
        gameOverIntent.putExtra("condition", condition);
        gameOverIntent.putExtra("fraction", fraction);
        gameOverIntent.putExtra("decimal", decimal);
        gameOverIntent.putExtra("score", score);
        gameOverIntent.putExtra("name", (String) getIntent().getExtras().get("name"));
        context.startActivity(gameOverIntent);
        this.finish();
    }


    public void updateScore(int amount) {
        score += amount;
        scoreTextView.setText("Score: " + score);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (FractionBlock fB : activeFractionBlocks) {
            fB.handler.removeCallbacks(fB.runnable);
            ((ViewGroup) fB.getParent()).removeView(fB);
        }
        handler.removeCallbacks(createFractionBlock);
    }
}
