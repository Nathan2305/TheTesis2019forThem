package com.example.appforthem.SettingsActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Switch;

import com.example.appforthem.R;

import static com.example.appforthem.Activities.HomeActivity.sharedPreferences;

public class AlertaSettings extends AppCompatActivity {
        private Switch aSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerta_settings);
        aSwitch=findViewById(R.id.switch_alerta);
        if (sharedPreferences != null){
            if (sharedPreferences.contains("ALARMA_ACTIVA")){
                if (sharedPreferences.getBoolean("ALARMA_ACTIVA", true)){
                    aSwitch.setText("Alarma activa...");
                }else{
                    aSwitch.setText("Alarma sin activar..");
                }
                aSwitch.setChecked(sharedPreferences.getBoolean("ALARMA_ACTIVA", true));
            }
        }
    }
}
