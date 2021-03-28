package edu.byu.cs428.workoutprogresstracker;

import android.app.Application;

import edu.byu.cs428.workoutprogresstracker.dao.sqlite.SQLiteDAO;

public class WorkoutProgressTracker extends Application {
    @Override
    public void onCreate() {
        getApplicationContext().deleteDatabase("Workouts.db");
        new SQLiteDAO(getApplicationContext());
        super.onCreate();
    }
}
