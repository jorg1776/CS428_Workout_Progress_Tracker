package edu.byu.cs428.workoutprogresstracker.models.metric;

public class TimeMetric extends Metric {
    public TimeMetric() {
        super("time", 0, "sec");
    }

    public TimeMetric(Double value, String units) {
        super("time", value, units);
    }

    @Override
    public String[] getPossibleUnits() {
        return new String[]{ "hr", "min", "sec" };
    }
}
