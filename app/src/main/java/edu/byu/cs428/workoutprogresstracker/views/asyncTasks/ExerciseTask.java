package edu.byu.cs428.workoutprogresstracker.views.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs428.workoutprogresstracker.dao.DataAccessException;
import edu.byu.cs428.workoutprogresstracker.presenters.ExercisesListPresenter;
import edu.byu.cs428.workoutprogresstracker.services.requests.ExercisesRequest;
import edu.byu.cs428.workoutprogresstracker.services.responses.ExercisesResponse;

public class ExerciseTask extends AsyncTask<ExercisesRequest, Void, ExercisesResponse> {
    private final ExercisesListPresenter presenter;
    private final Observer observer;
    private Exception exception;

    public interface Observer {
        void exerciseRetrieved(ExercisesResponse exercisesResponse);
        void handleException(Exception exception);
    }

    public ExerciseTask(ExercisesListPresenter presenter, Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected ExercisesResponse doInBackground(ExercisesRequest... exerciseRequests) {

        ExercisesResponse response = null;

        try {
            response = presenter.loadExercises(exerciseRequests[0]);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return response;
    }


    protected void onPostExecute(ExercisesResponse exercisesResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else {
            observer.exerciseRetrieved(exercisesResponse);
        }
    }
}
