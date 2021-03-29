package edu.byu.cs428.workoutprogresstracker.models.metric;

public abstract class Metric {
    public String name;
    private Number value = 0;
    private String units;

    public Metric(String name, Number value, String units) {
        this.name = name;
        this.value = value;
        this.units = units;
    }

    public abstract String[] getPossibleUnits();


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
