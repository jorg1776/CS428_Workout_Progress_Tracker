package edu.byu.cs428.workoutprogresstracker.models;


import java.util.Date;

import edu.byu.cs428.workoutprogresstracker.models.metric.Metric;

public class History {
    Metric goal;
    Date timestamp;

    public Metric getGoal() {
        return goal;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
