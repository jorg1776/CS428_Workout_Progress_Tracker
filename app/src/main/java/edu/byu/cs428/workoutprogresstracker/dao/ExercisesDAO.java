package edu.byu.cs428.workoutprogresstracker.dao;

import java.util.List;
import java.util.UUID;

import edu.byu.cs428.workoutprogresstracker.models.Exercise;

public interface ExercisesDAO {
    Exercise loadExercise(UUID exerciseID) throws DataAccessException;
    void saveExercise(Exercise exercise) throws DataAccessException;
    List<Exercise> loadExercisesList(String type, int count, UUID lastExercise);
}
