package edu.byu.cs428.workoutprogresstracker.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;
import java.sql.Statement;

public class Database extends SQLiteOpenHelper {

    public Database(Context context) {
        super(context, "Userdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {

        String sqlCreateExerciseStmt =
                "CREATE TABLE IF NOT EXISTS ExerciseTable " +
                    "(" +
                    "id TEXT NOT NULL UNIQUE, " +
                    "name TEXT NOT NULL, " +
                    "muscleGroup TEXT NOT NULL, " +
                    "PRIMARY KEY (id)" +
                    ");";

        String sqlCreateHistoryStmt =
                "CREATE TABLE IF NOT EXISTS HistoryTable " +
                    "(" +
                    "id TEXT NOT NULL UNIQUE, " +
                    "goal TEXT NOT NULL, " +
                    "timestamp TEXT NOT NULL, " +
                    "PRIMARY KEY (id) " +
                    ");";

        DB.execSQL(sqlCreateExerciseStmt);
        DB.execSQL(sqlCreateHistoryStmt);
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("DROP TABLE IF EXISTS ExerciseTable");
        DB.execSQL("DROP TABLE IF EXISTS HistoryTable");
    }
}
