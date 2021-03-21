package edu.byu.cs428.workoutprogresstracker.presenters;

import android.view.View;

import java.util.Arrays;

import edu.byu.cs428.workoutprogresstracker.models.Exercise;
import edu.byu.cs428.workoutprogresstracker.services.ExerciseService;
import edu.byu.cs428.workoutprogresstracker.services.requests.ExercisesRequest;
import edu.byu.cs428.workoutprogresstracker.services.responses.ExercisesResponse;


public class ExercisePresenter {
    private final ExercisePresenter.View view;

    public interface View{}

    public ExercisePresenter(ExercisePresenter.View view) {this.view = view;}

    public ExercisesResponse getExercise(ExercisesRequest request){
        ExerciseService exerciseService = getExerciseService();
        //return exerciseService.getExercises(request);

        Exercise exercise1 = new Exercise();
        exercise1.setName("Russian twists");
        Exercise exercise2 = new Exercise();
        exercise2.setName("Bench press");
        Exercise exercise3 = new Exercise();
        exercise3.setName("Goblet squat");
        ExercisesResponse response = new ExercisesResponse(Arrays.asList(exercise1, exercise2, exercise3), false);
        return response;
    }

    ExerciseService getExerciseService() { return new ExerciseService(); }
}
