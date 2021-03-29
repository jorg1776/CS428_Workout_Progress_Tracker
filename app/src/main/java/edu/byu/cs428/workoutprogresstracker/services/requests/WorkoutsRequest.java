package edu.byu.cs428.workoutprogresstracker.services.requests;

import edu.byu.cs428.workoutprogresstracker.models.Workout;

public class WorkoutsRequest {
    private int count;
    private int lastWorkout;
    private String muscleGroup;

    public WorkoutsRequest(String muscleGroup, int count, int lastWorkout) {
        this.muscleGroup = muscleGroup;
        this.count = count;
        this.lastWorkout = lastWorkout;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public int getCount() {
        return count;
    }

    public int getLastWorkout() {
        return lastWorkout;
    }
}
