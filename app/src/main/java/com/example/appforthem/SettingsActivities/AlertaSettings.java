package com.example.appforthem.SettingsActivities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.Switch;

import com.example.appforthem.Clases.Constants;
import com.example.appforthem.Clases.ServiceMap;
import com.example.appforthem.R;

import static com.example.appforthem.Activities.HomeActivity.prefsEditor;
import static com.example.appforthem.Activities.HomeActivity.sharedPreferences;

public class AlertaSettings extends AppCompatActivity {
    private Switch switchAlarma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerta_settings);
        switchAlarma = findViewById(R.id.switch_alerta);
        if (sharedPreferences != null) {
            if (sharedPreferences.contains(Constants.BTN_ENABLED)) {
                prefsEditor = sharedPreferences.edit();
                if (sharedPreferences.getBoolean(Constants.BTN_ENABLED, false)) {
                    prefsEditor.putString(Constants.SWITCH_ALARMA_TEXT, Constants.ACTIVAR_ALARMA);
                    prefsEditor.putBoolean(Constants.ALARMA_ACTIVA, false);
                } else {
                    prefsEditor.putString(Constants.SWITCH_ALARMA_TEXT, Constants.DESACTIVAR_ALARMA);
                    prefsEditor.putBoolean(Constants.ALARMA_ACTIVA, true);
                }
            } else {
                prefsEditor.putString(Constants.SWITCH_ALARMA_TEXT, Constants.ACTIVAR_ALARMA);
                prefsEditor.putBoolean(Constants.ALARMA_ACTIVA, false);
            }
            prefsEditor.apply();
            switchAlarma.setChecked(sharedPreferences.getBoolean(Constants.ALARMA_ACTIVA, true));
            switchAlarma.setText(sharedPreferences.getString(Constants.SWITCH_ALARMA_TEXT, ""));
        }
        switchAlarma.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {  //DESACTIVAR ALARMA
                    showDialogPromtToCancelAlert();
                } else {  // ACTIVAR ALARMA
                    if (!sharedPreferences.getBoolean(Constants.ALARMA_ACTIVA, true)) {
                        startService(new Intent(getApplicationContext(), ServiceMap.class));
                        prefsEditor.putBoolean(Constants.ALARMA_ACTIVA, true);
                        prefsEditor.putBoolean(Constants.BTN_ENABLED, false);
                        prefsEditor.putString(Constants.BTN_TXT_VALUE, "ALERTA ENVIADA");
                        prefsEditor.putString(Constants.SWITCH_ALARMA_TEXT, Constants.DESACTIVAR_ALARMA);
                        prefsEditor.apply();
                        switchAlarma.setText(sharedPreferences.getString(Constants.SWITCH_ALARMA_TEXT, ""));
                    }
                }
            }
        });
    }

    private void showDialogPromtToCancelAlert() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(getString(R.string.desactivar_alarma)).
                setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        prefsEditor.putBoolean(Constants.ALARMA_ACTIVA, false);
                        prefsEditor.putBoolean(Constants.BTN_ENABLED, true);
                        prefsEditor.putString(Constants.BTN_TXT_VALUE, Constants.ENVIAR_ALERTA);
                        prefsEditor.putString(Constants.SWITCH_ALARMA_TEXT, Constants.ACTIVAR_ALARMA);
                        prefsEditor.apply();
                        stopService(new Intent(getApplicationContext(), ServiceMap.class));
                        switchAlarma.setText(sharedPreferences.getString(Constants.SWITCH_ALARMA_TEXT, ""));
                    }
                }).
                setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        prefsEditor.putBoolean(Constants.ALARMA_ACTIVA, true);
                        prefsEditor.putBoolean(Constants.BTN_ENABLED, false);
                        prefsEditor.putString(Constants.BTN_TXT_VALUE, Constants.ENVIANDO_ALERTA);
                        prefsEditor.putString(Constants.SWITCH_ALARMA_TEXT, Constants.DESACTIVAR_ALARMA);
                        prefsEditor.apply();
                        switchAlarma.setChecked(sharedPreferences.getBoolean(Constants.ALARMA_ACTIVA, true));
                        switchAlarma.setText(sharedPreferences.getString(Constants.SWITCH_ALARMA_TEXT, ""));
                    }
                });
        dialog.setCancelable(false);
        dialog.create();
        dialog.show();
    }

}
