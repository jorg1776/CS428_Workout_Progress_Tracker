package edu.byu.cs428.workoutprogresstracker.dao.sqlite;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.List;
import java.util.UUID;

import edu.byu.cs428.workoutprogresstracker.dao.DataAccessException;
import edu.byu.cs428.workoutprogresstracker.dao.ExercisesDAO;
import edu.byu.cs428.workoutprogresstracker.models.Exercise;

public class ExercisesSQLiteDAO implements ExercisesDAO {
    private final SQLiteDAO dao = new SQLiteDAO();

    @Override
    public Exercise loadExercise(UUID exerciseID) throws DataAccessException {
        Exercise exercise = null;
        String id = exerciseID.toString();

        try {
            Cursor cursor = dao.executeQuery("Select * from ExerciseTable where id = ?", new String[]{id});

            if (cursor.getCount() > 0) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String muscleGroup = cursor.getString(cursor.getColumnIndex("muscleGroup"));
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
        contentValues.put("id", exercise.getId().toString());
        contentValues.put("name", exercise.getName());
        contentValues.put("muscleGroup", exercise.getMuscleGroup());

        try {
            dao.executeInsert("ExerciseTable", contentValues);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataAccessException("ERROR: encountered while saving exercise");
        }
    }

    @Override
    public List<Exercise> loadExercisesList(String type, int count, UUID lastExercise) {
        return null;
    }
}
