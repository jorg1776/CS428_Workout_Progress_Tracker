package edu.byu.cs428.workoutprogresstracker.views;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Date;

import edu.byu.cs428.workoutprogresstracker.R;

public class CalendarFragment extends Fragment implements IDialogListener {
    TextView dates;
    LocalDateTime d;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.calendar_tab, container, false);
        d = LocalDateTime.now();

        dates = view.findViewById(R.id.dates);
        dates.setText(getDate());

        Button dayOneButton = view.findViewById(R.id.button_one);
        Button dayTwoButton = view.findViewById(R.id.button_two);
        Button dayThreeButton = view.findViewById(R.id.button_three);
        Button dayFourButton = view.findViewById(R.id.button_four);
        Button dayFiveButton = view.findViewById(R.id.button_five);
        Button daySixButton = view.findViewById(R.id.button_six);
        Button daySevenButton = view.findViewById(R.id.button_seven);

        dayOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWorkout();
            }
        });

        dayTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWorkout();
            }
        });

        dayThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWorkout();
            }
        });

        dayFourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWorkout();
            }
        });

        dayFiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWorkout();
            }
        });

        daySixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWorkout();
            }
        });

        daySevenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWorkout();
            }
        });
        DayOfWeek day = d.getDayOfWeek();
        int dayValue = day.getValue();

        TextView dayOne = view.findViewById(R.id.day_one);
        TextView dayTwo = view.findViewById(R.id.day_two);
        TextView dayThree = view.findViewById(R.id.day_three);
        TextView dayFour = view.findViewById(R.id.day_four);
        TextView dayFive = view.findViewById(R.id.day_five);
        TextView daySix = view.findViewById(R.id.day_six);
        TextView daySeven = view.findViewById(R.id.day_seven);



        if(dayValue == 7) {
            dayOne.setText("Sunday");
            dayTwo.setText("Monday");
            dayThree.setText("Tuesday");
            dayFour.setText("Wednesday");
            dayFive.setText("Thursday");
            daySix.setText("Friday");
            daySeven.setText("Saturday");
        }

        else if (dayValue == 1) {
            dayOne.setText("Monday");
            dayTwo.setText("Tuesday");
            dayThree.setText("Wednesday");
            dayFour.setText("Thursday");
            dayFive.setText("Friday");
            daySix.setText("Saturday");
            daySeven.setText("Sunday");
        }

        else if (dayValue == 2) {
            dayOne.setText("Tuesday");
            dayTwo.setText("Wednesday");
            dayThree.setText("Thursday");
            dayFour.setText("Friday");
            dayFive.setText("Saturday");
            daySix.setText("Sunday");
            daySeven.setText("Monday");
        }

        else if (dayValue == 3) {
            dayOne.setText("Wednesday");
            dayTwo.setText("Thursday");
            dayThree.setText("Friday");
            dayFour.setText("Saturday");
            dayFive.setText("Sunday");
            daySix.setText("Monday");
            daySeven.setText("Tuesday");
        }

        else if (dayValue == 4) {
            dayOne.setText("Thursday");
            dayTwo.setText("Friday");
            dayThree.setText("Saturday");
            dayFour.setText("Sunday");
            dayFive.setText("Monday");
            daySix.setText("Tuesday");
            daySeven.setText("Wednesday");
        }

        else if (dayValue == 5) {
            dayOne.setText("Friday");
            dayTwo.setText("Saturday");
            dayThree.setText("Sunday");
            dayFour.setText("Monday");
            dayFive.setText("Tuesday");
            daySix.setText("Wednesday");
            daySeven.setText("Thursday");
        }

        else if (dayValue == 6) {
            dayOne.setText("Saturday");
            dayTwo.setText("Sunday");
            dayThree.setText("Monday");
            dayFour.setText("Tuesday");
            dayFive.setText("Wednesday");
            daySix.setText("Thursday");
            daySeven.setText("Friday");
        }



        return view;
    }

    public void addWorkout(){}

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getDate() {
        int month = d.getMonthValue();
        int date = d.getDayOfMonth();
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
        return viewableDates;
    }

    @Override
    public void notifyClosed() {

    }
}
