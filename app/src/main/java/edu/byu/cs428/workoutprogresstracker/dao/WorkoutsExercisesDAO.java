package edu.byu.cs428.workoutprogresstracker.dao;

public interface WorkoutsExercisesDAO {
    void addExerciseToWorkout(int exerciseId, int workoutId) throws DataAccessException;
    void removeExerciseFromWorkout(int exerciseId, int workoutId) throws DataAccessException;
}
