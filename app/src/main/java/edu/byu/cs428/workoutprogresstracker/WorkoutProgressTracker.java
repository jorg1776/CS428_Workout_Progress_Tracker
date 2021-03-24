package edu.byu.cs428.workoutprogresstracker;

import android.app.Application;
import android.content.Context;

public class WorkoutProgressTracker extends Application {
    private static WorkoutProgressTracker instance;

    public static Context getAppContext() {
        return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }
}
