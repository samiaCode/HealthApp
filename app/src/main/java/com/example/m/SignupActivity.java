package com.example.m;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;

public class SignupActivity extends AppCompatActivity {
    EditText nameEditText, ageEditText, emailEditText, phoneEditText;
    SeekBar weightSeekBar;
    TextView weightValueTextView;
    Button dobButton, saveButton, loginButton;
    LinearLayout formLayout;
    RadioGroup genderRadioGroup;
    EditText passwordEditText;


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
        formLayout = findViewById(R.id.formLayout);
        genderRadioGroup = findViewById(R.id.genderRadioGroup);
        passwordEditText=findViewById(R.id.passwordEditText);

        dobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        weightSeekBar.setMax(300);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString().trim();
                String age = ageEditText.getText().toString().trim();
                String dob = dobButton.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();
                String pass=passwordEditText.getText().toString().trim();
                int weight = weightSeekBar.getProgress();
                int selectedGenderId = genderRadioGroup.getCheckedRadioButtonId();

                boolean isEmptyField = false;

                if (TextUtils.isEmpty(name)) {
                    isEmptyField = true;
                    nameEditText.setError(getString(R.string.warning_empty_field));
                }
                if (TextUtils.isEmpty(age)) {
                    isEmptyField = true;
                    ageEditText.setError(getString(R.string.warning_empty_field));
                }
                if (dob.equals("Select Date of Birth")) {
                    isEmptyField = true;
                    Toast.makeText(SignupActivity.this, "Please select your Date of Birth", Toast.LENGTH_SHORT).show();
                }
                if (selectedGenderId == -1) {
                    isEmptyField = true;
                    Toast.makeText(SignupActivity.this, "Please select your gender", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(email)) {
                    isEmptyField = true;
                    emailEditText.setError(getString(R.string.warning_empty_field));
                }
                if (TextUtils.isEmpty(phone)) {
                    isEmptyField = true;
                    phoneEditText.setError(getString(R.string.warning_empty_field));
                }
                if (weight == 0) {
                    isEmptyField = true;
                    Toast.makeText(SignupActivity.this, "Please select your weight", Toast.LENGTH_SHORT).show();
                }

                if (!isEmptyField) {
                    String gender;
                    if (selectedGenderId == R.id.maleRadioButton) {
                        gender = "Male";
                    } else {
                        gender = "Female";
                    }
                    if (TextUtils.isEmpty(name)) {
                        isEmptyField = true;
                        passwordEditText.setError(getString(R.string.warning_empty_field));
                    }

                    String password = generateHash(passwordEditText.getText().toString().trim());

                    saveUserData(email, password);

                    String message = "Name: " + name + "\n" +
                            "Age: " + age + "\n" +
                            "Date of Birth: " + dob + "\n" +
                            "Gender: " + gender + "\n" +
                            "Email: " + email + "\n" +
                            "Phone: " + phone + "\n" +
                            "Weight: " + weight + " kg";
                    Toast.makeText(SignupActivity.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignupActivity.this, "Please fill all the required fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to LoginActivity
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        dobButton.setText(selectedDate);
                    }
                },
                year, month, day);
        datePickerDialog.show();
    }

    private String generateHash(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private void saveUserData(String email, String hashedPassword) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putString("password", hashedPassword);
        editor.apply();
    }
}