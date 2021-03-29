package edu.byu.cs428.workoutprogresstracker.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Date;

import edu.byu.cs428.workoutprogresstracker.R;

public class CalendarFragment extends Fragment implements IDialogListener {
    TextView calendarDates;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.calendar_tab, container, false);
        /*
        calendarDates = view.findViewById(R.id.calendar_dates);
        Date d = new Date();
        int month = d.getMonth();
        int date = d.getDay();
        int dayLimit = 31;
        int finalMonth = month;

        if (month == 2) {
            dayLimit = 28;
        }

        if (month == 4 || month == 6 || month ==9 || month == 11) {
            dayLimit = 30;
        }

        int finalDate = date + 7 % dayLimit;
        if (finalDate < 8) {
            finalMonth = (month + 1) % 12;
        }

        String viewableDates = String.valueOf(month) + "/" + String.valueOf(date) + " - " + String.valueOf(finalMonth) + "/" + String.valueOf(finalDate);
        calendarDates.setText(viewableDates);
        */

        return view;
    }

    @Override
    public void notifyClosed() {

    }
}
