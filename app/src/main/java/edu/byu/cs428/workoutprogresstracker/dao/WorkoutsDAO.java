package edu.byu.cs428.workoutprogresstracker.dao;

import java.util.List;

import edu.byu.cs428.workoutprogresstracker.models.Workout;

public interface WorkoutsDAO {
    void createWorkout(Workout workout) throws DataAccessException;
    Workout loadWorkout(int workoutId) throws DataAccessException;
    void saveWorkout(Workout workout) throws DataAccessException;
    void deleteWorkout(int workoutId) throws DataAccessException;
    List<Workout> loadWorkoutsList(String muscleGroup, int count, int lastWorkout) throws DataAccessException;
}
