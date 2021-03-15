package edu.byu.cs428.workoutprogresstracker.services.responses;

import java.util.List;

import edu.byu.cs428.workoutprogresstracker.models.Exercise;

public class ExercisesResponse {
    private List<Exercise> exercises;
    private boolean hasMorePages;

    public ExercisesResponse(List<Exercise> exercises, boolean hasMorePages) {
        this.hasMorePages = hasMorePages;
        this.exercises = exercises;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public boolean isHasMorePages() { return hasMorePages; }
}
