package edu.byu.cs428.workoutprogresstracker.dao;

import java.util.List;

import edu.byu.cs428.workoutprogresstracker.models.Exercise;

public interface ExercisesDAO {
    void createExercise(Exercise exercise) throws DataAccessException;
    Exercise loadExercise(int exerciseId) throws DataAccessException;
    void saveExercise(Exercise exercise) throws DataAccessException;
    void deleteExercise(int exerciseId) throws DataAccessException;
    List<Exercise> loadExercisesList(String muscleGroup) throws DataAccessException;
}
