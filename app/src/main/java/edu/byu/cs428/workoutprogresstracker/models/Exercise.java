package edu.byu.cs428.workoutprogresstracker.models;

import java.util.List;
import java.util.UUID;

import edu.byu.cs428.workoutprogresstracker.models.metric.Metric;

class Exercise {
    UUID id;
    String name;
    Metric objective;
    Metric goal;
    List<History> history;
    String muscleGroup;

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
