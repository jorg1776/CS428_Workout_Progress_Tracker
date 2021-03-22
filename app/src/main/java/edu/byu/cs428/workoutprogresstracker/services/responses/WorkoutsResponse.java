package edu.byu.cs428.workoutprogresstracker.services.responses;

import java.util.List;

import edu.byu.cs428.workoutprogresstracker.models.Workout;

public class WorkoutsResponse {
    private List<Workout> workouts;
    private boolean hasMorePages;

    public WorkoutsResponse(List<Workout> workouts, boolean hasMorePages) {
        this.hasMorePages = hasMorePages;
        this.workouts = workouts;
    }

    public List<Workout> getWorkouts() {
        return workouts;
    }

    public boolean isHasMorePages() { return hasMorePages; }
}
