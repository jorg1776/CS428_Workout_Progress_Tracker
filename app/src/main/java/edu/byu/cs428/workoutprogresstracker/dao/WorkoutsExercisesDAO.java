package edu.byu.cs428.workoutprogresstracker.dao;

import java.util.List;

import edu.byu.cs428.workoutprogresstracker.models.Exercise;

public interface WorkoutsExercisesDAO {
    void addExerciseToWorkout(Exercise exercise, int workoutId) throws DataAccessException;
    void removeExerciseFromWorkout(int exerciseId, int workoutId) throws DataAccessException;
    List<Exercise> getWorkoutExercises(int workoutId) throws DataAccessException;
}
