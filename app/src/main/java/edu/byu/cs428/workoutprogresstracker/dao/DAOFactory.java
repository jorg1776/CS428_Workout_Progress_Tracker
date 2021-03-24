package edu.byu.cs428.workoutprogresstracker.dao;

import edu.byu.cs428.workoutprogresstracker.dao.sqlite.ExercisesSQLiteDAO;
import edu.byu.cs428.workoutprogresstracker.dao.sqlite.WorkoutsSQLiteDAO;

public class DAOFactory {
    public static ExercisesDAO getExercisesDAO() {
        return new ExercisesSQLiteDAO();
    }

    public static WorkoutsDAO getWorkoutsDAO() {
        return new WorkoutsSQLiteDAO();
    }
}
