package com.example.m;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MedicineActivity extends AppCompatActivity {
    private RecyclerView medRecView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        medRecView = findViewById(R.id.MedRV);
        //sample data
        ArrayList<Medicine> medicines = new ArrayList<>();
        medicines.add(new Medicine("Baclofen"));
        medicines.add(new Medicine("clindamycin"));
        medicines.add(new Medicine("Erlotinib"));
        medicines.add(new Medicine("Foscarnet"));
        medicines.add(new Medicine("Vitamin D3"));
        medicines.add(new Medicine("Isotretinoin"));
        medicines.add(new Medicine("Vitamin C"));
        medicines.add(new Medicine("Valproic acid"));
        MedicineRecViewAdapter adapter = new MedicineRecViewAdapter();
        adapter.setMedicines(medicines);
        medRecView.setAdapter(adapter);
        medRecView.setLayoutManager(new LinearLayoutManager(this));
    }
}