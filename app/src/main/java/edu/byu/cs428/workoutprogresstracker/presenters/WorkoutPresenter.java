package edu.byu.cs428.workoutprogresstracker.presenters;

import android.view.View;

import edu.byu.cs428.workoutprogresstracker.models.Exercise;
import edu.byu.cs428.workoutprogresstracker.models.Workout;
import edu.byu.cs428.workoutprogresstracker.services.ExercisesService;
import edu.byu.cs428.workoutprogresstracker.services.WorkoutsService;
import edu.byu.cs428.workoutprogresstracker.services.responses.NewExerciseResponse;

public class WorkoutPresenter {
    WorkoutsService service = new WorkoutsService();
    public WorkoutPresenter() {}

    public Workout loadWorkout (int id) {
        //return service.loadWorkout(id);
        return null;
    }

    public void saveWorkout(Workout workout){
        //service.saveWorkout(workout);
    }
}
