package edu.byu.cs428.workoutprogresstracker.models.metric;

public class DistanceMetric extends Metric{
    Float value;
    String units;
    String name;

    public DistanceMetric() {
        super("distance", 0, "mi");
    }

    public DistanceMetric(Double value, String units) {
        super("distance", value, units);
    }

    @Override
    public String[] getPossibleUnits() {
        return new String[]{"mi", "km"};
    }
}
