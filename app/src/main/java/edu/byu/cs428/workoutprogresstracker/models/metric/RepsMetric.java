package edu.byu.cs428.workoutprogresstracker.models.metric;

public class RepsMetric extends Metric {
    public RepsMetric() {
        super("reps", 0, "count");
    }

    public RepsMetric(int value) {
        super("reps", value, "count");
    }

    @Override
    public String[] getPossibleUnits() {
        return new String[]{ "count" };
    }
}
