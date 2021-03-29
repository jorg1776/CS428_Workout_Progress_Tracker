package edu.byu.cs428.workoutprogresstracker.presenters;

import java.util.Arrays;
import java.util.List;

import edu.byu.cs428.workoutprogresstracker.dao.DataAccessException;
import edu.byu.cs428.workoutprogresstracker.models.Exercise;
import edu.byu.cs428.workoutprogresstracker.models.Workout;
import edu.byu.cs428.workoutprogresstracker.services.WorkoutsService;
import edu.byu.cs428.workoutprogresstracker.services.responses.ExercisesResponse;

public class WorkoutPresenter {
    WorkoutsService service = new WorkoutsService();
    public WorkoutPresenter() {}

    public void createWorkout(Workout workout) throws DataAccessException {
        service.createWorkout(workout);
    }

    public Workout loadWorkout (int id) throws DataAccessException {
        return service.loadWorkout(id);
    }

    public void saveWorkout(Workout workout) throws DataAccessException {
        service.saveWorkout(workout);
    }

    public List<Exercise> getWorkoutExercises(int workoutId) throws DataAccessException {
        return service.getWorkoutExercises(workoutId);
    }

    public void addExercise(Exercise exercise, int workoutId) throws DataAccessException {
        service.addExerciseToWorkout(exercise, workoutId);
    }

    public void removeExercise(int exercise, int workout) throws DataAccessException {
        service.removeExerciseFromWorkout(exercise, workout);
    }

    public void deleteWorkout(int workoutId) throws DataAccessException {
        service.deleteWorkout(workoutId);
    }
}
