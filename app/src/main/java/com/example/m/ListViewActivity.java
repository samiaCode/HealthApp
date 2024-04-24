package com.example.m;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ListViewActivity extends AppCompatActivity {

    private ListView habitsListView;
    private ArrayList<Habit> habitsList;
    private ArrayAdapter<Habit> habitsAdapter;
    private HashMap<Habit, Date> checkedHabitsMap;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        loadDataFromSharedPreferences();

        habitsListView = findViewById(R.id.habitsListView);

        habitsAdapter = new ArrayAdapter<Habit>(this, R.layout.list_item_habit, habitsList) {
            @NonNull
            @Override
            public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_habit, parent, false);
                }

                final Habit habit = habitsList.get(position);

                CheckBox checkBox = convertView.findViewById(R.id.checkBox);
                checkBox.setText(habit.getHabitName());
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            checkedHabitsMap.put(habit, new Date());
                        } else {
                            checkedHabitsMap.remove(habit);
                        }
                    }
                });

                Button buttonDelete = convertView.findViewById(R.id.button_delete);
                buttonDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        habitsList.remove(position);
                        habitsAdapter.notifyDataSetChanged();
                    }
                });

                return convertView;
            }
        };
        habitsListView.setAdapter(habitsAdapter);

        checkedHabitsMap = new HashMap<>();

        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddHabitDialog();
            }
        });

        Button previousHabitsButton = findViewById(R.id.previousHabitsButton);
        previousHabitsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPreviousHabits();
            }
        });

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveDataToSharedPreferences();
    }

    private void saveDataToSharedPreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("habitsList", serializeHabitsList());
        editor.putString("checkedHabitsMap", serializeCheckedHabitsMap());
        editor.apply();
    }

    private String serializeHabitsList() {
        try {
            JSONArray jsonArray = new JSONArray();
            for (Habit habit : habitsList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("habitName", habit.getHabitName());
                jsonArray.put(jsonObject);
            }
            return jsonArray.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String serializeCheckedHabitsMap() {
        try {
            JSONObject jsonObject = new JSONObject();
            for (Map.Entry<Habit, Date> entry : checkedHabitsMap.entrySet()) {
                Habit habit = entry.getKey();
                Date date = entry.getValue();
                JSONObject habitJson = new JSONObject();
                habitJson.put("habitName", habit.getHabitName());
                jsonObject.put(habitJson.toString(), date.toString());
            }
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void loadDataFromSharedPreferences() {
        habitsList = deserializeHabitsList(sharedPreferences.getString("habitsList", ""));
        checkedHabitsMap = deserializeCheckedHabitsMap(sharedPreferences.getString("checkedHabitsMap", ""));

        if (habitsList == null) {
            habitsList = new ArrayList<>();
        }
        if (checkedHabitsMap == null) {
            checkedHabitsMap = new HashMap<>();
        }
    }

    private ArrayList<Habit> deserializeHabitsList(String json) {
        ArrayList<Habit> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String habitName = jsonObject.getString("habitName");
                list.add(new Habit(habitName));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    private HashMap<Habit, Date> deserializeCheckedHabitsMap(String json) {
        HashMap<Habit, Date> map = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                String value = jsonObject.getString(key);
                Habit habit = new Habit(new JSONObject(key).getString("habitName"));
                Date date = new Date(value);
                map.put(habit, date);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    private void showAddHabitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Habit");

        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_habit, null);
        final EditText input = dialogView.findViewById(R.id.editText);
        builder.setView(dialogView);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String habitName = input.getText().toString();
                Habit habit = new Habit(habitName);
                habitsList.add(habit);
                habitsAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void showPreviousHabits() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {
                final Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, month, dayOfMonth);

                List<Habit> habitsForDate = filterHabitsForDate(selectedDate.getTime());

                showCheckedHabitsForDate(habitsForDate);

                saveCheckedHabitsWithDate(selectedDate.getTime(), habitsForDate);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private List<Habit> filterHabitsForDate(Date date) {
        List<Habit> habitsForDate = new ArrayList<>();
        for (Map.Entry<Habit, Date> entry : checkedHabitsMap.entrySet()) {
            Habit habit = entry.getKey();
            Date habitDate = entry.getValue();
            if (isSameDate(habitDate, date)) {
                habitsForDate.add(habit);
            }
        }
        return habitsForDate;
    }

    private boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }

    private void showCheckedHabitsForDate(List<Habit> habitsForDate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Checked Habits");

        StringBuilder habitsText = new StringBuilder();
        for (Habit habit : habitsForDate) {
            habitsText.append(habit.getHabitName()).append("\n");
        }

        if (habitsForDate.isEmpty()) {
            habitsText.append("No checked habits for this date.");
        }

        builder.setMessage(habitsText.toString());
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private void saveCheckedHabitsWithDate(Date date, List<Habit> habitsForDate) {

        for (Habit habit : habitsForDate) {
            System.out.println("Checked Habit: " + habit.getHabitName() + " - " + date.toString());
        }
    }
}