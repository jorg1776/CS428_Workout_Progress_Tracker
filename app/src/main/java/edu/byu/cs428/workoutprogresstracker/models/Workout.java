package edu.byu.cs428.workoutprogresstracker.models;
import java.util.List;
import java.util.UUID;

public class Workout {
    UUID id;
    String name;
    List<Exercise> history;
    String muscleGroup;

    public Workout(UUID id, String name, List<Exercise> history, String muscleGroup){
        this.id = id;
        this.name = name;
        this.history = history;
        this.muscleGroup = muscleGroup;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Exercise> getHistory() {
        return history;
    }

    public void setHistory(List<Exercise> history) {
        this.history = history;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }
}
