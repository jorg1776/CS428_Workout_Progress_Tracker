package edu.byu.cs428.workoutprogresstracker.models.metric;

public class WeightMetric extends Metric{
    Float value;
    String units;

    public WeightMetric() {
        this.name = "Weight";
        this.units = "lb";
    }

    String[] getPossibleUnits() {
        return new String[]{"lb", "kg"};
    }

    public void setUnits(String units) {
        this.units = units;
    }
}
