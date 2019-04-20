package com.example.appforthem.Activities;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.ProgressBar;

import com.example.appforthem.R;
import com.github.ybq.android.spinkit.style.ChasingDots;


public class CustomProgressBar extends AppCompatActivity {
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_progress_bar);
        progressBar = findViewById(R.id.spin_kit);
        ChasingDots chasingDots = new ChasingDots();
        progressBar.setProgressDrawable(chasingDots);

    }



}