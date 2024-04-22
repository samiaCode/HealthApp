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
import android.widget.Toast;

import java.util.ArrayList;

public class HabitsTracker extends AppCompatActivity {

    private ListView TaskList;
    private ArrayAdapter<String> arrAdapter;

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
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.habit) {
            showAddTaskDialog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void showAddTaskDialog() {
        final EditText taskEdit = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Add a new task")
                .setMessage("What do you want to do next?")
                .setView(taskEdit)
                .setPositiveButton("Add", (dialogInterface, which) -> {
                    String task = String.valueOf(taskEdit.getText());
                    addTask(task);
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    private void addTask(String task) {
        if (task.trim().isEmpty()) {
            Toast.makeText(this, "Task cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        arrAdapter.add(task);
    }

    public void deleteTask(int position) {
        arrAdapter.remove(arrAdapter.getItem(position));
    }

    private void updateUI() {
        ArrayList<String> taskList = new ArrayList<>();

        if (arrAdapter == null) {
            arrAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskList);
            TaskList.setAdapter(arrAdapter);
        } else {
            arrAdapter.notifyDataSetChanged();
        }
    }
}