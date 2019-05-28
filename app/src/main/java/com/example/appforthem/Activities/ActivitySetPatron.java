package com.example.appforthem.Activities;

import static com.example.appforthem.Activities.HomeActivity.prefsEditor;
import static com.example.appforthem.Activities.HomeActivity.sharedPreferences;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.example.appforthem.Clases.BackendlessSettings;
import com.example.appforthem.Clases.Constants;
import com.example.appforthem.R;

import java.util.List;

public class ActivitySetPatron extends AppCompatActivity {
    com.andrognito.patternlockview.PatternLockView mPatternLockView;
    String patron = "";
    TextView aviso;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_patron);
        aviso = findViewById(R.id.aviso);
        mPatternLockView = findViewById(R.id.pattern_lock_view);
        mPatternLockView.addPatternLockListener(mPatternLockViewListener);

    }

    private PatternLockViewListener mPatternLockViewListener = new PatternLockViewListener() {
        @Override
        public void onStarted() {
            System.out.println("ON STARTED" + getClass().getName() + " Pattern drawing started");
        }

        @Override
        public void onProgress(List<PatternLockView.Dot> progressPattern) {
            System.out.println("ON PROGRESS" + getClass().getName() + " Pattern progress: " +
                    PatternLockUtils.patternToString(mPatternLockView, progressPattern));
        }

        @Override
        public void onComplete(List<PatternLockView.Dot> pattern) {
            System.out.println("ON COMPLETE " + getClass().getName() + " Pattern complete: " +
                    PatternLockUtils.patternToString(mPatternLockView, pattern));
            if (count > 0) {
                if (sharedPreferences.contains("total_clave")) {
                    if (!sharedPreferences.getString("total_clave", "").equalsIgnoreCase(PatternLockUtils.patternToString(mPatternLockView, pattern))) {
                        BackendlessSettings.showToast(getApplicationContext(), "Patron no coinciden");
                    } else {
                        BackendlessSettings.showToast(getApplicationContext(), "Nuevo patron guardado con éxito");
                        prefsEditor.putString("WHAT_KIND_SEC", Constants.PATRON);
                        prefsEditor.apply();
                        finish();
                    }
                }
            }
            if (count == 0) {
                patron = PatternLockUtils.patternToString(mPatternLockView, pattern);
                prefsEditor.putString("total_clave", patron);
                prefsEditor.apply();
                pattern.clear();
                count++;
                aviso.setText("Vuelve a registrar tu patrón");
            }
        }

        @Override
        public void onCleared() {
            System.out.println("ON CLEARED" + getClass().getName() + " Pattern has been cleared");
        }
    };
}
