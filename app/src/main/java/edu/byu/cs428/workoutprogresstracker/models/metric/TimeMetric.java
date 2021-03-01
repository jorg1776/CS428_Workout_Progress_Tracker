package edu.byu.cs428.workoutprogresstracker.models.metric;

public class TimeMetric extends Metric{
    Float value;
    String units;

    public TimeMetric() {
        this.name = "Time";
        this.units = "min";
    }

    String[] getPossibleUnits() {
        return new String[]{"hr", "min", "sec"};
    }

    public void setUnits(String units) {
        this.units = units;
    }
}
