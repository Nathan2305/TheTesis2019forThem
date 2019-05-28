package com.example.appforthem.Activities;


import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.appforthem.Clases.AdapterForAjustes;
import com.example.appforthem.Clases.AdapterForSheetOption;
import com.example.appforthem.R;
import com.example.appforthem.SettingsActivities.AlertaSettings;

import static com.example.appforthem.Activities.PinActivityNext.pinFrom;


public class Ajustes extends AppCompatActivity {

    public static boolean isLogout = false;
    RecyclerView listaAjustes;
    RecyclerView.LayoutManager layoutManager;
    BottomSheetDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);
        pinFrom = Ajustes.class.getSimpleName();
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
                    case 2:
                        //startActivity(new Intent(getApplicationContext(), ActivitySetPin.class));
                        showSheetDialog();
                        break;
                }
            }

            @Override
            public void onDeleteClick(int position) {
                //removeItem(position);
            }
        });

    }


    public void showSheetDialog() {
        View view = getLayoutInflater().inflate(R.layout.sheet_dialog, null);
        dialog = new BottomSheetDialog(this);
        RecyclerView recyler = view.findViewById(R.id.recyler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyler.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter = new AdapterForSheetOption(getApplicationContext());
        recyler.setAdapter(adapter);
        ((AdapterForSheetOption) adapter).setOnItemClickListener(new AdapterForSheetOption.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(getApplicationContext(), ActivitySetPin.class));
                        dialog.dismiss();
                        break;
                    case 1:
                        break;
                    case 2:
                        startActivity(new Intent(getApplicationContext(), ActivitySetPatron.class));
                        dialog.dismiss();
                }
            }
        });
        dialog.setContentView(view);
        dialog.show();
    }


}
