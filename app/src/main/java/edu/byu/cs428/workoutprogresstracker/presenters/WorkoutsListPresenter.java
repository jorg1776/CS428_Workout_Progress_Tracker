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
    }

    WorkoutsService getWorkoutsService() { return new WorkoutsService(); }
}
