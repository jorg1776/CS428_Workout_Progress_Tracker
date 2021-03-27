package edu.byu.cs428.workoutprogresstracker.presenters;

import android.view.View;

import edu.byu.cs428.workoutprogresstracker.models.Exercise;
import edu.byu.cs428.workoutprogresstracker.services.ExercisesService;

public class ExercisePresenter {
    ExercisesService service = new ExercisesService();
    public ExercisePresenter() {}

    public Exercise loadExercise (int id) {
        //return service.loadExercise(id);
        Exercise exercise1 = new Exercise("exercise 1", null, null, null, null);
        exercise1.setId(1);
        return exercise1;
    }

    public void saveExercise(Exercise exercise){
        //service.saveExercise(exercise);
    }


}
