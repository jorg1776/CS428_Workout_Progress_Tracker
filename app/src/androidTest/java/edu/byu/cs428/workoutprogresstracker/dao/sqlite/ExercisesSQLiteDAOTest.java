package edu.byu.cs428.workoutprogresstracker.dao.sqlite;

import org.junit.Test;

import java.util.List;

import edu.byu.cs428.workoutprogresstracker.models.Exercise;
import edu.byu.cs428.workoutprogresstracker.models.metric.Metric;
import edu.byu.cs428.workoutprogresstracker.models.metric.TimeMetric;

import static org.junit.Assert.*;

public class ExercisesSQLiteDAOTest {

    @Test
    public void createExercise() {
        ExercisesSQLiteDAO o = new ExercisesSQLiteDAO();
        Metric objective = new TimeMetric(2.2, "s");
        Metric goal = new TimeMetric(2.0, "s");
        Exercise exercise = new Exercise(100, "shoulder", objective, goal, "arm");
        try {
            o.createExercise(exercise);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void loadExercise() {
        ExercisesSQLiteDAO o = new ExercisesSQLiteDAO();
        Metric objective = new TimeMetric(2.2, "s");
        Metric goal = new TimeMetric(2.0, "s");
        Exercise exercise = new Exercise(100, "shoulder", objective, goal, "arm");
        try {
            o.createExercise(exercise);
            Exercise loadedExercise = o.loadExercise(100);
            assertNotNull(loadedExercise);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void saveExercise() {
        ExercisesSQLiteDAO o = new ExercisesSQLiteDAO();
        Metric objective = new TimeMetric(2.2, "s");
        Metric goal = new TimeMetric(2.0, "s");
        Exercise exercise = new Exercise(100, "shoulder", objective, goal, "arm");
        try {
            o.createExercise(exercise);
            Exercise update = new Exercise(100, "bicep", objective, goal, "arm");
            o.saveExercise(update);
            Exercise loadedExercise = o.loadExercise(100);
            assertEquals(loadedExercise.getName(), "bicep");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void deleteExercise() {
        ExercisesSQLiteDAO o = new ExercisesSQLiteDAO();
        Metric objective = new TimeMetric(2.2, "s");
        Metric goal = new TimeMetric(2.0, "s");
        Exercise exercise = new Exercise(100, "shoulder", objective, goal, "arm");
        try {
            o.createExercise(exercise);
            o.deleteExercise(100);
            Exercise loadedExercise = o.loadExercise(100);
            assertNull(loadedExercise);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void loadExercisesList() {
        ExercisesSQLiteDAO o = new ExercisesSQLiteDAO();
        Metric objective = new TimeMetric(2.2, "s");
        Metric goal = new TimeMetric(2.0, "s");
        Exercise exercise = new Exercise(100, "shoulder", objective, goal, "arm");
        try {
            o.createExercise(exercise);
            List<Exercise> exercises = o.loadExercisesList("arm");
            assertNotNull(exercises);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}