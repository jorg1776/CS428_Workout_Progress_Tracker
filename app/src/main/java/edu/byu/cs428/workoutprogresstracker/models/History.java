package edu.byu.cs428.workoutprogresstracker.models;


import java.util.Date;

import edu.byu.cs428.workoutprogresstracker.models.metric.Metric;

public class History {
    private Metric goal;
    private Date timestamp;

    public History(Metric goal) {
        this.goal = goal;
    }

    public History(Metric goal, Date timestamp) {
        this.goal = goal;
        this.timestamp = timestamp;
    }

    public Metric getGoal() {
        return goal;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
