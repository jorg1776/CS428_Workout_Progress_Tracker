package edu.byu.cs428.workoutprogresstracker.services.requests;

public class NewExerciseRequest {
    private String name;
    private String metric;
    private String muscleGroup;

    public NewExerciseRequest(String name, String metric, String muscleGroup) {
        this.name = name;
        //metric will either be a string "time" or "weight"
        this.metric = metric;
        this.muscleGroup = muscleGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }
}
