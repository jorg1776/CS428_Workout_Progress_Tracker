package edu.byu.cs428.workoutprogresstracker.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs428.workoutprogresstracker.R;
import edu.byu.cs428.workoutprogresstracker.models.Exercise;

import edu.byu.cs428.workoutprogresstracker.presenters.ExercisesListPresenter;
import edu.byu.cs428.workoutprogresstracker.presenters.ExercisePresenter;

import edu.byu.cs428.workoutprogresstracker.services.requests.ExercisesRequest;
import edu.byu.cs428.workoutprogresstracker.services.responses.ExercisesResponse;
import edu.byu.cs428.workoutprogresstracker.views.asyncTasks.ExerciseTask;

public class ExerciseFragment extends Fragment implements AdapterView.OnItemSelectedListener, IDialogListener {
    private ExerciseRecyclerViewAdapter exerciseRecyclerViewAdapter;
    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;
    private static final String LOG_TAG = "ExerciseFragment";
    ExercisesListPresenter presenter;
    String selectedMuscleGroup = "All";
    RecyclerView exerciseRecyclerView;

    private static final int PAGE_SIZE = 100;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.exercise_tab, container, false);

        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        List<String> muscleGroups = new ArrayList<String>();
        muscleGroups.add("All");
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

        presenter = new ExercisesListPresenter();

        exerciseRecyclerView = view.findViewById(R.id.exerciseRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        exerciseRecyclerView.setLayoutManager(layoutManager);
        exerciseRecyclerViewAdapter = new ExerciseRecyclerViewAdapter();
        exerciseRecyclerView.setAdapter(exerciseRecyclerViewAdapter);
        exerciseRecyclerView.addOnScrollListener(new ExerciseRecyclerViewPaginationScrollListener(layoutManager));

        Button addButton = view.findViewById(R.id.add_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("click");
                createExerciseBox();
            }
        });

        return view;
    }

    private void createExerciseBox () {
        NewExerciseDialog dialog = new NewExerciseDialog();
        dialog.show(getParentFragmentManager(), LOG_TAG);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedMuscleGroup = parent.getItemAtPosition(position).toString();
        exerciseRecyclerViewAdapter = new ExerciseRecyclerViewAdapter();
        exerciseRecyclerView.setAdapter(exerciseRecyclerViewAdapter);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void notifyClosed() {
        exerciseRecyclerViewAdapter = new ExerciseRecyclerViewAdapter();
        exerciseRecyclerView.setAdapter(exerciseRecyclerViewAdapter);
    }

    private void createViewExerciseBox (int exerciseId) {
        SelectedExerciseDialog dialog = new SelectedExerciseDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("id", exerciseId);
        dialog.setArguments(bundle);
        dialog.show(getParentFragmentManager(), LOG_TAG);
    }


    //////////////////// EXERCISE RECYCLER METHODS ///////////////////////////////

    private class ExerciseHolder extends RecyclerView.ViewHolder {

        private final TextView exerciseName;
        private final TextView exerciseID;

        ExerciseHolder(@NonNull View itemView, int viewType) {
            super(itemView);

            if(viewType == ITEM_VIEW) {
                exerciseName = itemView.findViewById(R.id.exercise_name);
                exerciseID = itemView.findViewById(R.id.exercise_id);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(getContext(), "You selected '" + exerciseName.getText() + "'.", Toast.LENGTH_SHORT).show();
                        createViewExerciseBox(Integer.parseInt(exerciseID.getText().toString()));
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
    private class ExerciseRecyclerViewAdapter extends RecyclerView.Adapter<ExerciseFragment.ExerciseHolder> implements ExerciseTask.Observer {

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
        public ExerciseFragment.ExerciseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(ExerciseFragment.this.getContext());
            View view;

            if(viewType == LOADING_DATA_VIEW) {
                view =layoutInflater.inflate(R.layout.loading_row, parent, false);

            } else {
                view = layoutInflater.inflate(R.layout.exercise_row, parent, false);
            }

            return new ExerciseFragment.ExerciseHolder(view, viewType);
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

            ExerciseTask eTask = new ExerciseTask(presenter, this);
            //ExercisesRequest request = new ExercisesRequest(PAGE_SIZE, lastExercise);
            ExercisesRequest request = new ExercisesRequest(selectedMuscleGroup, PAGE_SIZE, exercises.get(exercises.size() - 1).getId());
            eTask.execute(request);
            //presenter.loadExercises(selectedMuscleGroup, getItemCount(), exercises.size() - 1);
            //presenter.loadExercises(request);
        }

        @Override
        public void exerciseRetrieved(ExercisesResponse exercisesResponse) {
            List<Exercise> exercises = exercisesResponse.getExercises();


            //lastExercise = (exercises.size() > 0) ? exercises.get(exercises.size() -1) : null;
            hasMorePages = exercisesResponse.isHasMorePages();

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
            Exercise exercise = new Exercise("Dummy exercise", null, null, null, null);
            exercise.setId(1);
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
