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

    public Workout loadWorkout (int id) {
        //return service.loadWorkout(id);

        Exercise exercise1 = new Exercise("exercise 1", null, null, null, null);
        exercise1.setId(1);
        Exercise exercise2 = new Exercise("exercise 2", null, null, null, null);
        exercise2.setId(2);
        Workout workout1 = new Workout("workout 1", Arrays.asList(exercise1, exercise2), "cardio");
        workout1.setId(0);
        return workout1;
    }

    public void saveWorkout(Workout workout){
        //service.saveWorkout(workout);
    }

    public List<Exercise> getWorkoutExercises(int workoutId) throws DataAccessException {
        //return service.getWorkoutExercises(workoutId);

        Exercise exercise1 = new Exercise("exercise 1", null, null, null, null);
        exercise1.setId(1);
        Exercise exercise2 = new Exercise("exercise 2", null, null, null, null);
        exercise2.setId(2);
        Exercise exercise3 = new Exercise("exercise 3", null, null, null, null);
        exercise3.setId(3);
        Exercise exercise4 = new Exercise("exercise 4", null, null, null, null);
        exercise4.setId(4);
        Exercise exercise5 = new Exercise("exercise 5", null, null, null, null);
        exercise5.setId(5);
        return Arrays.asList(exercise1, exercise2, exercise3, exercise4, exercise5);
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
