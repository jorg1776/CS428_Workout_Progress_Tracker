package edu.byu.cs428.workoutprogresstracker.services;

import java.util.List;

import edu.byu.cs428.workoutprogresstracker.dao.DAOFactory;
import edu.byu.cs428.workoutprogresstracker.dao.DataAccessException;
import edu.byu.cs428.workoutprogresstracker.dao.ExerciseHistoryDAO;
import edu.byu.cs428.workoutprogresstracker.dao.ExercisesDAO;
import edu.byu.cs428.workoutprogresstracker.models.Exercise;
import edu.byu.cs428.workoutprogresstracker.models.metric.Metric;


public class ExercisesService implements ExercisesDAO, ExerciseHistoryDAO {

    private final ExercisesDAO exercisesDAO = DAOFactory.getExercisesDAO();

    @Override
    public void createExercise(Exercise exercise) throws DataAccessException {
        exercisesDAO.createExercise(exercise);
    }

    public Exercise loadExercise(int exerciseId) throws DataAccessException {
        return exercisesDAO.loadExercise(exerciseId);
    }

    public void saveExercise(Exercise exercise) throws DataAccessException {
        exercisesDAO.saveExercise(exercise);
    }

    @Override
    public void deleteExercise(int exerciseId) throws DataAccessException {
        exercisesDAO.deleteExercise(exerciseId);
    }

    @Override
    public List<Exercise> loadExercisesList(String muscleGroup) throws DataAccessException {
        return exercisesDAO.loadExercisesList(muscleGroup);
    }

    @Override
    public void addNewGoalReached(int exerciseId, Metric goal) throws DataAccessException {

    }

    @Override
    public void getLastGoal(int exerciseId) throws DataAccessException {

    }
}
