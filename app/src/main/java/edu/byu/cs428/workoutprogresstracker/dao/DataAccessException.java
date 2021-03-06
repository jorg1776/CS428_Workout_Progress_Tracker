package edu.byu.cs428.workoutprogresstracker.dao;

public class DataAccessException extends Exception{
    DataAccessException(String message){
        super(message);
    }
    DataAccessException(){
        super();
    }
}