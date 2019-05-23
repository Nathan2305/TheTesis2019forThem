package com.example.appforthem.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.appforthem.Clases.BackendlessSettings;
import com.example.appforthem.Clases.Constants;
import com.example.appforthem.R;

import static com.example.appforthem.Activities.HomeActivity.prefsEditor;

public class ActivitySetPin extends AppCompatActivity {
    private boolean k1;
    private boolean k2;
    private boolean k3;
    private boolean k4;
    private EditText key1;
    private EditText key2;
    private EditText key3;
    private EditText key4;
    private Button cancelar_btn;
    private Button guardar_btn;
    private TextView textPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pin);
        key1 = findViewById(R.id.key1);
        key2 = findViewById(R.id.key2);
        key3 = findViewById(R.id.key3);
        key4 = findViewById(R.id.key4);
        textPin = findViewById(R.id.textPin);
        cancelar_btn = findViewById(R.id.cancelar_btn);
        guardar_btn = findViewById(R.id.guardar_btn);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        key1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer editText1Length = key1.getText().toString().length();
                if (editText1Length > 0) {
                    key2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        key2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer editText1Length = key2.getText().toString().length();
                if (editText1Length > 0) {
                    key3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        key3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer editText1Length = key3.getText().toString().length();
                if (editText1Length > 0) {
                    key4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        key4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        guardar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String k1 = key1.getText().toString();
                final String k2 = key2.getText().toString();
                final String k3 = key3.getText().toString();
                final String k4 = key4.getText().toString();
                if (validatePass(k1, k2, k3, k4)) {
                    cleanAllText();
                    key1.requestFocus();
                    textPin.setText("Repita Pin de Acceso");
                    guardar_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String k1_2 = key1.getText().toString();
                            String k2_2 = key2.getText().toString();
                            String k3_2 = key3.getText().toString();
                            String k4_2 = key4.getText().toString();
                            if (validatePass(k1_2, k2_2, k3_2, k4_2)) {
                                if (k1.equalsIgnoreCase(k1_2) &&
                                        k2.equalsIgnoreCase(k2_2) &&
                                        k3.equalsIgnoreCase(k3_2) &&
                                        k4.equalsIgnoreCase(k4_2)){
                                    BackendlessSettings.showToast(getApplicationContext(),"Nuevo Pin guardado con éxito");
                                    prefsEditor.putString(Constants.SET_PIN,k1+k2+k3+k4);
                                    prefsEditor.putBoolean(Constants.PIN_ENABLED,true);
                                    prefsEditor.apply();
                                    finish();
                                }else{
                                    BackendlessSettings.showToast(getApplicationContext(),"Pin no coinciden");
                                }
                            }
                        }
                    });
                }
            }
        });
        cancelar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public boolean validatePass(String k1, String k2, String k3, String k4) {
        if (!"".equalsIgnoreCase(k1) &&
                !"".equalsIgnoreCase(k2) &&
                !"".equalsIgnoreCase(k3) &&
                !"".equalsIgnoreCase(k4)) {
            return true;
        } else
            return false;
    }

    public void cleanAllText() {
        key1.setText("");
        key2.setText("");
        key3.setText("");
        key4.setText("");
    }
}
