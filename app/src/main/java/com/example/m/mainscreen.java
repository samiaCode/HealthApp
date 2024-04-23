package com.example.m;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class mainscreen extends AppCompatActivity {

    CardView imagesCard;
    CardView image;
    ViewFlipper flipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);

        imagesCard = findViewById(R.id.imageCard);

        imagesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainscreen.this, MedicineActivity.class);
                startActivity(intent);
            }
        });
        image = findViewById(R.id.audioCard);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainscreen.this, ListViewActivity.class);
                startActivity(intent);
            }
        });

        flipper = findViewById(R.id.flipper);
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