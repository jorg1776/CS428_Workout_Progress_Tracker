package edu.byu.cs428.workoutprogresstracker.dao;

import edu.byu.cs428.workoutprogresstracker.models.metric.Metric;

public interface ExerciseHistoryDAO {
    void addNewGoalReached(int exerciseId, Metric goal) throws DataAccessException;
    void getLastGoal(int exerciseId) throws DataAccessException;
}
