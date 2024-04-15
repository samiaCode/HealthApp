package com.example.m;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class mainscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);

        // Start MainActivity after a short delay
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(mainscreen.this, MainActivity.class);
                startActivity(intent);
                finish(); // Finish mainscreen activity
            }
        }, 1); // Reduce the delay to 1 second for smoother transition
    }
}