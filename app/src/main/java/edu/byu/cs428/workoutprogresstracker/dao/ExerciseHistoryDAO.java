package edu.byu.cs428.workoutprogresstracker.dao;

import java.util.List;

import edu.byu.cs428.workoutprogresstracker.models.History;
import edu.byu.cs428.workoutprogresstracker.models.metric.Metric;

public interface ExerciseHistoryDAO {
    List<History> getExerciseHistory(int exerciseId) throws DataAccessException;
    void addNewGoalReached(int exerciseId, Metric goal) throws DataAccessException;
    History getLastGoal(int exerciseId) throws DataAccessException;
}
