package edu.byu.cs428.workoutprogresstracker.services;

import java.util.List;

import edu.byu.cs428.workoutprogresstracker.dao.DAOFactory;
import edu.byu.cs428.workoutprogresstracker.dao.DataAccessException;
import edu.byu.cs428.workoutprogresstracker.dao.WorkoutsDAO;
import edu.byu.cs428.workoutprogresstracker.dao.WorkoutsExercisesDAO;
import edu.byu.cs428.workoutprogresstracker.models.Workout;

public class WorkoutsService implements WorkoutsDAO, WorkoutsExercisesDAO {
    private final WorkoutsDAO workoutsDAO = DAOFactory.getWorkoutsDAO();
    private final WorkoutsExercisesDAO workoutsExercisesDAO = DAOFactory.getWorkoutsExercisesDAO();

    public void createWorkout(Workout workout) throws DataAccessException {
        workoutsDAO.createWorkout(workout);
    }

    public Workout loadWorkout(int workoutId) throws DataAccessException {
        return workoutsDAO.loadWorkout(workoutId);
    }

    public void saveWorkout(Workout workout) throws DataAccessException {
        workoutsDAO.saveWorkout(workout);
    }

    public void deleteWorkout(int workoutId) throws DataAccessException {
        workoutsDAO.deleteWorkout(workoutId);
    }

    public List<Workout> loadWorkoutsList(String type, int count, int lastWorkout) throws DataAccessException {
        return workoutsDAO.loadWorkoutsList(type, count, lastWorkout);
    }

    public void addExerciseToWorkout(int exerciseId, int workoutId) throws DataAccessException {
        workoutsExercisesDAO.addExerciseToWorkout(exerciseId, workoutId);
    }

    public void removeExerciseFromWorkout(int exerciseId, int workoutId) throws DataAccessException {
        workoutsExercisesDAO.removeExerciseFromWorkout(exerciseId, workoutId);
    }
}
