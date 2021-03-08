package edu.byu.cs428.workoutprogresstracker.dao;

public class DataAccessException extends Exception{
    public DataAccessException(String message){
        super(message);
    }
    DataAccessException(){
        super();
    }
}