package com.example.appforthem.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.appforthem.Clases.Constants;
import com.example.appforthem.R;

import static com.example.appforthem.Activities.HomeActivity.sharedPreferences;


public class PinActivityNext extends AppCompatActivity {

    private EditText key1;
    private EditText key2;
    private EditText key3;
    private EditText key4;
    private TextView cancel;
    String firstKey = "";
    String secondKey = "";
    String thirdKey = "";
    String lastKey = "";
    public static String pinFrom = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);
        key1 = findViewById(R.id.key1);
        key2 = findViewById(R.id.key2);
        key3 = findViewById(R.id.key3);
        key4 = findViewById(R.id.key4);
        cancel = findViewById(R.id.cancel);
        String passPIN = sharedPreferences.getString(Constants.SET_PIN, "");
        int lengthPIN = passPIN.length();
        firstKey = passPIN.substring(0, 1);
        secondKey = passPIN.substring(1, 2);
        thirdKey = passPIN.substring(2, 3);
        lastKey = passPIN.substring(lengthPIN - 1);
        pinFrom = PinActivityNext.class.getSimpleName();
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
                /*Integer editText1Length = key1.getText().toString().length();
                if (editText1Length > 0) {
                    key2.requestFocus();
                }*/
            }

            @Override
            public void afterTextChanged(Editable s) {
                Integer editText1Length = key1.getText().toString().length();
                if (editText1Length > 0) {
                    key2.requestFocus();
                }
                checkPIN(key1, key2, key3, key4);
            }
        });
        key2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                /*Integer editText1Length = key2.getText().toString().length();
                if (editText1Length > 0) {
                    key3.requestFocus();
                }*/
            }

            @Override
            public void afterTextChanged(Editable s) {
                Integer editText1Length = key2.getText().toString().length();
                if (editText1Length > 0) {
                    key3.requestFocus();
                }
                checkPIN(key1, key2, key3, key4);
            }
        });
        key3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                /*Integer editText1Length = key3.getText().toString().length();
                if (editText1Length > 0) {
                    key4.requestFocus();
                }*/
            }

            @Override
            public void afterTextChanged(Editable s) {
                Integer editText1Length = key3.getText().toString().length();
                if (editText1Length > 0) {
                    key4.requestFocus();
                }
                checkPIN(key1, key2, key3, key4);
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
                checkPIN(key1, key2, key3, key4);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Ajustes.class.getSimpleName().equalsIgnoreCase(pinFrom)) {
            finish();
        }
    }

    public void checkPIN(EditText key1, EditText key2, EditText key3, EditText key4) {
        String k1 = key1.getText().toString();
        String k2 = key2.getText().toString();
        String k3 = key3.getText().toString();
        String k4 = key4.getText().toString();
        if (k1.equalsIgnoreCase(firstKey)
                && k2.equalsIgnoreCase(secondKey)
                && k3.equalsIgnoreCase(thirdKey)
                && k4.equalsIgnoreCase(lastKey)) {
            key1.setEnabled(false);
            key2.setEnabled(false);
            key3.setEnabled(false);
            key4.setEnabled(false);
            startActivity(new Intent(getApplicationContext(), Ajustes.class));
        }
    }
}
