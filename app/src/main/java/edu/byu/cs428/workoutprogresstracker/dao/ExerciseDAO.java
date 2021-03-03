package edu.byu.cs428.workoutprogresstracker.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ExerciseDAO {

    Database database = null;

    public ExerciseDAO(Context context){
        database = new Database(context);
    }

    public Boolean insertExerciseData(String id, String name, String muscleGroup)
    {
        SQLiteDatabase DB = database.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("name", name);
        contentValues.put("muscleGroup", muscleGroup);
        long result=DB.insert("ExerciseTable", null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }


    public Boolean deleteExerciseData (String id)
    {
        SQLiteDatabase DB = database.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from ExerciseTable where id = ?", new String[]{id});
        if (cursor.getCount() > 0) {
            long result = DB.delete("ExerciseTable", "id=?", new String[]{id});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }

    }

    public Cursor getExerciseData ()
    {
        SQLiteDatabase DB = database.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from ExerciseTable", null);
        return cursor;
    }
}