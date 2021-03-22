package edu.byu.cs428.workoutprogresstracker.models;

import java.util.List;
import java.util.UUID;

import edu.byu.cs428.workoutprogresstracker.models.metric.Metric;

public class Exercise {
    UUID id;
    String name;
    Metric objective;
    Metric goal;
    List<History> history;
    String muscleGroup;

    public UUID getId() {
        return id;
    }

    public List<History> getHistory() {
        return history;
    }

    public void setMuscleGroup(String muscleGroup) { this.muscleGroup = muscleGroup; }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public Exercise() {
        this.id = UUID.randomUUID();
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
