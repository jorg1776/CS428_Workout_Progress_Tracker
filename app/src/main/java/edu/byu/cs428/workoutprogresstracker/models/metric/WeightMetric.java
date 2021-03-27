package edu.byu.cs428.workoutprogresstracker.models.metric;

public class WeightMetric extends Metric {
    public WeightMetric() {
        super("weight", 0, "lb");
    }

    public WeightMetric(Double value, String units) {
        super("weight", value, units);
    }

    @Override
    public String[] getPossibleUnits() {
        return new String[]{"lb", "kg"};
    }
}
