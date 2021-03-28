package edu.byu.cs428.workoutprogresstracker.presenters;

import java.util.Arrays;

import edu.byu.cs428.workoutprogresstracker.dao.DataAccessException;
import edu.byu.cs428.workoutprogresstracker.models.Exercise;
import edu.byu.cs428.workoutprogresstracker.models.Workout;
import edu.byu.cs428.workoutprogresstracker.services.ExercisesService;
import edu.byu.cs428.workoutprogresstracker.services.WorkoutsService;
import edu.byu.cs428.workoutprogresstracker.services.requests.ExercisesRequest;
import edu.byu.cs428.workoutprogresstracker.services.requests.WorkoutsRequest;
import edu.byu.cs428.workoutprogresstracker.services.responses.ExercisesResponse;
import edu.byu.cs428.workoutprogresstracker.services.responses.WorkoutsResponse;

public class WorkoutsListPresenter {
    private WorkoutsService workoutsService;
    private WorkoutsListPresenter.View view;

    public interface View{}

    public WorkoutsListPresenter(WorkoutsListPresenter.View view) {this.view = view;}
    public WorkoutsListPresenter(){}
    public WorkoutsResponse loadWorkouts(WorkoutsRequest request) throws DataAccessException {
        workoutsService = getWorkoutsService();
        return new WorkoutsResponse(workoutsService.loadWorkoutsList(request.getMuscleGroup(), request.getCount(), request.getLastWorkout()), false);


        //mock data, can delete once connected to backend then uncomment return statement above
        /*
        Exercise exercise1 = new Exercise("exercise 1", null, null, null, null);
        exercise1.setId(1);
        Exercise exercise2 = new Exercise("exercise 2", null, null, null, null);
        exercise2.setId(2);
        Workout workout1 = new Workout("workout 1", Arrays.asList(exercise1, exercise2), "cardio");
        workout1.setId(0);

        Exercise exercise3 = new Exercise("exercise 3", null, null, null, null);
        exercise3.setId(3);
        Exercise exercise4 = new Exercise("exercise 4", null, null, null, null);
        exercise4.setId(4);
        Exercise exercise5 = new Exercise("exercise 5", null, null, null, null);
        exercise5.setId(5);
        Workout workout2 = new Workout("workout 2", Arrays.asList(exercise3, exercise4, exercise5), "legs");
        workout2.setId(1);

        WorkoutsResponse response = new WorkoutsResponse(Arrays.asList(workout1, workout2), false);
        return response;*/
    }

    WorkoutsService getWorkoutsService() { return new WorkoutsService(); }
}
