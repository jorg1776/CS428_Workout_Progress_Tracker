package edu.byu.cs428.workoutprogresstracker.views;

import java.util.ArrayList;
import java.util.List;

public class DialogManager {
    private static DialogManager instance = null;
    List<IDialogListener> listeners;

    static DialogManager getInstance() {
        if (instance == null)
            instance = new DialogManager();
        return instance;
    }

    private DialogManager() {
        listeners = new ArrayList<>();
    }

    public void addListener(IDialogListener i) {
        listeners.add(i);
    }

    public void notifyListeners() {
        for(int i = 0; i < listeners.size(); i++) {
            listeners.get(i).notifyClosed();
        }
    }

}
