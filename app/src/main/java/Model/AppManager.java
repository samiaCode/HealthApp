package Model;

import android.content.Context;

import java.util.ArrayList;

public class AppManager {
    private ArrayList<Medicine> medicines;
    private static AppManager instance;
    private AppManager(Context context) {
        init();
    }

    private void init() {
    }

    public static synchronized AppManager getInstance(Context context) {
        if (instance == null) {
            instance = new AppManager(context);
        }
        return instance;
    }


}
