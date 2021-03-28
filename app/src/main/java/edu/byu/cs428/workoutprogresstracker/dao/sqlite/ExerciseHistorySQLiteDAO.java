package edu.byu.cs428.workoutprogresstracker.dao.sqlite;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.byu.cs428.workoutprogresstracker.dao.DataAccessException;
import edu.byu.cs428.workoutprogresstracker.dao.ExerciseHistoryDAO;
import edu.byu.cs428.workoutprogresstracker.models.History;
import edu.byu.cs428.workoutprogresstracker.models.metric.Metric;
import edu.byu.cs428.workoutprogresstracker.models.metric.MetricFactory;

public class ExerciseHistorySQLiteDAO implements ExerciseHistoryDAO {
    private final SQLiteDAO dao = SQLiteDAO.getInstance();

    @Override
    public List<History> getExerciseHistory(int exerciseId) throws DataAccessException {
        try {
            List<History> historyList = new ArrayList<>();
            String query = "SELECT * FROM exercise_history WHERE exercise_id=?";
            String[] whereArgs = new String[]{ Integer.toString(exerciseId) };

            Cursor cursor = dao.executeQuery(query, whereArgs);

            while (cursor.moveToNext()) {
                String goalName = cursor.getString(cursor.getColumnIndex("goal_name"));
                Double goalValue = cursor.getDouble(cursor.getColumnIndex("goal_value"));
                String goalUnits = cursor.getString(cursor.getColumnIndex("goal_units"));
                int timestamp = cursor.getInt(cursor.getColumnIndex("timestamp"));

                Metric goal = MetricFactory.createMetric(goalName, goalValue, goalUnits);
                historyList.add(new History(goal, new Date(timestamp)));
            }

            return historyList;
        } catch (Exception e) {
            throw new DataAccessException("Error getting history for exercise");
        }
    }

    @Override
    public void addNewGoalReached(int exerciseId, Metric goal) throws DataAccessException {
        ContentValues contentValues = new ContentValues();
        contentValues.put("exercise_id", exerciseId);
        contentValues.put("goal_name", goal.getName());
        contentValues.put("goal_value", goal.getValue().doubleValue());
        contentValues.put("goal_units", goal.getUnits());
        contentValues.put("timestamp", System.currentTimeMillis());

        try {
            dao.executeInsert("exercise_history", contentValues);
        } catch (Exception e) {
            throw new DataAccessException("Error updating exercise history");
        }
    }

    @Override
    public History getLastGoal(int exerciseId) throws DataAccessException {
        try {
            String query = "SELECT goal_name, goal_value, goal_units, MAX(timestamp) " +
            "FROM exercise_history WHERE exercise_id=?";
            String[] whereArgs = new String[]{ Integer.toString(exerciseId) };

            Cursor cursor = dao.executeQuery(query, whereArgs);

            if (cursor.getCount() > 0) {
                String goalName = cursor.getString(cursor.getColumnIndex("goal_name"));
                Double goalValue = cursor.getDouble(cursor.getColumnIndex("goal_value"));
                String goalUnits = cursor.getString(cursor.getColumnIndex("goal_units"));

                Metric goal = MetricFactory.createMetric(goalName, goalValue, goalUnits);

                return new History(goal);
            }
            else {
                throw new DataAccessException("Error getting most recent exercise goal");
            }
        } catch (Exception e) {
            throw new DataAccessException("Error getting most recent exercise goal");
        }
    }
}
