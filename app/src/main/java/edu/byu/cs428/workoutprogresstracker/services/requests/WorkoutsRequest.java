package edu.byu.cs428.workoutprogresstracker.services.requests;

import edu.byu.cs428.workoutprogresstracker.models.Workout;

public class WorkoutsRequest {
    private int limit;
    private Workout lastWorkout;

    public WorkoutsRequest(int limit, Workout lastWorkout) {
        this.limit = limit;
        this.lastWorkout = lastWorkout;
    }

    public int getLimit() {
        return limit;
    }

    public Workout getLastWorkout() {
        return lastWorkout;
    }
}
