package edu.byu.cs428.workoutprogresstracker.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import edu.byu.cs428.workoutprogresstracker.models.Workout;
import edu.byu.cs428.workoutprogresstracker.presenters.WorkoutsListPresenter;
import edu.byu.cs428.workoutprogresstracker.services.requests.ExercisesRequest;
import edu.byu.cs428.workoutprogresstracker.services.requests.WorkoutsRequest;
import edu.byu.cs428.workoutprogresstracker.services.responses.ExercisesResponse;
import edu.byu.cs428.workoutprogresstracker.services.responses.WorkoutsResponse;
import edu.byu.cs428.workoutprogresstracker.views.asyncTasks.WorkoutTask;

public class WorkoutsFragment extends Fragment {
    private WorkoutsRecyclerViewAdapter workoutsRecyclerViewAdapter;
    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;
    private static final String LOG_TAG = "WorkoutFragment";
    WorkoutsListPresenter presenter;
    private static final int PAGE_SIZE = 100;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.workouts_tab, container, false);

        presenter = new WorkoutsListPresenter();

        RecyclerView workoutsRecyclerView = view.findViewById(R.id.workoutsRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        workoutsRecyclerView.setLayoutManager(layoutManager);
        workoutsRecyclerViewAdapter = new WorkoutsRecyclerViewAdapter();
        workoutsRecyclerView.setAdapter(workoutsRecyclerViewAdapter);
        workoutsRecyclerView.addOnScrollListener(new WorkoutsRecyclerViewPaginationScrollListener(layoutManager));

        Button addButton = view.findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("click");
                createWorkoutBox();
            }
        });

        return view;
    }

    private void createWorkoutBox () {
        NewWorkoutDialog dialog = new NewWorkoutDialog();
        dialog.show(getParentFragmentManager(), LOG_TAG);
    }

    //////////////////// WORKOUT RECYCLER METHODS ////////////////////////////////

    private class WorkoutHolder extends RecyclerView.ViewHolder {

        private final TextView workoutName;
        private final TextView workoutMuscleGroup;
        private final TextView workoutID;

        WorkoutHolder(@NonNull View itemView, int viewType) {
            super(itemView);

            if(viewType == ITEM_VIEW) {
                workoutName = itemView.findViewById(R.id.workout_name);
                workoutMuscleGroup = itemView.findViewById(R.id.workout_muscle_group);
                workoutID = itemView.findViewById(R.id.workout_id);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "You selected '" + workoutName.getText() + "'.", Toast.LENGTH_SHORT).show();

                        //add code to open individual workout view i.e. list of all exercises
                    }
                });
            } else {
                workoutName = null;
                workoutMuscleGroup = null;
                workoutID = null;
            }
        }


        /**
         * Binds the workout data to the view.
         */
        void bindWorkout(Workout workout) {

            workoutName.setText(workout.getName());
            workoutMuscleGroup.setText(workout.getMuscleGroup());
            workoutID.setText(String.valueOf(workout.getId()));

        }
    }

    /**
     * The adapter for the RecyclerView that displays the Exercises.
     */
    private class WorkoutsRecyclerViewAdapter extends RecyclerView.Adapter<WorkoutHolder> implements WorkoutTask.Observer {

        private final List<Workout> workouts = new ArrayList<>();

        private Workout lastWorkout;

        private boolean hasMorePages;
        private boolean isLoading = false;

        /**
         * Creates an instance and loads the first page of workout data.
         */
        WorkoutsRecyclerViewAdapter() {
            loadMoreItems();
        }

        /**
         * Adds new workouts to the list from which the RecyclerView retrieves the users it displays
         * and notifies the RecyclerView that items have been added.
         */
        void addItems(List<Workout> newWorkouts) {
            int startInsertPosition = workouts.size();
            workouts.addAll(newWorkouts);
            this.notifyItemRangeInserted(startInsertPosition, newWorkouts.size());
        }

        /**
         * Adds a single workout to the list from which the RecyclerView retrieves the exercises it
         * displays and notifies the RecyclerView that an item has been added.
         */
        void addItem(Workout workout) {
            workouts.add(workout);
            this.notifyItemInserted(workouts.size() - 1);
        }

        /**
         * Removes a workout from the list from which the RecyclerView retrieves the exercises it displays
         * and notifies the RecyclerView that an item has been removed.
         */
        void removeItem(Workout workout) {
            int position = workouts.indexOf(workout);
            workouts.remove(position);
            this.notifyItemRemoved(position);
        }

        /**
         *  Creates a view holder for a workout to be displayed in the RecyclerView or for a message
         *  indicating that new rows are being loaded if we are waiting for rows to load.
         */
        @NonNull
        @Override
        public WorkoutHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(WorkoutsFragment.this.getContext());
            View view;

            if(viewType == LOADING_DATA_VIEW) {
                view =layoutInflater.inflate(R.layout.loading_row, parent, false);

            } else {
                view = layoutInflater.inflate(R.layout.workout_row, parent, false);
            }

            return new WorkoutHolder(view, viewType);
        }

        @Override
        public void onBindViewHolder(@NonNull WorkoutHolder workoutHolder, int position) {
            if(!isLoading) {
                workoutHolder.bindWorkout(workouts.get(position));
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
            return (position == workouts.size() - 1 && isLoading) ? LOADING_DATA_VIEW : ITEM_VIEW;
        }

        @Override
        public int getItemCount() {
            return workouts.size();
        }

        /**
         * Causes the Adapter to display a loading footer and make a request to get more following
         * data.
         */
        void loadMoreItems() {
            isLoading = true;
            addLoadingFooter();

            WorkoutTask wTask = new WorkoutTask(presenter, this);
            //ExercisesRequest request = new ExercisesRequest(PAGE_SIZE, lastExercise);
            WorkoutsRequest request = new WorkoutsRequest("abs", PAGE_SIZE, workouts.get(workouts.size() - 1).getId());
            wTask.execute(request);
            //presenter.loadExercises(selectedMuscleGroup, getItemCount(), exercises.size() - 1);
            //presenter.loadExercises(request);
        }

        @Override
        public void workoutRetrieved(WorkoutsResponse workoutsResponse) {
            List<Workout> workouts = workoutsResponse.getWorkouts();
            hasMorePages = workoutsResponse.isHasMorePages();

            isLoading = false;
            removeLoadingFooter();
            workoutsRecyclerViewAdapter.addItems(workouts);
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
            Workout workout = new Workout("Dummy workout", null, null);
            workout.setId(1);
            addItem(workout);
        }

        /**
         * Removes the dummy exercise from the list of exercises so the RecyclerView will stop displaying
         * the loading footer at the bottom of the list.
         */
        private void removeLoadingFooter() {
            removeItem(workouts.get(workouts.size() - 1));
        }
    }

    /**
     * A scroll listener that detects when the user has scrolled to the bottom of the currently
     * available data.
     */
    private class WorkoutsRecyclerViewPaginationScrollListener extends RecyclerView.OnScrollListener {

        private LinearLayoutManager layoutManager;

        /**
         * Creates a new instance.
         *
         * @param layoutManager the layout manager being used by the RecyclerView.
         */
        WorkoutsRecyclerViewPaginationScrollListener(LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        private WorkoutsRecyclerViewPaginationScrollListener() {
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

            if (!workoutsRecyclerViewAdapter.isLoading && workoutsRecyclerViewAdapter.hasMorePages) {
                if ((visibleItemCount + firstVisibleItemPosition) >=
                        totalItemCount && firstVisibleItemPosition >= 0) {
                    workoutsRecyclerViewAdapter.loadMoreItems();
                }
            }
        }
    }
}
