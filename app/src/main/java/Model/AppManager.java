package Model;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;

public class AppManager {
    private HashMap<String, ArrayList<String>> userMedicines;
    private static AppManager instance;
    private Context context;

    private AppManager(Context context) {
        this.context = context.getApplicationContext();
        userMedicines = new HashMap<>();
    }

    public static synchronized AppManager getInstance(Context context) {
        if (instance == null) {
            instance = new AppManager(context);
        }
        return instance;
    }

    public void addMedicine(String userId, String medicine) {
        ArrayList<String> medicines = userMedicines.get(userId);
        if (medicines == null) {
            medicines = new ArrayList<>();
        }
        medicines.add(medicine);
        userMedicines.put(userId, medicines);
    }

    public ArrayList<String> getUserMedicines(String userId) {
        return userMedicines.get(userId);
    }

    public void clearUserMedicines(String userId) {
        userMedicines.remove(userId);
    }
}