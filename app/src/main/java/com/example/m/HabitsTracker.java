package com.example.m;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class HabitsTracker extends AppCompatActivity {

    private ListView TaskList;
    private ArrayAdapter<String> arrAdapter;
    private static final int MENU_ADD_TASK = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habits_tracker);

        TaskList = findViewById(R.id.list_todo);

        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        menu.add(Menu.NONE, MENU_ADD_TASK, Menu.NONE, "Add Habit");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_ADD_TASK:
                showAddTaskDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showAddTaskDialog() {
        final EditText taskEdit = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Add a new Habit")
                .setMessage("What habit do you want to do next?")
                .setView(taskEdit)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = taskEdit.getText().toString();
                        addTask(task);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    public void deleteTask(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = parent.findViewById(R.id.title_task);
        String task = String.valueOf(taskTextView.getText());
        arrAdapter.remove(task);
        arrAdapter.notifyDataSetChanged();
    }

    private void addTask(String task) {
        arrAdapter.add(task);
        arrAdapter.notifyDataSetChanged();
    }

    private void updateUI() {
        ArrayList<String> taskList = new ArrayList<>();


        if (arrAdapter == null) {
            arrAdapter = new ArrayAdapter<>(this, R.layout.todo_task, R.id.title_task, taskList);
            TaskList.setAdapter(arrAdapter);
        } else {
            arrAdapter.clear();
            arrAdapter.addAll(taskList);
            arrAdapter.notifyDataSetChanged();
        }
    }
}