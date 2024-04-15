package com.example.m;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class SignupActivity extends AppCompatActivity {
    EditText nameEditText, ageEditText, emailEditText, phoneEditText;
    SeekBar weightSeekBar;
    TextView weightValueTextView;
    Button dobButton, saveButton, loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        nameEditText = findViewById(R.id.nameEditText);
        ageEditText = findViewById(R.id.ageEditText);
        emailEditText = findViewById(R.id.emailEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        weightSeekBar = findViewById(R.id.weightSeekBar);
        weightValueTextView = findViewById(R.id.weightValueTextView);
        dobButton = findViewById(R.id.dobButton);
        saveButton = findViewById(R.id.saveButton);
        loginButton = findViewById(R.id.loginButton);

        // Set OnClickListener for Date of Birth button
        dobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // Set maximum value for the SeekBar
        weightSeekBar.setMax(300);

        // Set OnSeekBarChangeListener for weight SeekBar
        weightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Update the TextView displaying the SeekBar value
                weightValueTextView.setText(progress + " kg");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        // Set OnClickListener for Save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the entered values
                String name = nameEditText.getText().toString();
                String age = ageEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                int weight = weightSeekBar.getProgress();

                // Save the data (you can implement your own logic here)
                // For demonstration purposes, we'll just show a toast message
                String message = "Name: " + name + "\n" +
                        "Age: " + age + "\n" +
                        "Email: " + email + "\n" +
                        "Phone: " + phone + "\n" +
                        "Weight: " + weight + " kg";
                Toast.makeText(SignupActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        // Set OnClickListener for Login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to LoginActivity
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    // Method to show DatePickerDialog
    private void showDatePickerDialog() {
        // Get current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create DatePickerDialog and show
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Display selected date in a TextView or perform any required action
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        dobButton.setText(selectedDate);
                    }
                },
                year, month, day);
        datePickerDialog.show();
    }
}