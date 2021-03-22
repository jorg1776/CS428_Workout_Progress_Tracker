package edu.byu.cs428.workoutprogresstracker.presenters;

import java.util.Arrays;
import java.util.List;

import edu.byu.cs428.workoutprogresstracker.models.Exercise;
import edu.byu.cs428.workoutprogresstracker.services.ExercisesService;
import edu.byu.cs428.workoutprogresstracker.services.requests.ExercisesRequest;
import edu.byu.cs428.workoutprogresstracker.services.responses.ExercisesResponse;


public class ExercisesListPresenter {
    private ExercisesService exercisesService;
    private ExercisesListPresenter.View view;

    public interface View{}

    public ExercisesListPresenter(ExercisesListPresenter.View view) {this.view = view;}
    public ExercisesListPresenter(){}
    public ExercisesResponse loadExercises(ExercisesRequest request){
        exercisesService = getExerciseService();
        //return new ExercisesResponse(exercisesService.loadExercisesList(muscleGroup, count, lastExercise));

        Exercise exercise1 = new Exercise();
        exercise1.setName("exercise 1");
        Exercise exercise2 = new Exercise();
        exercise2.setName("exercise 2");
        Exercise exercise3 = new Exercise();
        exercise3.setName("exercise 3");
        Exercise exercise4 = new Exercise();
        exercise4.setName("exercise 4");
        Exercise exercise5 = new Exercise();
        exercise5.setName("exercise 5");
        ExercisesResponse response = new ExercisesResponse(Arrays.asList(exercise1, exercise2, exercise3, exercise4, exercise5), false);
        return response;
    }

    ExercisesService getExerciseService() { return new ExercisesService(); }
}
