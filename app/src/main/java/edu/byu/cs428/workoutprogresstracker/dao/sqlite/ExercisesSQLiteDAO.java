package edu.byu.cs428.workoutprogresstracker.dao.sqlite;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.List;

import edu.byu.cs428.workoutprogresstracker.dao.DataAccessException;
import edu.byu.cs428.workoutprogresstracker.dao.ExercisesDAO;
import edu.byu.cs428.workoutprogresstracker.models.Exercise;

public class ExercisesSQLiteDAO implements ExercisesDAO {
    private final SQLiteDAO dao = new SQLiteDAO();

    @Override
    public Exercise loadExercise(int exerciseID) throws DataAccessException {
        Exercise exercise = null;

        try {
            Cursor cursor = dao.executeQuery("SELECT * FROM exercises WHERE exercise_id = ?", new String[]{Integer.toString(exerciseID)});

            if (cursor.getCount() > 0) {
                String name = cursor.getString(cursor.getColumnIndex("exercise_name"));
                String muscleGroup = cursor.getString(cursor.getColumnIndex("exercise_muscle_group"));
                exercise = new Exercise(exerciseID, name, null, null, null, muscleGroup);
            }

            return exercise;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataAccessException("ERROR: encountered while loading exercise");
        }
    }

    @Override
    public void saveExercise(Exercise exercise) throws DataAccessException {
        ContentValues contentValues = new ContentValues();
        contentValues.put("exercise_id", exercise.getId());
        contentValues.put("exercise_name", exercise.getName());
        contentValues.put("exercise_muscle_group", exercise.getMuscleGroup());

        try {
            dao.executeInsert("exercises", contentValues);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataAccessException("ERROR: encountered while saving exercise");
        }
    }

    @Override
    public List<Exercise> loadExercisesList(String type, int count, int lastExercise) {
        return null;
    }
}
