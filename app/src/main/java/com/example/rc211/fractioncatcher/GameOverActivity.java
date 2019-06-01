package com.example.rc211.fractioncatcher;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over_screen);


        Bundle extras = getIntent().getExtras();

        int score = (int) extras.get("score");

        TextView scoreTextView = findViewById(R.id.scoreTextView);
        scoreTextView.setText("Score: " + score);

        TextView fractionTextView = findViewById(R.id.fractionTextView);
        fractionTextView.setText(extras.get("fraction") + " = " + extras.get("decimal"));

        TextView conditionTextView = findViewById(R.id.conditionTextView);
        conditionTextView.setText(extras.get("condition") + "");

        HighScoreDatabase db = Room.databaseBuilder(getApplicationContext(), HighScoreDatabase.class, "test-db").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        HighScoreDao dao = db.highScoreDao();

        HighScoreLog currentHighScore = dao.getHighScoreLog();

        HighScoreLog newHighScore = new HighScoreLog();
        newHighScore.setName((String) extras.get("name"));
        newHighScore.setScore(score);
        newHighScore.setMistake("You said that " + fractionTextView.getText() + " " + conditionTextView.getText() + " 3/2 = 1.5");

        if ((currentHighScore == null) || (newHighScore.getScore() > currentHighScore.getScore())) {
            dao.deleteHighScoreLog();
            dao.insertHighScoreLog(newHighScore);
        }
    }

    public void tryAgain(View view) {
        Intent intent = new Intent(this, StartActivity.class);
        intent.putExtra("name", (String) getIntent().getExtras().get("name"));
        startActivity(intent);
        finish();
    }

}
