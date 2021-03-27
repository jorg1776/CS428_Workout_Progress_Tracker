package edu.byu.cs428.workoutprogresstracker.models.metric;

public class DistanceMetric extends Metric{
    Float value;
    String units;
    String name;

    public DistanceMetric() {
        this.name = "Distance";
        this.units = "mi";
    }

    String[] getPossibleUnits() {
        return new String[]{"mi", "km"};
    }

    public void setUnits(String units) {
        this.units = units;
    }
}
