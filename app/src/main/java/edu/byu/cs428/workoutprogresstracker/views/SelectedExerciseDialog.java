package edu.byu.cs428.workoutprogresstracker.views;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import edu.byu.cs428.workoutprogresstracker.R;
import edu.byu.cs428.workoutprogresstracker.dao.DataAccessException;
import edu.byu.cs428.workoutprogresstracker.models.Exercise;
import edu.byu.cs428.workoutprogresstracker.models.History;
import edu.byu.cs428.workoutprogresstracker.models.metric.InvalidMetricTypeException;
import edu.byu.cs428.workoutprogresstracker.models.metric.Metric;
import edu.byu.cs428.workoutprogresstracker.models.metric.MetricFactory;
import edu.byu.cs428.workoutprogresstracker.models.metric.RepsMetric;
import edu.byu.cs428.workoutprogresstracker.presenters.ExercisePresenter;
import edu.byu.cs428.workoutprogresstracker.presenters.WorkoutPresenter;
import edu.byu.cs428.workoutprogresstracker.services.requests.ExerciseHistoryRequest;
import edu.byu.cs428.workoutprogresstracker.services.requests.ExercisesRequest;
import edu.byu.cs428.workoutprogresstracker.services.responses.ExerciseHistoryResponse;
import edu.byu.cs428.workoutprogresstracker.services.responses.ExercisesResponse;
import edu.byu.cs428.workoutprogresstracker.views.asyncTasks.ExerciseHistoryTask;
import edu.byu.cs428.workoutprogresstracker.views.asyncTasks.ExerciseTask;

public class SelectedExerciseDialog extends DialogFragment {
    EditText exerciseName;
    int exerciseId;
    TextView exerciseGoal;
    EditText exerciseGoalValue;
    Exercise selectedExercise;
    Button doneButton;
    Button deleteButton;
    Button addEntryButton;
    DialogManager dialogManager = DialogManager.getInstance();
    EditText addReps;
    EditText addWeight;
    TextView count;
    TextView metric;

    private SelectedExerciseDialog.ExerciseHistoryRecyclerViewAdapter exerciseHistoryRecyclerViewAdapter;
    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;
    private static final String LOG_TAG = "WorkoutDialog";
    ExercisePresenter presenter;
    String selectedMuscleGroup;
    List<Exercise> addedExercises;

    private static final int PAGE_SIZE = 30;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = inflater.inflate(R.layout.dialog_selected_exercise, container, false);
        exerciseId = getArguments().getInt("id");
        final ExercisePresenter ePresenter = new ExercisePresenter();
        try {
            selectedExercise = ePresenter.loadExercise(exerciseId);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        presenter = new ExercisePresenter();

        //
        exerciseGoalValue = view.findViewById(R.id.exercise_goal_value);
        exerciseGoalValue.setText(selectedExercise.getGoal().getValue().toString());
        exerciseGoal = view.findViewById(R.id.exercise_goal);
        exerciseName = view.findViewById(R.id.exercise_title);
        exerciseName.setText(selectedExercise.getName());
        count = view.findViewById(R.id.count);
        count.setText(selectedExercise.getGoal().getUnits());


        addEntryButton = view.findViewById(R.id.add_history_button);
        addReps = view.findViewById(R.id.add_Entry_Reps);
        addEntryButton = view.findViewById(R.id.add_history_button);
        doneButton = view.findViewById(R.id.done_button);
        deleteButton = view.findViewById(R.id.delete_button);

        addEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String reps = addReps.getText().toString();
                System.out.println(reps);

                Metric metric = null;
                try {
                   new MetricFactory();
                   metric = MetricFactory.createMetric(selectedExercise.getGoal().getName(), Double.parseDouble(addReps.getText().toString()), selectedExercise.getGoal().getUnits());
                   ePresenter.addExerciseHistory(selectedExercise.getId(), metric);
                   History history = new History(metric, new Date());
                   selectedExercise.getHistory().add(history);
                   ePresenter.saveExercise(selectedExercise);

                } catch (InvalidMetricTypeException | DataAccessException e) {
                    e.printStackTrace();
                }

            }
        });




        RecyclerView exerciseHistoryRecyclerView = view.findViewById(R.id.exercise_history_values);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        exerciseHistoryRecyclerView.setLayoutManager(layoutManager);
        try {
            exerciseHistoryRecyclerViewAdapter = new ExerciseHistoryRecyclerViewAdapter();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        exerciseHistoryRecyclerView.setAdapter(exerciseHistoryRecyclerViewAdapter);
        exerciseHistoryRecyclerView.addOnScrollListener(new ExerciseHistoryRecyclerViewPaginationScrollListener(layoutManager));

        exerciseName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                selectedExercise.setName(exerciseName.getText().toString());
                try {
                    ePresenter.saveExercise(selectedExercise);
                } catch (DataAccessException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //nothing
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedExercise.getGoal().updateValue(Double.parseDouble(exerciseGoalValue.getText().toString()));
                try {
                    ePresenter.saveExercise(selectedExercise);
                } catch (DataAccessException e) {
                    e.printStackTrace();
                }
                getDialog().dismiss();
                dialogManager.notifyListeners();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ePresenter.deleteExercise(exerciseId);
                } catch (DataAccessException e) {
                    e.printStackTrace();
                }
                getDialog().dismiss();
                dialogManager.notifyListeners();
            }
        });

        return view;
    }


    //////////////////// EXERCISE HISTORY RECYCLER METHODS ///////////////////////////////

    private class ExerciseHistoryHolder extends RecyclerView.ViewHolder {

        private final TextView exerciseDate;
        private final TextView exerciseValues;

        ExerciseHistoryHolder(@NonNull View itemView, int viewType) {
            super(itemView);

            if(viewType == ITEM_VIEW) {
                exerciseDate = itemView.findViewById(R.id.exercise_history_date);
                exerciseValues = itemView.findViewById(R.id.exercise_history_values);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });
            } else {
                exerciseDate = null;
                exerciseValues = null;
            }
        }
        private void selectedExerciseBox (int exerciseID) {
            SelectedExerciseDialog dialog = new SelectedExerciseDialog();
            Bundle bundle = new Bundle();
            bundle.putInt("id", exerciseID);
            dialog.setArguments(bundle);
            dialog.show(getParentFragmentManager(), LOG_TAG);
        }


        /**
         * Binds the exercise data to the view.
         */
        void bindHistory(History history) {
            exerciseDate.setText(history.getTimestamp().toString());
            String values = history.getGoal().getValue() + " " + history.getGoal().getUnits();
            exerciseValues.setText(values);

        }
    }

    /**
     * The adapter for the RecyclerView that displays the Exercises.
     */
    private class ExerciseHistoryRecyclerViewAdapter extends RecyclerView.Adapter<SelectedExerciseDialog.ExerciseHistoryHolder> implements ExerciseHistoryTask.Observer {

        private final List<History> exerciseHistory = new ArrayList<>();

        private Exercise lastExercise;

        private boolean hasMorePages;
        private boolean isLoading = false;

        /**
         * Creates an instance and loads the first page of exercise data.
         */
        ExerciseHistoryRecyclerViewAdapter() throws DataAccessException {
            loadMoreItems();
        }

        /**
         * Adds new exercises to the list from which the RecyclerView retrieves the users it displays
         * and notifies the RecyclerView that items have been added.
         */
        void addItems(List<History> newHistories) {
            int startInsertPosition = exerciseHistory.size();
            exerciseHistory.addAll(newHistories);
            this.notifyItemRangeInserted(startInsertPosition, newHistories.size());
        }

        /**
         * Adds a single exercise to the list from which the RecyclerView retrieves the exercises it
         * displays and notifies the RecyclerView that an item has been added.
         */
        void addItem(History history) {
            exerciseHistory.add(history);
            this.notifyItemInserted(exerciseHistory.size() - 1);
        }

        /**
         * Removes a exercises from the list from which the RecyclerView retrieves the exercises it displays
         * and notifies the RecyclerView that an item has been removed.
         */
        void removeItem(History history) {
            int position = exerciseHistory.indexOf(history);
            exerciseHistory.remove(position);
            this.notifyItemRemoved(position);
        }

        /**
         *  Creates a view holder for an exercise to be displayed in the RecyclerView or for a message
         *  indicating that new rows are being loaded if we are waiting for rows to load.
         */
        @NonNull
        @Override
        public SelectedExerciseDialog.ExerciseHistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(SelectedExerciseDialog.this.getContext());
            View view;

            if(viewType == LOADING_DATA_VIEW) {
                view =layoutInflater.inflate(R.layout.loading_row, parent, false);

            } else {
                view = layoutInflater.inflate(R.layout.exercise_history_row, parent, false);
            }

            return new SelectedExerciseDialog.ExerciseHistoryHolder(view, viewType);
        }

        @Override
        public void onBindViewHolder(@NonNull SelectedExerciseDialog.ExerciseHistoryHolder exerciseHistoryHolder, int position) {
            if(!isLoading) {
                exerciseHistoryHolder.bindHistory(exerciseHistory.get(position));
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
            return (position == exerciseHistory.size() - 1 && isLoading) ? LOADING_DATA_VIEW : ITEM_VIEW;
        }

        @Override
        public int getItemCount() {
            return exerciseHistory.size();
        }

        /**
         * Causes the Adapter to display a loading footer and make a request to get more following
         * data.
         */
        void loadMoreItems() throws DataAccessException {
            isLoading = true;
            addLoadingFooter();

            ExerciseHistoryTask eTask = new ExerciseHistoryTask(presenter, this);
            ExerciseHistoryRequest request = new ExerciseHistoryRequest(selectedExercise.getId());
            eTask.execute(request);

            List<History> exerciseHistory = presenter.loadExercise(selectedExercise.getId()).getHistory();
            hasMorePages = false;
            isLoading = false;
            removeLoadingFooter();
            addItems(exerciseHistory);
        }

        @Override
        public void exerciseRetrieved(ExerciseHistoryResponse exerciseHistoryResponse) {
            Exercise returnedExercise = exerciseHistoryResponse.getExercise();
            List<History> newHistories = returnedExercise.getHistory();
            int newExercisesIndex = selectedExercise.getHistory().size() -1;
            List<History> historiesToAdd = new ArrayList<>();
            for(int i = newExercisesIndex; i < newHistories.size(); i++){
                historiesToAdd.add(newHistories.get(newExercisesIndex));
            }


            //lastExercise = (exercises.size() > 0) ? exercises.get(exercises.size() -1) : null;
            hasMorePages = false;

            isLoading = false;
            removeLoadingFooter();
            exerciseHistoryRecyclerViewAdapter.addItems(historiesToAdd);
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
            History history = new History(new RepsMetric());
            //history.setId(1);
            addItem(history);
        }

        /**
         * Removes the dummy exercise from the list of exercises so the RecyclerView will stop displaying
         * the loading footer at the bottom of the list.
         */
        private void removeLoadingFooter() {
            removeItem(exerciseHistory.get(exerciseHistory.size() - 1));
        }
    }

    /**
     * A scroll listener that detects when the user has scrolled to the bottom of the currently
     * available data.
     */
    private class ExerciseHistoryRecyclerViewPaginationScrollListener extends RecyclerView.OnScrollListener {

        private LinearLayoutManager layoutManager;

        /**
         * Creates a new instance.
         *
         * @param layoutManager the layout manager being used by the RecyclerView.
         */
        ExerciseHistoryRecyclerViewPaginationScrollListener(LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        private ExerciseHistoryRecyclerViewPaginationScrollListener() {
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

            if (!exerciseHistoryRecyclerViewAdapter.isLoading && exerciseHistoryRecyclerViewAdapter.hasMorePages) {
                if ((visibleItemCount + firstVisibleItemPosition) >=
                        totalItemCount && firstVisibleItemPosition >= 0) {
                    try {
                        exerciseHistoryRecyclerViewAdapter.loadMoreItems();
                    } catch (DataAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
