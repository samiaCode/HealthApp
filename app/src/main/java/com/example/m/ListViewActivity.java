package com.example.m;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListViewActivity extends AppCompatActivity {

    private ListView habitsListView;
    private ArrayList<Habit> habitsList;
    private ArrayAdapter<Habit> habitsAdapter;
    private HashMap<Habit, Date> checkedHabitsMap; // HashMap to store checked habits with dates

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        habitsListView = findViewById(R.id.habitsListView);
        habitsList = new ArrayList<>();
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
                            // When a habit is checked, add it to the checkedHabitsMap with the current date
                            checkedHabitsMap.put(habit, new Date());
                        } else {
                            // If habit is unchecked, remove it from the checkedHabitsMap
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

        checkedHabitsMap = new HashMap<>(); // Initialize the checkedHabitsMap

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
                Habit habit = new Habit(habitName, new Date());
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
        // Get the current date to set as the initial date in the DatePicker
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Show DatePicker dialog for date selection
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {
                // Get the selected date
                final Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, month, dayOfMonth);

                // Filter habits list based on selected date
                List<Habit> habitsForDate = filterHabitsForDate(selectedDate.getTime());

                // Show checked habits for the selected date
                showCheckedHabitsForDate(habitsForDate);

                // Save checked habits with the selected date
                saveCheckedHabitsWithDate(selectedDate.getTime(), habitsForDate);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    // Method to filter habits list based on selected date
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

    // Method to check if two dates are on the same day
    private boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }

    // Method to show checked habits for the selected date
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

    // Method to save checked habits with the date
    private void saveCheckedHabitsWithDate(Date date, List<Habit> habitsForDate) {
        // Here, you can save the checked habits with their corresponding date
        // For example, you can store them in SharedPreferences, a database, or any other storage mechanism
        // For demonstration purposes, let's print them
        for (Habit habit : habitsForDate) {
            Log.d("Checked Habit", habit.getHabitName() + " - " + date.toString());
        }
    }
}