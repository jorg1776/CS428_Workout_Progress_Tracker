package edu.byu.cs428.workoutprogresstracker.dao.sqlite;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.List;

import edu.byu.cs428.workoutprogresstracker.dao.DataAccessException;
import edu.byu.cs428.workoutprogresstracker.dao.WorkoutsDAO;
import edu.byu.cs428.workoutprogresstracker.models.Workout;

public class WorkoutsSQLiteDAO implements WorkoutsDAO {
    private final SQLiteDAO dao = new SQLiteDAO();

    @Override
    public Workout loadWorkout(int workoutID) throws DataAccessException {
        Workout workout = null;

        try {
            Cursor cursor = dao.executeQuery("SELECT * FROM workouts WHERE workout_id = ?", new String[]{Integer.toString(workoutID)});

            if (cursor.getCount() > 0) {
                String name = cursor.getString(cursor.getColumnIndex("workout_name"));
                String muscleGroup = cursor.getString(cursor.getColumnIndex("workout_muscle_group"));
                workout = new Workout(workoutID, name, null, muscleGroup);
            }

            return workout;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataAccessException("ERROR: encountered while executing loadWorkout");
        }
    }

    @Override
    public void saveWorkout(Workout workout) throws DataAccessException {
        ContentValues contentValues = new ContentValues();
        contentValues.put("workout_id", workout.getId());
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
    public List<Workout> loadWorkoutsList(String type, int count, int lastWorkout) {
        return null;
    }
}
