package com.example.appforthem.Activities;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.example.appforthem.Clases.AdapterForAjustes;
import com.example.appforthem.Clases.BackendlessGeoUtils;
import com.example.appforthem.Clases.BackendlessHelperUtils;
import com.example.appforthem.Clases.BackendlessSettings;
import com.example.appforthem.R;
import com.example.appforthem.SettingsActivities.AlertaSettings;


public class Ajustes extends AppCompatActivity {
    public static boolean isLogout = false;
    RecyclerView listaAjustes;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);
        listaAjustes = findViewById(R.id.lista_ajustes);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        AdapterForAjustes adapterForAjustes = new AdapterForAjustes(getApplicationContext());
        listaAjustes.setLayoutManager(layoutManager);
        listaAjustes.setAdapter(adapterForAjustes);
        listaAjustes.hasFixedSize();
        adapterForAjustes.setOnItemClickListener(new AdapterForAjustes.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(getApplicationContext(), AlertaSettings.class));
                        break;
                }
            }

            @Override
            public void onDeleteClick(int position) {
                //removeItem(position);
            }
        });

    }


}
