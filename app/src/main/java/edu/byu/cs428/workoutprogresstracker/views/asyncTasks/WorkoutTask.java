package edu.byu.cs428.workoutprogresstracker.views.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs428.workoutprogresstracker.presenters.ExercisesListPresenter;
import edu.byu.cs428.workoutprogresstracker.presenters.WorkoutsListPresenter;
import edu.byu.cs428.workoutprogresstracker.services.requests.ExercisesRequest;
import edu.byu.cs428.workoutprogresstracker.services.requests.WorkoutsRequest;
import edu.byu.cs428.workoutprogresstracker.services.responses.ExercisesResponse;
import edu.byu.cs428.workoutprogresstracker.services.responses.WorkoutsResponse;

public class WorkoutTask extends AsyncTask<WorkoutsRequest, Void, WorkoutsResponse> {
    private final WorkoutsListPresenter presenter;
    private final WorkoutTask.Observer observer;
    private Exception exception;

    public interface Observer {
        void workoutRetrieved(WorkoutsResponse workoutResponse);
        void handleException(Exception exception);
    }

    public WorkoutTask(WorkoutsListPresenter presenter, WorkoutTask.Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected WorkoutsResponse doInBackground(WorkoutsRequest... workoutRequests) {

        WorkoutsResponse response = null;

        response = presenter.loadWorkouts(workoutRequests[0]);

        return response;
    }


    protected void onPostExecute(WorkoutsResponse workoutsResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else {
            observer.workoutRetrieved(workoutsResponse);
        }
    }
}
