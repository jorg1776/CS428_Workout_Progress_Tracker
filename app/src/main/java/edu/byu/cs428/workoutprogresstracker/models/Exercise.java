package edu.byu.cs428.workoutprogresstracker.models;

import java.util.List;

import edu.byu.cs428.workoutprogresstracker.models.metric.Metric;

public class Exercise {
    private int id;
    private String name;
    private Metric objective;
    private Metric goal;
    private List<History> history;
    private String muscleGroup;

    public Exercise(String name, Metric objective, Metric goal, List<History> history, String muscleGroup) {
        this.name = name;
        this.objective = objective;
        this.goal = goal;
        this.history = history;
        this.muscleGroup = muscleGroup;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public List<History> getHistory() {
        return history;
    }

    public void setMuscleGroup(String muscleGroup) { this.muscleGroup = muscleGroup; }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void updateObjectiveMetric(Metric objective) {
        this.objective = objective;
    }

    public void updateObjectiveValue(Number value) {
        this.objective.updateValue(value);
    }

    public void updateGoalMetric(Metric goal) {
        this.goal = goal;
    }

    public void updateGoalValue(Number value) {
        this.goal.updateValue(value);
    }
}
