package com.example.m;


import static com.example.m.MedicineActivity.PREFS_NAME;
import static com.example.m.MedicineActivity.PREF_MEDICINES;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import Model.Medicine;

public class MedicineHistory extends AppCompatActivity {
    private ListView medList;
    private ArrayAdapter<String> medAdapter;
    private ArrayList<String> medicines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_history);

        medList = findViewById(R.id.medList);

        medicines = loadMedicines();

        if (medicines != null && !medicines.isEmpty()) {
            medAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, medicines);

            medList.setAdapter(medAdapter);
        } else {
            Toast.makeText(this, "No medicines found", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private ArrayList<String> loadMedicines() {
        SharedPreferences sharedPreferences = getSharedPreferences(MedicineActivity.PREFS_NAME, Context.MODE_PRIVATE);
        Set<String> medicineSet = sharedPreferences.getStringSet(MedicineActivity.PREF_MEDICINES, new HashSet<String>());
        return new ArrayList<>(medicineSet);
    }
}