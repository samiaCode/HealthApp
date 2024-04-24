package com.example.m;

import static com.example.m.MedicineAddEditActivity.FILE_NAME;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import Model.Medicine;

public class MedicineHistory extends AppCompatActivity {
    private ListView medList;
    private ArrayAdapter<Medicine> medAdapter;
    private ArrayList<Medicine> medicines;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_history);

        medList = findViewById(R.id.medList);
        medicines = new ArrayList<>();

        try {
            FileInputStream fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String temp = "";
            String text ="";
            int i =0;
            while((temp=br.readLine())!=null){
                medicines.add(i, new Medicine(temp));
                i++;
            }
            br.close();
            isr.close();
            fis.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        medAdapter = new ArrayAdapter<Medicine>(this, com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,medicines);


        medList.setAdapter(medAdapter);
    }
}