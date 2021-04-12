package edu.byu.cs428.workoutprogresstracker.dao.sqlite;

import org.junit.Test;

import java.util.List;

import edu.byu.cs428.workoutprogresstracker.models.Exercise;
import edu.byu.cs428.workoutprogresstracker.models.Workout;
import edu.byu.cs428.workoutprogresstracker.models.metric.Metric;
import edu.byu.cs428.workoutprogresstracker.models.metric.TimeMetric;

import static org.junit.Assert.*;

public class WorkoutsSQLiteDAOTest {

    @Test
    public void createWorkout() {
        WorkoutsSQLiteDAO o = new WorkoutsSQLiteDAO();
        Workout workout = new Workout(101, "shoulder", "arms");
        try {
            o.createWorkout(workout);
            Workout loadedWorkout = o.loadWorkout(101);
            assertEquals(loadedWorkout.getName(), "shoulder");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void loadWorkout() {
        WorkoutsSQLiteDAO o = new WorkoutsSQLiteDAO();
        Workout workout = new Workout(101, "shoulder", "arms");
        try {
            o.createWorkout(workout);
            Workout loadedWorkout = o.loadWorkout(101);
            assertNotNull(loadedWorkout);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void saveWorkout() {
        WorkoutsSQLiteDAO o = new WorkoutsSQLiteDAO();
        Workout workout = new Workout(101, "shoulder", "arms");
        try {
            o.createWorkout(workout);
            Workout update = new Workout(101, "bicep", "arms");
            o.saveWorkout(update);
            Workout loadedWorkout = o.loadWorkout(101);
            assertEquals(loadedWorkout.getName(), "bicep");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void deleteWorkout() {
        WorkoutsSQLiteDAO o = new WorkoutsSQLiteDAO();
        Workout workout = new Workout(101, "shoulder", "arms");
        try {
            o.createWorkout(workout);
            o.deleteWorkout(101);
            Workout loadedWorkout = o.loadWorkout(101);
            assertNull(loadedWorkout);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void loadWorkoutsList() {
        WorkoutsSQLiteDAO o = new WorkoutsSQLiteDAO();
        Workout workout = new Workout(101, "shoulder", "arms");
        try {
            o.createWorkout(workout);
            List<Workout> workouts = o.loadWorkoutsList("ams", 1, 101);
            assertNotNull(workouts);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}