package com.example.m;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.nio.FloatBuffer;

public class MedicineAddEditActivity extends AppCompatActivity {

    private TextView timeTV, dateTV;
    private Button timeBTN, dateBTN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_add_edit);

        timeTV = (TextView) findViewById(R.id.timeTv);
        timeBTN = (Button) findViewById(R.id.setTimeBTN);
        dateTV = (TextView) findViewById(R.id.dateTv);
        dateBTN = (Button) findViewById(R.id.setDateBTN);
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