package edu.byu.cs428.workoutprogresstracker.presenters;

import java.util.Arrays;
import java.util.List;

import edu.byu.cs428.workoutprogresstracker.dao.DataAccessException;
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
    public ExercisesResponse loadExercises(ExercisesRequest request) throws DataAccessException {
        exercisesService = getExerciseService();
        List<Exercise> exercises = exercisesService.loadExercisesList(request.getMuscleGroup());
        if (exercises != null) {
            return new ExercisesResponse(exercisesService.loadExercisesList(request.getMuscleGroup()/*, request.getCount(), request.getLastExercise())*/), false);
        }
        else {
            //mock data, can delete once connected to backend then uncomment return statement above
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
            ExercisesResponse response = new ExercisesResponse(Arrays.asList(exercise1, exercise2, exercise3, exercise4, exercise5), false);
            return response;
        }
    }

    ExercisesService getExerciseService() { return new ExercisesService(); }
}
