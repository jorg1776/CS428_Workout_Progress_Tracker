package edu.byu.cs428.workoutprogresstracker.models.metric;

public class MetricFactory {
    public static String[] getMetricTypes() {
        return new String[]{ "reps", "distance", "time", "weight" };
    }

    public static Metric createMetric(String name, Double value, String units) throws InvalidMetricTypeException {
        if (name.equals("reps")) {
            return new RepsMetric(value.intValue());
        }
        else if (name.equals("distance")) {
            return new DistanceMetric(value, units);
        }
        else if (name.equals("time")) {
            return new TimeMetric(value, units);
        }
        else if (name.equals("weight")) {
            return new WeightMetric(value, units);
        }
        else {
            throw new InvalidMetricTypeException();
        }
    }
}
