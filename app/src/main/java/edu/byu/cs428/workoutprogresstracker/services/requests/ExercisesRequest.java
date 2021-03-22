package edu.byu.cs428.workoutprogresstracker.services.requests;

import edu.byu.cs428.workoutprogresstracker.models.Exercise;

public class ExercisesRequest {
    private String muscleGroup;
    private int count;
    private int lastExercise;

    public ExercisesRequest(String muscleGroup, int count, int lastExercise) {
        this.count = count;
        this.lastExercise = lastExercise;
        this.muscleGroup = muscleGroup;
    }

    public int getCount() {
        return count;
    }

    public int getLastExercise() {
        return lastExercise;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }
}
