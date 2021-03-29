package edu.byu.cs428.workoutprogresstracker.views;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.byu.cs428.workoutprogresstracker.R;
import edu.byu.cs428.workoutprogresstracker.dao.DataAccessException;
import edu.byu.cs428.workoutprogresstracker.models.Exercise;
import edu.byu.cs428.workoutprogresstracker.presenters.ExercisePresenter;
import edu.byu.cs428.workoutprogresstracker.presenters.WorkoutPresenter;

public class SelectedExerciseDialog extends DialogFragment {
    EditText exerciseName;
    int exerciseId;
    Exercise selectedExercise;
    Button doneButton;
    Button deleteButton;
    DialogManager dialogManager = DialogManager.getInstance();

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


        exerciseName = view.findViewById(R.id.exercise_title);
        exerciseName.setText(selectedExercise.getName());

        doneButton = view.findViewById(R.id.done_button);
        deleteButton = view.findViewById(R.id.delete_button);

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
}
