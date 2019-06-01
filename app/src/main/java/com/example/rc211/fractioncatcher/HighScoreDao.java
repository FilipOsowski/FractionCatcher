package com.example.rc211.fractioncatcher;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface HighScoreDao {

    @Query("SELECT * FROM HighScoreLog LIMIT 1")
    HighScoreLog getHighScoreLog();

    @Query("DELETE FROM HighScoreLog")
    void deleteHighScoreLog();

    @Insert
    void insertHighScoreLog(HighScoreLog highScoreLog);
}

