package com.example.appforthem.Activities;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.example.appforthem.Clases.AdapterForAgresores;
import com.example.appforthem.R;
import com.github.ybq.android.spinkit.style.FadingCircle;

import java.util.ArrayList;

public class AgresorActivity extends AppCompatActivity {
    RecyclerView agresores;
    RecyclerView.LayoutManager layoutManager;
    ProgressBar progressBar;
    FadingCircle fadingCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_agresores);
        progressBar = findViewById(R.id.pbAgresores);
        fadingCircle = new FadingCircle();
        progressBar.setProgressDrawable(fadingCircle);
        agresores = findViewById(R.id.agresores);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        RecyclerView.Adapter adapter = new AdapterForAgresores(this)/*, response)*/;
        agresores.setAdapter(adapter);
        agresores.setLayoutManager(layoutManager);
    }
}
