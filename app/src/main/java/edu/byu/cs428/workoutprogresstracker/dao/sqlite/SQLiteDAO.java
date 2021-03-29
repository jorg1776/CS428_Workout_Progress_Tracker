package edu.byu.cs428.workoutprogresstracker.dao.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import edu.byu.cs428.workoutprogresstracker.dao.DataAccessException;
import edu.byu.cs428.workoutprogresstracker.models.Exercise;
import edu.byu.cs428.workoutprogresstracker.models.History;
import edu.byu.cs428.workoutprogresstracker.models.Workout;
import edu.byu.cs428.workoutprogresstracker.models.metric.DistanceMetric;
import edu.byu.cs428.workoutprogresstracker.models.metric.Metric;
import edu.byu.cs428.workoutprogresstracker.models.metric.RepsMetric;
import edu.byu.cs428.workoutprogresstracker.models.metric.TimeMetric;
import edu.byu.cs428.workoutprogresstracker.models.metric.WeightMetric;
import edu.byu.cs428.workoutprogresstracker.services.ExercisesService;
import edu.byu.cs428.workoutprogresstracker.services.WorkoutsService;

public class SQLiteDAO extends SQLiteOpenHelper {
    private SQLiteDatabase DB;
    private static SQLiteDAO instance;

    public SQLiteDAO(Context context) {
        super(context, "Workouts.db", null, 1);
        instance = this;
        DB = this.getReadableDatabase();
    }

    public static SQLiteDAO getInstance() {
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        System.out.println("Creating Database");

        String sqlCreateExerciseTableStmt =
                "CREATE TABLE IF NOT EXISTS exercises " +
                        "(" +
                        "exercise_id INTEGER PRIMARY KEY, " +
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

        this.DB = DB;
        addDummyData();
    }

    private void addDummyData() {
        ExercisesService exercisesService = new ExercisesService();
        WorkoutsService workoutsService = new WorkoutsService();

        Metric benchObjective = new RepsMetric(10);
        Metric benchGoal = new WeightMetric(155d, "lb");
        Exercise benchPress = new Exercise(1, "Bench Press", benchObjective, benchGoal, "Chest");

        Metric inclinedBenchObjective = new RepsMetric(10);
        Metric inclinedBenchGoal = new WeightMetric(135d, "lb");
        Exercise inclinedBenchPress = new Exercise(2, "Inclined Bench Press", inclinedBenchObjective, inclinedBenchGoal, "Chest");

        Workout chestWorkout = new Workout(1, "Chest", "Chest");


        Metric mileRunObjective = new DistanceMetric(1d, "mi");
        Metric mileRunGoal = new TimeMetric(9d, "min");
        Exercise mileRun = new Exercise(3, "Mile Run", mileRunObjective, mileRunGoal, "Cardio");

        Metric sprintObjective = new DistanceMetric(100d, "m");
        Metric sprintGoal = new TimeMetric(13d, "sec");
        Exercise sprint = new Exercise(4, "100m Sprint", sprintObjective, sprintGoal, "Cardio");

        Workout cardioWorkout = new Workout(2, "Cardio", "Cardio");


        try {
            exercisesService.createExercise(benchPress);
            exercisesService.createExercise(inclinedBenchPress);
            exercisesService.createExercise(mileRun);
            exercisesService.createExercise(sprint);

            workoutsService.createWorkout(chestWorkout);
            workoutsService.createWorkout(cardioWorkout);

            workoutsService.addExerciseToWorkout(benchPress, chestWorkout.getId());
            workoutsService.addExerciseToWorkout(inclinedBenchPress, chestWorkout.getId());

            workoutsService.addExerciseToWorkout(mileRun, cardioWorkout.getId());
            workoutsService.addExerciseToWorkout(sprint, cardioWorkout.getId());
        } catch (DataAccessException e) {
            System.err.println(e);
        }
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
