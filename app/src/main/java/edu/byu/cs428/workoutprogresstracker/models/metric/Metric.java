package edu.byu.cs428.workoutprogresstracker.models.metric;

public abstract class Metric {
    String name;
    Number value = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Number getValue() {
        return value;
    }

    public void updateValue(Number value) {
        this.value = value;
    }
}
