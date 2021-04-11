package edu.byu.cs428.workoutprogresstracker.views.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs428.workoutprogresstracker.dao.DataAccessException;
import edu.byu.cs428.workoutprogresstracker.models.Exercise;
import edu.byu.cs428.workoutprogresstracker.presenters.ExercisePresenter;
import edu.byu.cs428.workoutprogresstracker.presenters.ExercisesListPresenter;
import edu.byu.cs428.workoutprogresstracker.services.requests.ExerciseHistoryRequest;
import edu.byu.cs428.workoutprogresstracker.services.requests.ExercisesRequest;
import edu.byu.cs428.workoutprogresstracker.services.responses.ExerciseHistoryResponse;
import edu.byu.cs428.workoutprogresstracker.services.responses.ExercisesResponse;

public class ExerciseHistoryTask extends AsyncTask<ExerciseHistoryRequest, Void, ExerciseHistoryResponse> {
    private final ExercisePresenter presenter;
    private final Observer observer;
    private Exception exception;

    public interface Observer {
        void exerciseRetrieved(ExerciseHistoryResponse exerciseHistoryResponse);
        void handleException(Exception exception);
    }

    public ExerciseHistoryTask(ExercisePresenter presenter, Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected ExerciseHistoryResponse doInBackground(ExerciseHistoryRequest... exerciseHistoryRequests) {

        ExerciseHistoryResponse response = null;

        try {
            int id = exerciseHistoryRequests[0].getId();
            response = new ExerciseHistoryResponse(presenter.loadExercise(id), false);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return response;
    }


    protected void onPostExecute(ExerciseHistoryResponse exerciseHistoryResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else {
            observer.exerciseRetrieved(exerciseHistoryResponse);
        }
    }
}
