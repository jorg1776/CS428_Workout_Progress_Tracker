package edu.byu.cs428.workoutprogresstracker.views;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.byu.cs428.workoutprogresstracker.R;
import edu.byu.cs428.workoutprogresstracker.models.Exercise;
import edu.byu.cs428.workoutprogresstracker.models.Workout;
import edu.byu.cs428.workoutprogresstracker.presenters.ExercisePresenter;
import edu.byu.cs428.workoutprogresstracker.presenters.ExercisesListPresenter;
import edu.byu.cs428.workoutprogresstracker.presenters.WorkoutPresenter;
import edu.byu.cs428.workoutprogresstracker.services.requests.ExercisesRequest;
import edu.byu.cs428.workoutprogresstracker.services.responses.ExercisesResponse;
import edu.byu.cs428.workoutprogresstracker.views.asyncTasks.ExerciseTask;

public class NewWorkoutDialog extends DialogFragment implements AdapterView.OnItemSelectedListener {
    private EditText workoutName;
    private Button createButton;
    private String selectedMuscleGroup;
    private List<Exercise> addedExercises = new ArrayList<>();
    private ExerciseRecyclerViewAdapter exerciseRecyclerViewAdapter;
    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;
    ExercisesListPresenter eListPresenter;
    ExercisePresenter ePresenter;
    private static final String LOG_TAG = "WorkoutDialog";
    private static final int PAGE_SIZE = 100;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_new_workout, container, false);

        eListPresenter = new ExercisesListPresenter();
        ePresenter = new ExercisePresenter();

        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        List<String> muscleGroups = new ArrayList<String>();
        muscleGroups.add("Abs");
        muscleGroups.add("Back");
        muscleGroups.add("Biceps");
        muscleGroups.add("Cardio");
        muscleGroups.add("Chest");
        muscleGroups.add("Legs");
        muscleGroups.add("Shoulders");
        muscleGroups.add("Triceps");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, muscleGroups);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        //set up the recycler view of the exercises
        RecyclerView exerciseRecyclerView = view.findViewById(R.id.exerciseRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        exerciseRecyclerView.setLayoutManager(layoutManager);
        exerciseRecyclerViewAdapter = new ExerciseRecyclerViewAdapter();
        exerciseRecyclerView.setAdapter(exerciseRecyclerViewAdapter);
        exerciseRecyclerView.addOnScrollListener(new ExerciseRecyclerViewPaginationScrollListener(layoutManager));

        workoutName = view.findViewById(R.id.workout_title_input);
        createButton = view.findViewById(R.id.create_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = workoutName.getText().toString();

                if(name.equals("") || selectedMuscleGroup == null) {
                    Toast toast=Toast.makeText(getActivity(),"Missing Information",Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                //FIX THE INT BEING PASSED HERE
                Workout workout = new Workout(name, null, selectedMuscleGroup);
                //save the created workout
                WorkoutPresenter presenter = new WorkoutPresenter();
                presenter.saveWorkout(workout);

                getDialog().dismiss();
            }
        });
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedMuscleGroup = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //////////////////// EXERCISE RECYCLER METHODS ///////////////////////////////

    private class ExerciseHolder extends RecyclerView.ViewHolder {

        private final TextView exerciseName;
        private final TextView exerciseID;

        ExerciseHolder(@NonNull final View itemView, int viewType) {
            super(itemView);

            if(viewType == ITEM_VIEW) {
                exerciseName = itemView.findViewById(R.id.exercise_name);
                exerciseID = itemView.findViewById(R.id.exercise_id);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("UseCompatLoadingForDrawables")
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "You selected '" + exerciseName.getText() + "'.", Toast.LENGTH_SHORT).show();
                        //FIX! SHOW THAT AN EXERCISE HAS BEEN SELECTED/DESELECTED

                        //view.setBackground(getResources().getDrawable(R.drawable.exercise_background_selected));

                        //get the selected exercise
                        Exercise selectedExercise = ePresenter.loadExercise(Integer.parseInt(exerciseID.getText().toString()));
                        addedExercises.add(selectedExercise);

                    }
                });
            } else {
                exerciseName = null;
                exerciseID = null;
            }
        }


        /**
         * Binds the exercise data to the view.
         */
        void bindExercise(Exercise exercise) {
            exerciseName.setText(exercise.getName());
            exerciseID.setText(String.valueOf(exercise.getId()));
        }
    }

    /**
     * The adapter for the RecyclerView that displays the Exercises.
     */
    private class ExerciseRecyclerViewAdapter extends RecyclerView.Adapter<ExerciseHolder> implements ExerciseTask.Observer {

        private final List<Exercise> exercises = new ArrayList<>();

        private Exercise lastExercise;

        private boolean hasMorePages;
        private boolean isLoading = false;

        /**
         * Creates an instance and loads the first page of exercise data.
         */
        ExerciseRecyclerViewAdapter() {
            loadMoreItems();
        }

        /**
         * Adds new exercises to the list from which the RecyclerView retrieves the users it displays
         * and notifies the RecyclerView that items have been added.
         */
        void addItems(List<Exercise> newExercises) {
            int startInsertPosition = exercises.size();
            exercises.addAll(newExercises);
            this.notifyItemRangeInserted(startInsertPosition, newExercises.size());
        }

        /**
         * Adds a single exercise to the list from which the RecyclerView retrieves the exercises it
         * displays and notifies the RecyclerView that an item has been added.
         */
        void addItem(Exercise exercise) {
            exercises.add(exercise);
            this.notifyItemInserted(exercises.size() - 1);
        }

        /**
         * Removes a exercises from the list from which the RecyclerView retrieves the exercises it displays
         * and notifies the RecyclerView that an item has been removed.
         */
        void removeItem(Exercise exercise) {
            int position = exercises.indexOf(exercise);
            exercises.remove(position);
            this.notifyItemRemoved(position);
        }

        /**
         *  Creates a view holder for an exercise to be displayed in the RecyclerView or for a message
         *  indicating that new rows are being loaded if we are waiting for rows to load.
         */
        @NonNull
        @Override
        public ExerciseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(NewWorkoutDialog.this.getContext());
            View view;

            if(viewType == LOADING_DATA_VIEW) {
                view =layoutInflater.inflate(R.layout.loading_row, parent, false);

            } else {
                view = layoutInflater.inflate(R.layout.exercise_row, parent, false);
            }

            return new ExerciseHolder(view, viewType);
        }

        @Override
        public void onBindViewHolder(@NonNull ExerciseHolder exerciseHolder, int position) {
            if(!isLoading) {
                exerciseHolder.bindExercise(exercises.get(position));
            }
        }

        /**
         * Returns the type of the view that should be displayed for the item currently at the
         * specified position.
         *
         * @param position the position of the items whose view type is to be returned.
         * @return the view type.
         */
        @Override
        public int getItemViewType(int position) {
            return (position == exercises.size() - 1 && isLoading) ? LOADING_DATA_VIEW : ITEM_VIEW;
        }

        @Override
        public int getItemCount() {
            return exercises.size();
        }

        /**
         * Causes the Adapter to display a loading footer and make a request to get more following
         * data.
         */
        void loadMoreItems() {
            isLoading = true;
            addLoadingFooter();

            ExerciseTask eTask = new ExerciseTask(eListPresenter, this);
            ExercisesRequest request = new ExercisesRequest(selectedMuscleGroup, PAGE_SIZE, exercises.get(exercises.size() - 1).getId());
            eTask.execute(request);
            //presenter.loadExercises(selectedMuscleGroup, PAGE_SIZE, exercises.get(exercises.size() - 1).getId());
            //presenter.loadExercises(request);
        }

        @Override
        public void exerciseRetrieved(ExercisesResponse exercisesResponse) {
            List<Exercise> exercises = exercisesResponse.getExercises();


            //lastExercise = (exercises.size() > 0) ? exercises.get(exercises.size() -1) : null;
            //hasMorePages = exercisesResponse.isHasMorePages();

            isLoading = false;
            removeLoadingFooter();
            exerciseRecyclerViewAdapter.addItems(exercises);
        }

        /**
         * A callback indicating that an exception was thrown by the presenter.
         *
         * @param exception the exception.
         */
        @Override
        public void handleException(Exception exception) {
            Log.e(LOG_TAG, exception.getMessage(), exception);
            removeLoadingFooter();
            Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * Adds a dummy exercise to the list of exercises so the RecyclerView will display a view (the
         * loading footer view) at the bottom of the list.
         */
        private void addLoadingFooter() {
            Exercise exercise = new Exercise("Dummy exercise", null, null,null, null);
            exercise.setId(0);
            addItem(exercise);
        }

        /**
         * Removes the dummy exercise from the list of exercises so the RecyclerView will stop displaying
         * the loading footer at the bottom of the list.
         */
        private void removeLoadingFooter() {
            removeItem(exercises.get(exercises.size() - 1));
        }
    }

    /**
     * A scroll listener that detects when the user has scrolled to the bottom of the currently
     * available data.
     */
    private class ExerciseRecyclerViewPaginationScrollListener extends RecyclerView.OnScrollListener {

        private LinearLayoutManager layoutManager;

        /**
         * Creates a new instance.
         *
         * @param layoutManager the layout manager being used by the RecyclerView.
         */
        ExerciseRecyclerViewPaginationScrollListener(LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        private ExerciseRecyclerViewPaginationScrollListener() {
        }

        /**
         * Determines whether the user has scrolled to the bottom of the currently available data
         * in the RecyclerView and asks the adapter to load more data if the last load request
         * indicated that there was more data to load.
         *
         * @param recyclerView the RecyclerView.
         * @param dx the amount of horizontal scroll.
         * @param dy the amount of vertical scroll.
         */
        @Override
        public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!exerciseRecyclerViewAdapter.isLoading && exerciseRecyclerViewAdapter.hasMorePages) {
                if ((visibleItemCount + firstVisibleItemPosition) >=
                        totalItemCount && firstVisibleItemPosition >= 0) {
                    exerciseRecyclerViewAdapter.loadMoreItems();
                }
            }
        }
    }
}
