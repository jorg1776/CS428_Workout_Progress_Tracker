package edu.byu.cs428.workoutprogresstracker.models.metric;

public abstract class Metric {
    private String name;
    private Number value = 0;

    private String units;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public Number getValue() {
        return value;
    }

    public void updateValue(Number value) {
        this.value = value;
    }
}
