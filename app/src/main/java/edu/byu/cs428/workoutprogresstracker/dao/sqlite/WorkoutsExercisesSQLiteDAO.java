package edu.byu.cs428.workoutprogresstracker.dao.sqlite;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs428.workoutprogresstracker.dao.DataAccessException;
import edu.byu.cs428.workoutprogresstracker.dao.WorkoutsExercisesDAO;
import edu.byu.cs428.workoutprogresstracker.models.Exercise;

public class WorkoutsExercisesSQLiteDAO implements WorkoutsExercisesDAO {
    private final SQLiteDAO dao = new SQLiteDAO();

    @Override
    public void addExerciseToWorkout(Exercise exercise, int workoutId) throws DataAccessException {
        ContentValues contentValues = new ContentValues();
        contentValues.put("workout_id", workoutId);
        contentValues.put("exercise_id", exercise.getId());
        contentValues.put("exercise_name", exercise.getName());

        try {
            dao.executeInsert("workouts_exercises", contentValues);
        } catch (Exception e) {
            throw new DataAccessException("Error adding exercise to workout");
        }
    }

    @Override
    public void removeExerciseFromWorkout(int exerciseId, int workoutId) throws DataAccessException {
        try {
            dao.executeDelete(
                    "workouts_exercises",
                    "workout_id=? AND exercise_id=?",
                    new String[]{ Integer.toString(workoutId), Integer.toString(exerciseId)});
        } catch (Exception e) {
            throw new DataAccessException("Error removing exercise from workout");
        }
    }

    @Override
    public List<Exercise> getWorkoutExercises(int workoutId) throws DataAccessException {
        try {
            List<Exercise> workoutExercises = new ArrayList<>();

            String query = "SELECT * FROM workouts_exercises WHERE workout_id=?";
            String[] whereArgs = new String[]{ Integer.toString(workoutId) };

            Cursor cursor = dao.executeQuery(query, whereArgs);

            while (cursor.moveToNext()) {
                int exerciseId = cursor.getInt(cursor.getColumnIndex("exercise_id"));
                String exerciseName = cursor.getString(cursor.getColumnIndex("exercise_name"));

                workoutExercises.add(new Exercise(exerciseId, exerciseName));
            }

            return workoutExercises;
        } catch (Exception e) {
            throw new DataAccessException("Error getting exercises for workout");
        }
    }
}
