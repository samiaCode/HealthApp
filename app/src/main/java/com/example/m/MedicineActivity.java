package com.example.m;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MedicineActivity extends AppCompatActivity {
    private RecyclerView medRecView;
    private FloatingActionButton addMedFBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        addMedFBTN = findViewById(R.id.addMedFBTN);
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

        addMedFBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicineActivity.this, MedicineAddEditActivity.class);
                startActivity(intent);
            }
        });

    }
}