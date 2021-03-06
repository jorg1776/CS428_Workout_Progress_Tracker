package edu.byu.cs428.workoutprogresstracker.presenters;

import android.view.View;

import edu.byu.cs428.workoutprogresstracker.dao.DataAccessException;
import edu.byu.cs428.workoutprogresstracker.models.Exercise;
import edu.byu.cs428.workoutprogresstracker.models.metric.Metric;
import edu.byu.cs428.workoutprogresstracker.services.ExercisesService;

public class ExercisePresenter {
    ExercisesService service = new ExercisesService();
    public ExercisePresenter() {}

    public void createExercise(Exercise exercise) throws DataAccessException {
        service.createExercise(exercise);
    }

    public Exercise loadExercise (int id) throws DataAccessException {
        return service.loadExercise(id);
    }

    public void saveExercise(Exercise exercise) throws DataAccessException {
        service.saveExercise(exercise);
    }


    public void deleteExercise(int exerciseId) throws DataAccessException {
        service.deleteExercise(exerciseId);
    }

    public void addExerciseHistory(int exerciseId, Metric metric) throws DataAccessException{
        service.addNewGoalReached(exerciseId, metric);
    }
}
