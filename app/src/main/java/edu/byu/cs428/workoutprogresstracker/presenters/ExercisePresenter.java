package edu.byu.cs428.workoutprogresstracker.presenters;

import android.view.View;

import edu.byu.cs428.workoutprogresstracker.models.Exercise;
import edu.byu.cs428.workoutprogresstracker.services.ExercisesService;
import edu.byu.cs428.workoutprogresstracker.services.requests.NewExerciseRequest;
import edu.byu.cs428.workoutprogresstracker.services.responses.NewExerciseResponse;

public class ExercisePresenter {
    ExercisesService service = new ExercisesService();
    public ExercisePresenter(View view) {}

    public Exercise loadExercise (int id) {
        //return service.loadExercise(id);
        return null;
    }

    public NewExerciseResponse saveExercise(Exercise exercise){
        //return service.saveExercise(exercise);
        return null;
    }


}
