package edu.byu.cs428.workoutprogresstracker.services.requests;

import edu.byu.cs428.workoutprogresstracker.models.Exercise;

public class ExercisesRequest {
    private int limit;
    private Exercise lastExercise;

    public ExercisesRequest(int limit, Exercise lastExercise) {
        this.limit = limit;
        this.lastExercise = lastExercise;
    }

    public int getLimit() {
        return limit;
    }

    public Exercise getLastExercise() {
        return lastExercise;
    }
}
