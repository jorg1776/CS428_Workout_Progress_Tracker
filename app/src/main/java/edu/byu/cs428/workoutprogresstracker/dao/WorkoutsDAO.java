package edu.byu.cs428.workoutprogresstracker.dao;

import java.util.List;
import java.util.UUID;

import edu.byu.cs428.workoutprogresstracker.models.Workout;

public interface WorkoutsDAO {
    Workout loadWorkout(UUID workoutID) throws DataAccessException;
    void saveWorkout(Workout workout) throws DataAccessException;
    List<Workout> loadWorkoutsList(String type, int count, UUID lastWorkout);
}
