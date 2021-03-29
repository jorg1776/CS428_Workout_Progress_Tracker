package edu.byu.cs428.workoutprogresstracker.models;

import java.util.List;


public class Workout {
    private int id;
    private String name;
    private String muscleGroup;
    private List<Exercise> exercises;

    public Workout(String name, List<Exercise> exercises, String muscleGroup){
        this.name = name;
        this.exercises = exercises;
        this.muscleGroup = muscleGroup;
    }


    public Workout(int id, String name, String muscleGroup){
        this.id = id;
        this.name = name;
        this.muscleGroup = muscleGroup;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

}
