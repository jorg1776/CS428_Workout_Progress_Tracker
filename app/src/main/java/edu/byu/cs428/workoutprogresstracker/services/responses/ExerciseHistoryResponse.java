package edu.byu.cs428.workoutprogresstracker.services.responses;

import java.util.List;

import edu.byu.cs428.workoutprogresstracker.models.Exercise;

public class ExerciseHistoryResponse {
    private List<Exercise> exercises;
    private boolean hasMorePages;
    private Exercise exercise;

    public ExerciseHistoryResponse(Exercise exercise, boolean hasMorePages) {
        this.hasMorePages = hasMorePages;
        this.exercise = exercise;
    }

    public ExerciseHistoryResponse(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public boolean isHasMorePages() { return hasMorePages; }

    public Exercise getExercise() {return exercise; }
}
