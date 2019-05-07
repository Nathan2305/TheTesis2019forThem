package com.example.appforthem.SettingsActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.appforthem.Clases.Constants;
import com.example.appforthem.R;

import static com.example.appforthem.Activities.HomeActivity.prefsEditor;
import static com.example.appforthem.Activities.HomeActivity.sharedPreferences;

public class AlertaSettings extends AppCompatActivity {
        private Switch aSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerta_settings);
        aSwitch=findViewById(R.id.switch_alerta);
        if (sharedPreferences != null){
            if (sharedPreferences.contains(Constants.BTN_TXT_VALUE)){
               /*if (sharedPreferences.getBoolean(Constants.ALARMA_ACTIVA, true)){
                    aSwitch.setText(getResources().getString(R.string.alarma_en_curso_msg));
                }else{
                    aSwitch.setText(getResources().getString(R.string.alarma_sin_activar_msg));
                }*/
                aSwitch.setChecked(sharedPreferences.getBoolean(Constants.ALARMA_ACTIVA, true));
                aSwitch.setText(sharedPreferences.getString(Constants.BTN_TXT_VALUE,""));

            }
        }
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (!isChecked){  //DESACTIVAR ALARMA
                        prefsEditor.putBoolean(Constants.ALARMA_ACTIVA,false);
                        prefsEditor.putBoolean(Constants.BTN_ENABLED,true);
                        prefsEditor.putString(Constants.BTN_TXT_VALUE,"Enviar Alerta");
                    }else{  // ACTIVAR ALARMA
                        prefsEditor.putBoolean(Constants.ALARMA_ACTIVA,true);
                        prefsEditor.putBoolean(Constants.BTN_ENABLED,false);
                        prefsEditor.putString(Constants.BTN_TXT_VALUE,"Alerta Enviada");
                    }
                prefsEditor.apply();
                aSwitch.setText(sharedPreferences.getString(Constants.BTN_TXT_VALUE,""));
            }
        });
    }
}
