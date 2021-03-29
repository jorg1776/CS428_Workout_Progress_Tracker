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

        return new ExercisesResponse(exercises, false);

    }

    ExercisesService getExerciseService() { return new ExercisesService(); }
}
