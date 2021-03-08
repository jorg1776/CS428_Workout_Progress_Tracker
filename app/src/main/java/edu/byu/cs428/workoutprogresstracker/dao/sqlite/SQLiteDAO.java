package edu.byu.cs428.workoutprogresstracker.dao.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.byu.cs428.workoutprogresstracker.WorkoutProgressTracker;

public class SQLiteDAO extends SQLiteOpenHelper {
    private static final String TAG = "DAO";
    private final SQLiteDatabase DB = this.getWritableDatabase();

    public SQLiteDAO() {
        super(WorkoutProgressTracker.getAppContext(), "Userdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {

        String sqlCreateExerciseTableStmt =
                "CREATE TABLE IF NOT EXISTS ExerciseTable " +
                        "(" +
                        "id TEXT NOT NULL UNIQUE, " +
                        "name TEXT NOT NULL, " +
                        "objective TEXT NOT NULL, " +
                        "goal TEXT NOT NULL, " +
                        "muscleGroup TEXT NOT NULL, " +
                        "PRIMARY KEY (id)" +
                        ");";

        String sqlCreateWorkoutTableStmt =
                "CREATE TABLE IF NOT EXISTS WorkoutTable " +
                        "(" +
                        "id TEXT NOT NULL UNIQUE, " +
                        "name TEXT NOT NULL, " +
                        "muscleGroup TEXT NOT NULL, " +
                        "PRIMARY KEY (id) " +
                        ");";

        DB.execSQL(sqlCreateExerciseTableStmt);
        DB.execSQL(sqlCreateWorkoutTableStmt);
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("DROP TABLE IF EXISTS ExerciseTable");
        DB.execSQL("DROP TABLE IF EXISTS WorkoutTable");
    }

    public Cursor executeQuery(String query, String[] queryArgs) throws Exception {
        return DB.rawQuery(query, queryArgs);
    }

    public long executeInsert(String table, ContentValues contentValues) throws Exception {
        long result = DB.insert(table, null, contentValues);

        if(result == -1) {
            throw new Exception();
        }

        return result;
    }
}
