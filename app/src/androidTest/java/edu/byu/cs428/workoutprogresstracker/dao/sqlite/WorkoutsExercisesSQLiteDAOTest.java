package edu.byu.cs428.workoutprogresstracker.dao.sqlite;

import android.content.ContentValues;

import org.junit.Test;

import java.util.List;

import edu.byu.cs428.workoutprogresstracker.dao.DataAccessException;
import edu.byu.cs428.workoutprogresstracker.models.Exercise;
import edu.byu.cs428.workoutprogresstracker.models.History;
import edu.byu.cs428.workoutprogresstracker.models.metric.Metric;
import edu.byu.cs428.workoutprogresstracker.models.metric.TimeMetric;

import static org.junit.Assert.*;

public class WorkoutsExercisesSQLiteDAOTest {

    @Test
    public void addExerciseToWorkout() {
        WorkoutsExercisesSQLiteDAO o = new WorkoutsExercisesSQLiteDAO();
        Metric objective = new TimeMetric(2.2, "s");
        Metric goal = new TimeMetric(2.0, "s");
        Exercise exercise = new Exercise("Israel", objective, goal, "arms");
        try {
            o.addExerciseToWorkout(exercise, 101);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void removeExerciseFromWorkout() {
        WorkoutsExercisesSQLiteDAO o = new WorkoutsExercisesSQLiteDAO();
        Metric objective = new TimeMetric(2.2, "s");
        Metric goal = new TimeMetric(2.0, "s");
        Exercise exercise = new Exercise("Israel", objective, goal, "arms");
        try {
            o.addExerciseToWorkout(exercise, 101);
            o.removeExerciseFromWorkout(101, 101);
            // TODO: Need to double check if exercise is removed.
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void getWorkoutExercises() {
        WorkoutsExercisesSQLiteDAO o = new WorkoutsExercisesSQLiteDAO();
        Metric objective = new TimeMetric(2.2, "s");
        Metric goal = new TimeMetric(2.0, "s");
        Exercise exercise = new Exercise("Israel", objective, goal, "arms");
        try {
            o.addExerciseToWorkout(exercise, 101);
            List<Exercise> exercises = o.getWorkoutExercises(101);
            assertNotNull(exercises);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}