package com.example.rc211.fractioncatcher;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HighScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        HighScoreDatabase db = Room.databaseBuilder(getApplicationContext(), HighScoreDatabase.class, "test-db").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        HighScoreDao dao = db.highScoreDao();

        TextView highScoreTextView = findViewById(R.id.highScoreTextView);
        TextView mistakeTextView = findViewById(R.id.mistakeTextView);

        HighScoreLog highScoreLog = dao.getHighScoreLog();

        if (highScoreLog == null) {
            highScoreTextView.setText("There is no high score yet");
            mistakeTextView.setText("");
        }
        else {
            highScoreTextView.setText(highScoreLog.getName() + ": " + highScoreLog.getScore());
            mistakeTextView.setText("The following mistake ended your high score: " + highScoreLog.getMistake());
        }
    }
}
