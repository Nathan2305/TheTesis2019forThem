package com.example.appforthem.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appforthem.R;
import com.example.appforthem.SettingsActivities.AlertaSettings;

public class PinActivity extends AppCompatActivity {
    private boolean k1;
    private boolean k2;
    private boolean k3;
    private boolean k4;
    private EditText key1;
    private EditText key2;
    private EditText key3;
    private EditText key4;
    private TextView cancel;
    public static String pinFrom="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);
        key1 = findViewById(R.id.key1);
        key2 = findViewById(R.id.key2);
        key3 = findViewById(R.id.key3);
        key4 = findViewById(R.id.key4);
        cancel = findViewById(R.id.cancel);
        pinFrom=PinActivity.class.getSimpleName();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
                k1 = false;
                if ("1".equalsIgnoreCase(key1.getText().toString())) {
                    k1 = true;
                }
                if (k1 && k2 && k3 && k4) {
                    key1.setEnabled(false);
                    key2.setEnabled(false);
                    key3.setEnabled(false);
                    key4.setEnabled(false);
                    startActivity(new Intent(getApplicationContext(), Ajustes.class));
                }
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
                k2 = false;
                if ("2".equalsIgnoreCase(key2.getText().toString())) {
                    k2 = true;
                }
                if (k1 && k2 && k3 && k4) {
                    key1.setEnabled(false);
                    key2.setEnabled(false);
                    key3.setEnabled(false);
                    key4.setEnabled(false);
                    startActivity(new Intent(getApplicationContext(), Ajustes.class));
                }
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
                k3 = false;
                if ("3".equalsIgnoreCase(key3.getText().toString())) {
                    k3 = true;
                }
                if (k1 && k2 && k3 && k4) {
                    key1.setEnabled(false);
                    key2.setEnabled(false);
                    key3.setEnabled(false);
                    key4.setEnabled(false);
                    startActivity(new Intent(getApplicationContext(), Ajustes.class));
                }
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
                k4 = false;
                if ("4".equalsIgnoreCase(key4.getText().toString())) {
                    k4 = true;
                }
                if (k1 && k2 && k3 && k4) {
                    key1.setEnabled(false);
                    key2.setEnabled(false);
                    key3.setEnabled(false);
                    key4.setEnabled(false);
                    startActivity(new Intent(getApplicationContext(), Ajustes.class));
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Ajustes.class.getSimpleName().equalsIgnoreCase(pinFrom)){
            finish();
        }
    }
}
