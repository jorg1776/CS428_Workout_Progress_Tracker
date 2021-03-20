package edu.byu.cs428.workoutprogresstracker.views.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs428.workoutprogresstracker.presenters.ExercisePresenter;
import edu.byu.cs428.workoutprogresstracker.presenters.NewExercisePresenter;
import edu.byu.cs428.workoutprogresstracker.services.requests.NewExerciseRequest;
import edu.byu.cs428.workoutprogresstracker.services.responses.ExercisesResponse;
import edu.byu.cs428.workoutprogresstracker.services.responses.NewExerciseResponse;


public class NewExerciseTask extends AsyncTask<NewExerciseRequest, Void, NewExerciseResponse> {
    NewExercisePresenter presenter;

    public NewExerciseTask(NewExercisePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected NewExerciseResponse doInBackground(NewExerciseRequest... newExerciseRequests) {
        NewExerciseResponse response = null;

        response = presenter.save(newExerciseRequests[0]);

        return response;
    }
}
