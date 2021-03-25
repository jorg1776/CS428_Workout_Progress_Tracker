package edu.byu.cs428.workoutprogresstracker.dao;

import edu.byu.cs428.workoutprogresstracker.dao.sqlite.ExerciseHistorySQLiteDAO;
import edu.byu.cs428.workoutprogresstracker.dao.sqlite.ExercisesSQLiteDAO;
import edu.byu.cs428.workoutprogresstracker.dao.sqlite.WorkoutsExercisesSQLiteDAO;
import edu.byu.cs428.workoutprogresstracker.dao.sqlite.WorkoutsSQLiteDAO;

public class DAOFactory {
    public static ExercisesDAO getExercisesDAO() {
        return new ExercisesSQLiteDAO();
    }

    public static ExerciseHistoryDAO getExerciseHistoryDAO() {
        return new ExerciseHistorySQLiteDAO();
    }

    public static WorkoutsDAO getWorkoutsDAO() {
        return new WorkoutsSQLiteDAO();
    }

    public static WorkoutsExercisesDAO getWorkoutsExercisesDAO() {
        return new WorkoutsExercisesSQLiteDAO();
    }
}
