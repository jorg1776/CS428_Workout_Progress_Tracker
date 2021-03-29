package edu.byu.cs428.workoutprogresstracker.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.byu.cs428.workoutprogresstracker.R;
import edu.byu.cs428.workoutprogresstracker.models.Exercise;
import edu.byu.cs428.workoutprogresstracker.models.metric.Metric;
import edu.byu.cs428.workoutprogresstracker.models.metric.TimeMetric;
import edu.byu.cs428.workoutprogresstracker.models.metric.WeightMetric;
import edu.byu.cs428.workoutprogresstracker.presenters.ExercisePresenter;


public class NewExerciseDialog extends DialogFragment implements AdapterView.OnItemSelectedListener {
    private EditText exerciseName;
    private Button createButton;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private String selectedMuscleGroup;
    private String metric;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_new_exercise, container, false);
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

        radioGroup = (RadioGroup) view.findViewById(R.id.radiogroup);
        exerciseName = view.findViewById(R.id.exercise_title_input);
        createButton = view.findViewById(R.id.create_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = exerciseName.getText().toString();
                int selectedId = radioGroup.getCheckedRadioButtonId();

                if(name.equals("") || selectedId == -1 || selectedMuscleGroup == null) {
                    Toast toast=Toast.makeText(getActivity(),"Missing Information",Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                radioButton = (RadioButton) view.findViewById(selectedId);
                metric = radioButton.getText().toString();
                Metric objective;
                if (metric.equals("Time")) {
                    objective = new TimeMetric();
                }
                else {
                    objective = new WeightMetric();
                }
                Exercise exercise = new Exercise(name, objective, null, null, selectedMuscleGroup);



                //save the created exercise
                ExercisePresenter presenter = new ExercisePresenter();
                presenter.saveExercise(exercise);

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
}
