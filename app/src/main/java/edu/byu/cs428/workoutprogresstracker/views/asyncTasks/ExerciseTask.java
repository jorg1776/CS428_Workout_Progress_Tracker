package edu.byu.cs428.workoutprogresstracker.views.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs428.workoutprogresstracker.models.Exercise;
import edu.byu.cs428.workoutprogresstracker.presenters.ExercisePresenter;
import edu.byu.cs428.workoutprogresstracker.services.requests.ExercisesRequest;
import edu.byu.cs428.workoutprogresstracker.services.responses.ExercisesResponse;

public class ExerciseTask extends AsyncTask<ExercisesRequest, Void, ExercisesResponse> {
    private final ExercisePresenter presenter;
    private final Observer observer;
    private Exception exception;

    public interface Observer {
        void exerciseRetrieved(Exercise exercise);
        void handleException(Exception exception);
    }

    public ExerciseTask(ExercisePresenter presenter, Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected ExercisesResponse doInBackground(ExercisesRequest... exerciseRequests) {

        ExercisesResponse response = null;

        response = presenter.getExercise(exerciseRequests[0]);

        return response;
    }


    protected void onPostExecute(Exercise exercise) {
        if(exception != null) {
            observer.handleException(exception);
        } else {
            observer.exerciseRetrieved(exercise);
        }
    }
}
