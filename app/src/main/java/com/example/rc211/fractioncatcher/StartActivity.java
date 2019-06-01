package com.example.rc211.fractioncatcher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {
    Button playButton;
    Button highScoreButton;
    Button howToPlayButton;

    TextView titleTextView;
    EditText playerNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        playButton = findViewById(R.id.playButton);
        highScoreButton = findViewById(R.id.highScoreButton);
        howToPlayButton = findViewById(R.id.howToPlayButton);
        titleTextView = findViewById(R.id.titleTextView);
        playerNameTextView = findViewById(R.id.playerNameTextView);


        final Activity activity = this;

        playerNameTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    playerNameTextView.clearFocus();
                    hideKeyboard(activity);
                }
                return false;
            }
        });

        playerNameTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    playerNameTextView.getText().clear();
                }
            }
        });

        ConstraintLayout constraintLayout = findViewById(R.id.startConstraintLayout);
        constraintLayout.setFocusableInTouchMode(true);
        constraintLayout.setFocusable(true);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        String name = "Player Name";
        try{
            name = (String) extras.get("name");
        }
        catch (java.lang.NullPointerException e) {}
        playerNameTextView.setText(name);

        titleTextView.setText(Html.fromHtml("<font color=\"#ff5964\">Fraction</font> <font color=\"#339989\"> Catcher</font>"));
    }

    public void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public void play(View view) {
        Intent startGame = new Intent(this, GameActivity.class);
        String name;
        name = playerNameTextView.getText().toString();

        if (name.isEmpty()) {
            name = "Player Name";
        }

        startGame.putExtra("name", name);
        startActivity(startGame);
        finish();
    }

    public void howToPlay(View view) {
        Intent intent = new Intent(this, HowToPlayActivity.class);
        startActivity(intent);
    }

    public void highScore(View view) {
        Intent intent = new Intent(this, HighScoreActivity.class);
        startActivity(intent);
    }

}
