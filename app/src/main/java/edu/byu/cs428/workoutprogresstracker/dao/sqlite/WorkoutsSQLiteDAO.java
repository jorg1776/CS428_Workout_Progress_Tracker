package edu.byu.cs428.workoutprogresstracker.dao.sqlite;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.List;
import java.util.UUID;

import edu.byu.cs428.workoutprogresstracker.dao.DataAccessException;
import edu.byu.cs428.workoutprogresstracker.dao.WorkoutsDAO;
import edu.byu.cs428.workoutprogresstracker.models.Workout;

public class WorkoutsSQLiteDAO implements WorkoutsDAO {
    private final SQLiteDAO dao = new SQLiteDAO();

    @Override
    public Workout loadWorkout(UUID workoutID) throws DataAccessException {
        Workout workout = null;
        String id = workoutID.toString();

        try {
            Cursor cursor = dao.executeQuery("Select * from WorkoutTable where id = ?", new String[]{id});

            if (cursor.getCount() > 0) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String muscleGroup = cursor.getString(cursor.getColumnIndex("muscleGroup"));
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
        contentValues.put("id", workout.getId().toString());
        contentValues.put("name", workout.getName());
        contentValues.put("muscleGroup", workout.getMuscleGroup());

        try {
            dao.executeInsert("WorkoutTable", contentValues);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataAccessException("ERROR: encountered while executing saveWorkout");
        }
    }

    @Override
    public List<Workout> loadWorkoutsList(String type, int count, UUID lastWorkout) {
        return null;
    }
}
