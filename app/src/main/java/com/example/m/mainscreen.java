package com.example.m;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class mainscreen extends AppCompatActivity {

    CardView imagesCard;
    CardView image;
    ViewFlipper flipper;
    Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);

        // Initialize views
        imagesCard = findViewById(R.id.imageCard);
        image = findViewById(R.id.audioCard);
        flipper = findViewById(R.id.flipper);
        logoutButton = findViewById(R.id.logoutButton);

        // Set click listeners for card views
        imagesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainscreen.this, MedicineActivity.class);
                startActivity(intent);
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainscreen.this, ListViewActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for logout button
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainscreen.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // Display images in the ViewFlipper
        int imgArray[] = {R.drawable.slide1, R.drawable.slide2, R.drawable.slide3, R.drawable.slide4, R.drawable.slide5, R.drawable.slide6};
        for (int img : imgArray) {
            showImage(img);
        }
    }

    public void showImage(int img) {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(img);
        flipper.addView(imageView);
        flipper.setFlipInterval(10000);
        flipper.setAutoStart(true);
        flipper.setInAnimation(this, android.R.anim.slide_in_left);
        flipper.setOutAnimation(this, android.R.anim.slide_out_right);
    }
}