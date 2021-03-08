package edu.byu.cs428.workoutprogresstracker.services;

import java.util.List;
import java.util.UUID;

import edu.byu.cs428.workoutprogresstracker.dao.DAOFactory;
import edu.byu.cs428.workoutprogresstracker.dao.DataAccessException;
import edu.byu.cs428.workoutprogresstracker.dao.ExercisesDAO;
import edu.byu.cs428.workoutprogresstracker.models.Exercise;

public class ExercisesService {
    private final ExercisesDAO exercisesDAO = DAOFactory.getExercisesDAO();

    public Exercise loadExercise(UUID exerciseId) throws DataAccessException {
        return exercisesDAO.loadExercise(exerciseId);
    }

    public void saveExercise(Exercise exercise) throws DataAccessException {
        exercisesDAO.saveExercise(exercise);
    }

    public List<Exercise> loadExercisesList(String type, int count, UUID lastExercise) {
        return exercisesDAO.loadExercisesList(type, count, lastExercise);
    }
}
