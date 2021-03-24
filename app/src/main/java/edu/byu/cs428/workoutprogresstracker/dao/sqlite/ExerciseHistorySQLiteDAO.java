package edu.byu.cs428.workoutprogresstracker.dao.sqlite;

import edu.byu.cs428.workoutprogresstracker.dao.DataAccessException;
import edu.byu.cs428.workoutprogresstracker.dao.ExerciseHistoryDAO;
import edu.byu.cs428.workoutprogresstracker.models.metric.Metric;

public class ExerciseHistorySQLiteDAO implements ExerciseHistoryDAO {
    @Override
    public void addNewGoalReached(int exerciseId, Metric goal) throws DataAccessException {

    }

    @Override
    public void getLastGoal(int exerciseId) throws DataAccessException {

    }
}
