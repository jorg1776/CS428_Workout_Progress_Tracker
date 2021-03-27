package edu.byu.cs428.workoutprogresstracker.models;

import java.util.List;

import edu.byu.cs428.workoutprogresstracker.models.metric.Metric;

public class Exercise {
    private final int id;
    private String name;
    private Metric objective;
    private Metric goal;
    private List<History> history;
    private String muscleGroup;

    public Exercise() {
        this.id = 0;
    }

    public Exercise(int id, String name){
        this.id = id;
        this.name = name;
    }

    public Exercise(int id, String name, Metric objective, Metric goal, List<History> history,
                    String muscleGroup){
        this.id = id;
        this.name = name;
        this.objective = objective;
        this.goal = goal;
        this.history = history;
        this.muscleGroup = muscleGroup;
    }

    public int getId() {
        return id;
    }

    public List<History> getHistory() {
        return history;
    }

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

    public Metric getObjective() {
        return objective;
    }

    public Metric getGoal() {
        return goal;
    }
}
