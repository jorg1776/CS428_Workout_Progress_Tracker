package edu.byu.cs428.workoutprogresstracker.dao.sqlite;

import java.util.List;

import edu.byu.cs428.workoutprogresstracker.dao.DataAccessException;
import edu.byu.cs428.workoutprogresstracker.dao.WorkoutsExercisesDAO;
import edu.byu.cs428.workoutprogresstracker.models.Exercise;

public class WorkoutsExercisesSQLiteDAO implements WorkoutsExercisesDAO {
    @Override
    public void addExerciseToWorkout(int exerciseId, int workoutId) throws DataAccessException {

    }

    @Override
    public void removeExerciseFromWorkout(int exerciseId, int workoutId) throws DataAccessException {

    }

    @Override
    public List<Exercise> getWorkoutExercises(int workoutId) throws DataAccessException {
        return null;
    }
}
