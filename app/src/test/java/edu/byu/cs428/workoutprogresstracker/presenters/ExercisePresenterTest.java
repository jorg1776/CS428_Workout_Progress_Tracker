package edu.byu.cs428.workoutprogresstracker.presenters;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs428.workoutprogresstracker.models.Exercise;
import edu.byu.cs428.workoutprogresstracker.services.ExerciseService;
import edu.byu.cs428.workoutprogresstracker.services.requests.ExercisesRequest;
import edu.byu.cs428.workoutprogresstracker.services.responses.ExercisesResponse;

import static org.junit.Assert.*;

public class ExercisePresenterTest {
    private ExercisesRequest request;
    private ExercisesResponse response;
    private ExerciseService mockExerciseService;
    private ExercisePresenter presenter;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        Exercise exercise1 = new Exercise();
        exercise1.setName("Russian twists");
        Exercise exercise2 = new Exercise();
        exercise2.setName("Bench press");
        Exercise exercise3 = new Exercise();
        exercise3.setName("Goblet squat");

        request = new ExercisesRequest(3, null);
        response = new ExercisesResponse(Arrays.asList(exercise1, exercise2, exercise3), false);

        mockExerciseService = Mockito.mock(ExerciseService.class);
        Mockito.when(mockExerciseService.getExercises(request)).thenReturn(response);

        presenter = Mockito.spy(new ExercisePresenter(new ExercisePresenter.View() {}));
        Mockito.when(presenter.getExerciseService()).thenReturn(mockExerciseService);
    }

    @Test
    public void getExercise_returnsServiceResult() {
        Mockito.when(mockExerciseService.getExercises(request)).thenReturn(response);
        assertEquals(response, presenter.getExercise(request));
    }

}