package com.example.rc211.fractioncatcher;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {HighScoreLog.class}, version = 2)
public abstract class HighScoreDatabase extends RoomDatabase {
    public abstract HighScoreDao highScoreDao();
}

