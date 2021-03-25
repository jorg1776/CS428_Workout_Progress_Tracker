package edu.byu.cs428.workoutprogresstracker.dao.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.byu.cs428.workoutprogresstracker.WorkoutProgressTracker;

public class SQLiteDAO extends SQLiteOpenHelper {
    private final SQLiteDatabase DB = this.getWritableDatabase();

    public SQLiteDAO() {
        super(WorkoutProgressTracker.getAppContext(), "Userdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {

        String sqlCreateExerciseTableStmt =
                "CREATE TABLE IF NOT EXISTS exercises " +
                        "(" +
                        "exercise_id INTEGER PRIMARY KEY, " +
                        "workout_id INTEGER, " +
                        "exercise_name TEXT NOT NULL, " +
                        "exercise_muscle_group TEXT NOT NULL," +
                        "objective_name TEXT NOT NULL, " +
                        "objective_value REAL NOT NULL, " +
                        "objective_units TEXT NOT NULL, " +
                        "goal_name TEXT NOT NULL, " +
                        "goal_value REAL NOT NULL, " +
                        "goal_units TEXT NOT NULL" +
                        ");";

        String sqlCreateExerciseHistoryTableSmt =
                "CREATE TABLE IF NOT EXISTS exercise_history" +
                        "(" +
                        "history_id INTEGER PRIMARY KEY, " +
                        "exercise_id INTEGER NOT NULL, " +
                        "goal_name TEXT NOT NULL, " +
                        "goal_value REAL NOT NULL, " +
                        "goal_units TEXT NOT NULL, " +
                        "timestamp INTEGER NOT NULL, " +
                        "FOREIGN KEY(exercise_id) REFERENCES exercises(exercise_id)" +
                        ");";

        String sqlCreateWorkoutTableStmt =
                "CREATE TABLE IF NOT EXISTS workouts " +
                        "(" +
                        "workout_id INTEGER PRIMARY KEY, " +
                        "workout_name TEXT NOT NULL, " +
                        "workout_muscle_group TEXT NOT NULL" +
                        ");";

        String sqlCreateWorkoutsExercisesTableStmt =
                "CREATE TABLE IF NOT EXISTS workouts_exercises " +
                        "(" +
                        "workout_id INTEGER NOT NULL, " +
                        "exercise_id INTEGER NOT NULL, " +
                        "exercise_name String NOT NULL" +
                        ");";

        DB.execSQL(sqlCreateExerciseTableStmt);
        DB.execSQL(sqlCreateExerciseHistoryTableSmt);
        DB.execSQL(sqlCreateWorkoutTableStmt);
        DB.execSQL(sqlCreateWorkoutsExercisesTableStmt);
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("DROP TABLE IF EXISTS exercises");
        DB.execSQL("DROP TABLE IF EXISTS history");
        DB.execSQL("DROP TABLE IF EXISTS workouts");
        DB.execSQL("DROP TABLE IF EXISTS workouts_exercises");
    }

    public Cursor executeQuery(String query, String[] queryArgs) throws Exception {
        return DB.rawQuery(query, queryArgs);
    }

    public void executeInsert(String table, ContentValues contentValues) throws Exception {
        long result = DB.insert(table, null, contentValues);

        if (result == -1) {
            throw new Exception();
        }
    }

    public void executeUpdate(String table, ContentValues contentValues, String whereClause, String[] whereArgs) throws Exception {
        int result = DB.update(table, contentValues, whereClause, whereArgs);

        if (result == -1) {
            throw new Exception();
        }
    }

    public void executeDelete(String table, String whereClause, String[] whereArgs) throws Exception {
        int result = DB.delete(table, whereClause, whereArgs);

        if (result == -1) {
            throw new Exception();
        }
    }
}
