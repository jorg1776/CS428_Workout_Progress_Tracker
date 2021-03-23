package edu.byu.cs428.workoutprogresstracker.presenters;

import edu.byu.cs428.workoutprogresstracker.models.Workout;
import edu.byu.cs428.workoutprogresstracker.services.WorkoutsService;

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
