package com.example.m;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import static com.example.m.MedicineActivity.PREFS_NAME;
import static com.example.m.MedicineActivity.PREF_MEDICINES;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import Model.Medicine;

public class MedicineActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "MedicinePrefs";
    public static final String PREF_MEDICINES = "medicines";
    private RecyclerView medRecView;
    private FloatingActionButton addMedFBTN;
    private ArrayList<String> medicines;
    private MedicineRecViewAdapter adapter;
    private static final int REQUEST_CODE_MEDICINE_ADDED = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);
        FloatingActionButton backButton = findViewById(R.id.Back);

        addMedFBTN = findViewById(R.id.addMedFBTN);
        medRecView = findViewById(R.id.MedRV);

        medicines = loadMedicines();

        adapter = new MedicineRecViewAdapter();

        medRecView.setAdapter(adapter);
        medRecView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setMedicines(medicines);

        addMedFBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicineActivity.this, MedicineAddEditActivity.class);
                startActivityForResult(intent, REQUEST_CODE_MEDICINE_ADDED);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the main screen
                Intent intent = new Intent(MedicineActivity.this, mainscreen.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_MEDICINE_ADDED && resultCode == RESULT_OK) {
            if (data != null) {
                String newMedicine = data.getStringExtra("newMedicine");

                medicines.add(newMedicine);

                saveMedicines(medicines);

                Intent intent = new Intent(MedicineActivity.this, MedicineHistory.class);
                intent.putStringArrayListExtra("medicines", medicines);
                startActivity(intent);

                finish();
            }
        }
    }

    private void saveMedicines(ArrayList<String> medicines) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> medicineSet = new HashSet<>(medicines);
        editor.putStringSet(PREF_MEDICINES, medicineSet);
        editor.apply();
    }

    private ArrayList<String> loadMedicines() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Set<String> medicineSet = sharedPreferences.getStringSet(PREF_MEDICINES, new HashSet<String>());
        return new ArrayList<>(medicineSet);
    }
}