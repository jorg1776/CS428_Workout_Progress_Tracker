package edu.byu.cs428.workoutprogresstracker.services;

import java.util.List;

import edu.byu.cs428.workoutprogresstracker.dao.DAOFactory;
import edu.byu.cs428.workoutprogresstracker.dao.DataAccessException;
import edu.byu.cs428.workoutprogresstracker.dao.ExerciseHistoryDAO;
import edu.byu.cs428.workoutprogresstracker.dao.ExercisesDAO;
import edu.byu.cs428.workoutprogresstracker.models.Exercise;
import edu.byu.cs428.workoutprogresstracker.models.History;
import edu.byu.cs428.workoutprogresstracker.models.metric.Metric;


public class ExercisesService implements ExercisesDAO, ExerciseHistoryDAO {

    private final ExercisesDAO exercisesDAO = DAOFactory.getExercisesDAO();
    private final ExerciseHistoryDAO exerciseHistoryDAO = DAOFactory.getExerciseHistoryDAO();

    @Override
    public void createExercise(Exercise exercise) throws DataAccessException {
        exercisesDAO.createExercise(exercise);
        addNewGoalReached(exercise.getId(), exercise.getGoal());
    }

    public Exercise loadExercise(int exerciseId) throws DataAccessException {
        Exercise exercise = exercisesDAO.loadExercise(exerciseId);
        exercise.setHistory(getExerciseHistory(exerciseId));
        return exercise;
    }

    public void saveExercise(Exercise exercise) throws DataAccessException {
        exercisesDAO.saveExercise(exercise);

        Metric currentGoal = exercise.getGoal();
        Metric lastGoal = getLastGoal(exercise.getId()).getGoal();
        if (!currentGoal.getUnits().equals(lastGoal.getUnits()) ||
                currentGoal.getValue().doubleValue() > lastGoal.getValue().doubleValue()) {
            addNewGoalReached(exercise.getId(), currentGoal);
        }
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
    public List<History> getExerciseHistory(int exerciseId) throws DataAccessException {
        return exerciseHistoryDAO.getExerciseHistory(exerciseId);
    }

    @Override
    public void addNewGoalReached(int exerciseId, Metric goal) throws DataAccessException {
        exerciseHistoryDAO.addNewGoalReached(exerciseId, goal);
    }

    @Override
    public History getLastGoal(int exerciseId) throws DataAccessException {
        return exerciseHistoryDAO.getLastGoal(exerciseId);
    }
}
