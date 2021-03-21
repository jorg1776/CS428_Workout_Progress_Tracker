package edu.byu.cs428.workoutprogresstracker.dao;

import java.util.List;

import edu.byu.cs428.workoutprogresstracker.models.Workout;

public interface WorkoutsDAO {
    void createWorkout(Workout workout) throws DataAccessException;
    Workout loadWorkout(int workoutID) throws DataAccessException;
    void saveWorkout(Workout workout) throws DataAccessException;
    List<Workout> loadWorkoutsList(String type, int count, int lastWorkout);
}
