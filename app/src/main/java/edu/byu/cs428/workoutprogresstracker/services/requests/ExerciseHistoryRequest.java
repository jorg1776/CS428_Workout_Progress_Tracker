package edu.byu.cs428.workoutprogresstracker.services.requests;

import edu.byu.cs428.workoutprogresstracker.models.Exercise;

public class ExerciseHistoryRequest {
    int id;
    public ExerciseHistoryRequest(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
