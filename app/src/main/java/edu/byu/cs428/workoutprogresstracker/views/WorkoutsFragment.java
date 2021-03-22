package edu.byu.cs428.workoutprogresstracker.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import edu.byu.cs428.workoutprogresstracker.R;

public class WorkoutsFragment extends Fragment {
    //private WorkoutRecyclerViewAdapter workoutRecyclerViewAdapter;
    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;
    private static final String LOG_TAG = "WorkoutFragment";
    //WorkoutPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.workouts_tab, container, false);

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
}
