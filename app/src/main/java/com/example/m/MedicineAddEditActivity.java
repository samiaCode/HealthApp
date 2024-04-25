package com.example.m;

import static android.app.NotificationChannel.DEFAULT_CHANNEL_ID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.FloatBuffer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MedicineAddEditActivity extends AppCompatActivity {

    private TextView timeTV, dateTV;
    private Button timeBTN, dateBTN, saveMedBTN;
    private EditText medET;
    private static final int REQUEST_CODE_MEDICINE_ADDED = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_add_edit);

        timeTV = findViewById(R.id.timeTv);
        timeBTN = findViewById(R.id.setTimeBTN);
        dateTV = findViewById(R.id.dateTv);
        dateBTN = findViewById(R.id.setDateBTN);
        medET = findViewById(R.id.medET);
        saveMedBTN = findViewById(R.id.saveMedBTN);

        timeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimeDialog();
            }
        });

        dateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDateDialog();
            }
        });

        saveMedBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String medName = medET.getText().toString();

                Intent resultIntent = new Intent();
                resultIntent.putExtra("newMedicine", medName);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

}
    private void openTimeDialog(){
        TimePickerDialog timeDial = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                timeTV.setText(String.valueOf(hourOfDay)+":"+String.valueOf(minute));
            }
        }, 11, 00, false);
        timeDial.show();
    }
    private void openDateDialog(){
        DatePickerDialog dateDial = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateTV.setText(String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(dayOfMonth));
            }
        }, 2024, 2, 1);
        dateDial.show();
    }
}