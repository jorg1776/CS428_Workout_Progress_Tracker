package edu.byu.cs428.workoutprogresstracker.services;

import java.util.List;

import edu.byu.cs428.workoutprogresstracker.dao.DAOFactory;
import edu.byu.cs428.workoutprogresstracker.dao.DataAccessException;
import edu.byu.cs428.workoutprogresstracker.dao.WorkoutsDAO;
import edu.byu.cs428.workoutprogresstracker.dao.WorkoutsExercisesDAO;
import edu.byu.cs428.workoutprogresstracker.models.Exercise;
import edu.byu.cs428.workoutprogresstracker.models.Workout;

public class WorkoutsService implements WorkoutsDAO, WorkoutsExercisesDAO {
    private final WorkoutsDAO workoutsDAO = DAOFactory.getWorkoutsDAO();
    private final WorkoutsExercisesDAO workoutsExercisesDAO = DAOFactory.getWorkoutsExercisesDAO();

    @Override
    public void createWorkout(Workout workout) throws DataAccessException {
        workoutsDAO.createWorkout(workout);
    }

    @Override
    public Workout loadWorkout(int workoutId) throws DataAccessException {
        Workout workout = workoutsDAO.loadWorkout(workoutId);
        workout.setExercises(getWorkoutExercises(workoutId));
        return workout;
    }

    @Override
    public void saveWorkout(Workout workout) throws DataAccessException {
        workoutsDAO.saveWorkout(workout);
    }

    @Override
    public void deleteWorkout(int workoutId) throws DataAccessException {
        workoutsDAO.deleteWorkout(workoutId);
    }

    @Override
    public List<Workout> loadWorkoutsList(String type, int count, int lastWorkout) throws DataAccessException {
        return workoutsDAO.loadWorkoutsList(type, count, lastWorkout);
    }

    @Override
    public void addExerciseToWorkout(int exerciseId, int workoutId) throws DataAccessException {
        workoutsExercisesDAO.addExerciseToWorkout(exerciseId, workoutId);
    }

    @Override
    public void removeExerciseFromWorkout(int exerciseId, int workoutId) throws DataAccessException {
        workoutsExercisesDAO.removeExerciseFromWorkout(exerciseId, workoutId);
    }

    @Override
    public List<Exercise> getWorkoutExercises(int workoutId) throws DataAccessException {
        return workoutsExercisesDAO.getWorkoutExercises(workoutId);
    }
}
