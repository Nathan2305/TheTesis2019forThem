package com.example.appforthem.Activities;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appforthem.Clases.AdapterForAjustes;
import com.example.appforthem.Clases.Ajuste;
import com.example.appforthem.R;
import com.example.appforthem.SettingsActivities.AlertaSettings;

import static com.example.appforthem.Activities.PinActivity.pinFrom;


public class Ajustes extends AppCompatActivity {

    public static boolean isLogout = false;
    RecyclerView listaAjustes;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);
        pinFrom= Ajustes.class.getSimpleName();
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
