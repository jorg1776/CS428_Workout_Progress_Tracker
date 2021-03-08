package edu.byu.cs428.workoutprogresstracker.views;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import edu.byu.cs428.workoutprogresstracker.R;
import edu.byu.cs428.workoutprogresstracker.views.CalendarFragment;
import edu.byu.cs428.workoutprogresstracker.views.ExerciseFragment;
import edu.byu.cs428.workoutprogresstracker.views.WorkoutsFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;
    private static final int[] TAB_TITLES = new int[]{R.string.calendarTabTitle, R.string.workoutsTabTitle, R.string.exercisesTabTitle};

    public SectionsPagerAdapter(Context context, @NonNull FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new CalendarFragment();
                break;
            case 1:
                fragment = new WorkoutsFragment();
                break;
            case 2:
                fragment = new ExerciseFragment();
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 3;
    }
}
