package edu.byu.cs428.workoutprogresstracker.models.metric;

public class InvalidMetricTypeException extends Exception {
    public InvalidMetricTypeException() {
        super("Invalid metric");
    }
}
