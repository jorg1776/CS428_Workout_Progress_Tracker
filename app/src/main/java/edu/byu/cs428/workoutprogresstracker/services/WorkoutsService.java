package edu.byu.cs428.workoutprogresstracker.services;

import java.util.List;

import edu.byu.cs428.workoutprogresstracker.dao.DAOFactory;
import edu.byu.cs428.workoutprogresstracker.dao.DataAccessException;
import edu.byu.cs428.workoutprogresstracker.dao.WorkoutsDAO;
import edu.byu.cs428.workoutprogresstracker.models.Workout;

public class WorkoutsService {
    private final WorkoutsDAO workoutsDAO = DAOFactory.getWorkoutsDAO();

    public Workout loadWorkout(int workoutID) throws DataAccessException {
        return workoutsDAO.loadWorkout(workoutID);
    }

    public void saveWorkout(Workout workout) throws DataAccessException {
        workoutsDAO.saveWorkout(workout);
    }

    public List<Workout> loadWorkoutsList(String type, int count, int lastWorkout) {
        return workoutsDAO.loadWorkoutsList(type, count, lastWorkout);
    }
}
