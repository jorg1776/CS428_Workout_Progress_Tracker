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
import edu.byu.cs428.workoutprogresstracker.dao.DataAccessException;
import edu.byu.cs428.workoutprogresstracker.models.Exercise;
import edu.byu.cs428.workoutprogresstracker.models.History;
import edu.byu.cs428.workoutprogresstracker.models.metric.DistanceMetric;
import edu.byu.cs428.workoutprogresstracker.models.metric.Metric;
import edu.byu.cs428.workoutprogresstracker.models.metric.RepsMetric;
import edu.byu.cs428.workoutprogresstracker.models.metric.TimeMetric;
import edu.byu.cs428.workoutprogresstracker.models.metric.WeightMetric;
import edu.byu.cs428.workoutprogresstracker.presenters.ExercisePresenter;


public class NewExerciseDialog extends DialogFragment implements AdapterView.OnItemSelectedListener {
    private EditText exerciseName;
    private String selectedMuscleGroup;
    private Metric objective;
    private Metric goal;
    DialogManager dialogManager = DialogManager.getInstance();
    Spinner first2;
    Spinner second2;
    String objectiveUnits;
    String goalUnits;
    EditText objectiveValue;
    EditText goalValue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_new_exercise, container, false);

        objectiveValue = view.findViewById(R.id.objective_value);
        goalValue = view.findViewById(R.id.goal_value);

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

        Spinner firstSpinner = (Spinner) view.findViewById(R.id.spinner_first_option);
        firstSpinner.setOnItemSelectedListener(this);
        List<String> objectives = new ArrayList<>();
        objectives.add("Reps");
        objectives.add("Distance");
        objectives.add("Duration");
        objectives.add("Weight");
        ArrayAdapter<String> objectiveAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, objectives);
        objectiveAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        firstSpinner.setAdapter(objectiveAdapter);

        first2 = (Spinner) view.findViewById(R.id.spinner_first_option_metric);
        first2.setVisibility(View.GONE);

        Spinner secondSpinner = (Spinner) view.findViewById(R.id.spinner_second_option);
        secondSpinner.setOnItemSelectedListener(this);
        List<String> goals = new ArrayList<>();
        goals.add("Weight");
        goals.add("Distance");
        goals.add("Duration");
        goals.add("Reps");
        ArrayAdapter<String> goalsAdpater = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, goals);
        goalsAdpater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        secondSpinner.setAdapter(goalsAdpater);

        second2 = (Spinner) view.findViewById(R.id.spinner_second_option_metric);
        second2.setVisibility(View.GONE);

        exerciseName = view.findViewById(R.id.exercise_title_input);
        Button createButton = view.findViewById(R.id.create_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = exerciseName.getText().toString();
                String objectiveV = objectiveValue.getText().toString();
                String goalV = goalValue.getText().toString();

                if(name.equals("") || objective == null || goal == null || objectiveV.equals("") || goalV.equals("") || selectedMuscleGroup == null) {
                    Toast toast=Toast.makeText(getActivity(),"Missing Information",Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                objective.updateValue(Double.parseDouble(objectiveV));
                objective.setUnits(objectiveUnits);
                goal.updateValue(Double.parseDouble(goalV));
                goal.setUnits(goalUnits);

                History newEntry = new History(goal);
                List<History> history = new ArrayList<>();
                history.add(newEntry);
                Exercise exercise = new Exercise(name, objective, goal, history, selectedMuscleGroup);

                //save the created exercise
                ExercisePresenter presenter = new ExercisePresenter();
                try {
                    presenter.saveExercise(exercise);
                } catch (DataAccessException e) {
                    e.printStackTrace();
                }

                getDialog().dismiss();
                dialogManager.notifyListeners();
            }
        });
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spinner) {
            selectedMuscleGroup = parent.getItemAtPosition(position).toString();
        } else if (parent.getId() == R.id.spinner_first_option) {
            String selectedObjective = parent.getItemAtPosition(position).toString();
            first2.setOnItemSelectedListener(this);
            List<String> measurements = new ArrayList<>();
            switch (selectedObjective) {
                case "Weight":
                    objective = new WeightMetric();
                    measurements.add("lbs");
                    measurements.add("kgs");
                    first2.setVisibility(View.VISIBLE);
                    break;
                case "Distance":
                    objective = new DistanceMetric();
                    measurements.add("mi");
                    measurements.add("km");
                    measurements.add("m");
                    first2.setVisibility(View.VISIBLE);
                    break;
                case "Duration":
                    objective = new TimeMetric();
                    measurements.add("min");
                    measurements.add("hr");
                    measurements.add("sec");
                    first2.setVisibility(View.VISIBLE);
                    break;
                case "Reps":
                    objective = new RepsMetric();
                    first2.setVisibility(View.GONE);
                    break;
            }
            ArrayAdapter<String> objectiveAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, measurements);
            objectiveAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            first2.setAdapter(objectiveAdapter);
        } else if (parent.getId() == R.id.spinner_second_option) {
            String selectedGoal = parent.getItemAtPosition(position).toString();
            second2.setOnItemSelectedListener(this);
            List<String> measurements = new ArrayList<>();
            switch (selectedGoal) {
                case "Weight":
                    goal = new WeightMetric();
                    measurements.add("lbs");
                    measurements.add("kgs");
                    second2.setVisibility(View.VISIBLE);
                    break;
                case "Distance":
                    goal = new DistanceMetric();
                    measurements.add("mi");
                    measurements.add("km");
                    measurements.add("m");
                    second2.setVisibility(View.VISIBLE);
                    break;
                case "Duration":
                    goal = new TimeMetric();
                    measurements.add("min");
                    measurements.add("hr");
                    measurements.add("sec");
                    second2.setVisibility(View.VISIBLE);
                    break;
                case "Reps":
                    objective = new RepsMetric();
                    second2.setVisibility(View.GONE);
                    break;
            }
            ArrayAdapter<String> objectiveAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, measurements);
            objectiveAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            second2.setAdapter(objectiveAdapter);
        }
        else if (parent.getId() == R.id.spinner_first_option_metric) {
            objectiveUnits = parent.getItemAtPosition(position).toString();
        }
        else {
            goalUnits = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
