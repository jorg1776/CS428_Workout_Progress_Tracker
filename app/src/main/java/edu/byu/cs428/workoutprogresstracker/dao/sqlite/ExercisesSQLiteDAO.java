package edu.byu.cs428.workoutprogresstracker.dao.sqlite;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs428.workoutprogresstracker.dao.DataAccessException;
import edu.byu.cs428.workoutprogresstracker.dao.ExercisesDAO;
import edu.byu.cs428.workoutprogresstracker.models.Exercise;
import edu.byu.cs428.workoutprogresstracker.models.metric.InvalidMetricTypeException;
import edu.byu.cs428.workoutprogresstracker.models.metric.Metric;
import edu.byu.cs428.workoutprogresstracker.models.metric.MetricFactory;

public class ExercisesSQLiteDAO implements ExercisesDAO {
    private final SQLiteDAO dao = SQLiteDAO.getInstance();

    @Override
    public void createExercise(Exercise exercise) throws DataAccessException {
        Metric objective = exercise.getObjective();
        Metric goal = exercise.getGoal();

        ContentValues contentValues = new ContentValues();
        contentValues.put("exercise_name", exercise.getName());
        contentValues.put("exercise_muscle_group", exercise.getMuscleGroup());
        contentValues.put("objective_name", objective.getName());
        contentValues.put("objective_value", objective.getValue().toString());
        contentValues.put("objective_units", objective.getUnits());
        contentValues.put("goal_name", goal.getName());
        contentValues.put("goal_value", goal.getValue().toString());
        contentValues.put("goal_units", goal.getUnits());

        try {
            dao.executeInsert("exercises", contentValues);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataAccessException("ERROR: encountered while saving exercise");
        }
    }

    @Override
    public Exercise loadExercise(int exerciseID) throws DataAccessException {
        try {
            Cursor cursor = dao.executeQuery("SELECT * FROM exercises WHERE exercise_id=?", new String[]{ Integer.toString(exerciseID) });

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                String exerciseName = cursor.getString(cursor.getColumnIndex("exercise_name"));
                String muscleGroup = cursor.getString(cursor.getColumnIndex("exercise_muscle_group"));
                String objectiveName = cursor.getString(cursor.getColumnIndex("objective_name"));
                Double objectiveValue = cursor.getDouble(cursor.getColumnIndex("objective_value"));
                String objectiveUnits = cursor.getString(cursor.getColumnIndex("objective_units"));
                String goalName = cursor.getString(cursor.getColumnIndex("goal_name"));
                Double goalValue = cursor.getDouble(cursor.getColumnIndex("goal_value"));
                String goalUnits = cursor.getString(cursor.getColumnIndex("goal_units"));

                Metric objective;
                Metric goal;

                try {
                    objective = MetricFactory.createMetric(objectiveName, objectiveValue, objectiveUnits);
                } catch (InvalidMetricTypeException e) {
                    throw new DataAccessException(String.format("%s saved with invalid objective type", exerciseName));
                }

                try {
                    goal = MetricFactory.createMetric(goalName, goalValue, goalUnits);
                } catch (InvalidMetricTypeException e) {
                    throw new DataAccessException(String.format("%s saved with invalid goal type", exerciseName));
                }

                return new Exercise(exerciseID, exerciseName, objective, goal, null, muscleGroup);
            }

            throw new DataAccessException("Exercise not found");
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataAccessException("ERROR: encountered while loading exercise");
        }
    }

    @Override
    public void saveExercise(Exercise exercise) throws DataAccessException {
        Metric objective = exercise.getObjective();
        Metric goal = exercise.getGoal();

        ContentValues contentValues = new ContentValues();
        contentValues.put("exercise_name", exercise.getName());
        contentValues.put("exercise_muscle_group", exercise.getMuscleGroup());
        contentValues.put("objective_name", objective.getName());
        contentValues.put("objective_value", objective.getValue().toString());
        contentValues.put("objective_units", objective.getUnits());
        contentValues.put("goal_name", goal.getName());
        contentValues.put("goal_value", goal.getValue().toString());
        contentValues.put("goal_units", goal.getUnits());

        try {
            dao.executeUpdate("exercises", contentValues, "exercise_id=?", new String[]{ Integer.toString(exercise.getId()) });
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataAccessException("ERROR: encountered while saving exercise");
        }
    }

    @Override
    public void deleteExercise(int exerciseId) throws DataAccessException {
        try {
            dao.executeDelete("exercises", "exercise_id=?", new String[]{ Integer.toString(exerciseId) });
        } catch (Exception e) {
            throw new DataAccessException("ERROR: encountered while deleting exercise");
        }
    }

    @Override
    public List<Exercise> loadExercisesList(String muscleGroup) throws DataAccessException {
        try {
            List<Exercise> exercises = new ArrayList<>();
            Cursor cursor;

            if (muscleGroup.equals("All") || muscleGroup == null) {
                cursor = dao.executeQuery("SELECT * FROM exercises", new String[]{});
            }
            else {
                cursor = dao.executeQuery("SELECT * FROM exercises WHERE exercise_muscle_group=?", new String[]{ muscleGroup });
            }

            while (cursor.moveToNext()) {
                int exerciseId = cursor.getInt(cursor.getColumnIndex("exercise_id"));
                String exerciseName = cursor.getString(cursor.getColumnIndex("exercise_name"));

                exercises.add(new Exercise(exerciseId, exerciseName));
            }

            return exercises;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataAccessException("ERROR: encountered while loading exercises");
        }
    }
}
