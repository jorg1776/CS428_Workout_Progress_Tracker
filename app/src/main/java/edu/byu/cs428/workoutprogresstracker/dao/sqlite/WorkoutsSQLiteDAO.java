package edu.byu.cs428.workoutprogresstracker.dao.sqlite;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs428.workoutprogresstracker.dao.DataAccessException;
import edu.byu.cs428.workoutprogresstracker.dao.WorkoutsDAO;
import edu.byu.cs428.workoutprogresstracker.models.Workout;

public class WorkoutsSQLiteDAO implements WorkoutsDAO {
    private final SQLiteDAO dao = new SQLiteDAO();

    @Override
    public void createWorkout(Workout workout) throws DataAccessException {
        ContentValues contentValues = new ContentValues();
        contentValues.put("workout_name", workout.getName());
        contentValues.put("workout_muscle_group", workout.getMuscleGroup());

        try {
            dao.executeInsert("workouts", contentValues);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataAccessException("ERROR: encountered while executing saveWorkout");
        }
    }

    @Override
    public Workout loadWorkout(int workoutID) throws DataAccessException {
        try {
            Cursor cursor = dao.executeQuery("SELECT * FROM workouts WHERE workout_id = ?", new String[]{Integer.toString(workoutID)});

            if (cursor.getCount() > 0) {
                String name = cursor.getString(cursor.getColumnIndex("workout_name"));
                String muscleGroup = cursor.getString(cursor.getColumnIndex("workout_muscle_group"));
                return new Workout(workoutID, name, muscleGroup);
            }

            throw new DataAccessException("Workout not found");
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataAccessException("ERROR: encountered while executing loadWorkout");
        }
    }

    @Override
    public void saveWorkout(Workout workout) throws DataAccessException {
        ContentValues contentValues = new ContentValues();
        contentValues.put("workout_name", workout.getName());
        contentValues.put("workout_muscle_group", workout.getMuscleGroup());

        try {
            dao.executeUpdate("workouts", contentValues, "workout_id=?", new String[]{Integer.toString(workout.getId())});
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataAccessException("ERROR: encountered while executing saveWorkout");
        }
    }

    @Override
    public void deleteWorkout(int workoutId) throws DataAccessException {
        try {
            dao.executeDelete("workouts", "workout_id=?", new String[]{ Integer.toString(workoutId) });
        } catch (Exception e) {
            throw new DataAccessException("ERROR: encountered while deleting workout");
        }
    }

    @Override
    public List<Workout> loadWorkoutsList(String muscleGroup, int count, int lastWorkout) throws DataAccessException {
        try {
            List<Workout> workouts = new ArrayList<>();

            if (muscleGroup == null) {
                muscleGroup = "workout_muscle_group";
            }

            Cursor cursor = dao.executeQuery("SELECT * FROM workouts WHERE workout_muscle_group = ?", new String[]{ muscleGroup });

            while (cursor.moveToNext()) {
                int workoutId = cursor.getInt(cursor.getColumnIndex("workout_id"));
                String name = cursor.getString(cursor.getColumnIndex("workout_name"));
                workouts.add(new Workout(workoutId, name, muscleGroup));
            }

            return workouts;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataAccessException("ERROR: encountered while loading workouts");
        }
    }
}
