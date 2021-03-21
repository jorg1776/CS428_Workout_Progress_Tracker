package edu.byu.cs428.workoutprogresstracker.models;
import java.util.List;

public class Workout {
    private final int id;
    private String name;
    private List<Exercise> history;
    private String muscleGroup;

    public Workout(int id, String name, List<Exercise> history, String muscleGroup){
        this.id = id;
        this.name = name;
        this.history = history;
        this.muscleGroup = muscleGroup;
    }

    public int getId() {
        return id;
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
