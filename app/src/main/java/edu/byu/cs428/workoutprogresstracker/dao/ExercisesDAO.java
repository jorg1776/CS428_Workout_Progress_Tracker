package edu.byu.cs428.workoutprogresstracker.dao;

import java.util.List;

import edu.byu.cs428.workoutprogresstracker.models.Exercise;

public interface ExercisesDAO {
    void createExercise(Exercise exercise) throws DataAccessException;
    Exercise loadExercise(int exerciseID) throws DataAccessException;
    void saveExercise(Exercise exercise) throws DataAccessException;
    List<Exercise> loadExercisesList(String type, int count, int lastExercise);
}
