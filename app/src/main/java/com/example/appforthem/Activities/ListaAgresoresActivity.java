package com.example.appforthem.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.appforthem.Clases.AdapterForAgresores;
import com.example.appforthem.R;

public class ListaAgresoresActivity extends AppCompatActivity {
    RecyclerView agresores;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_agresores);
        agresores=findViewById(R.id.agresores);
        layoutManager= new LinearLayoutManager(getApplicationContext());
        agresores.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter=new AdapterForAgresores(getApplicationContext());

    }
}
