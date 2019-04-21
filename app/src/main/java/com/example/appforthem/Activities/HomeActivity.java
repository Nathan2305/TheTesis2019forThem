package com.example.appforthem.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.appforthem.Clases.CustomAdapterOptions;
import com.example.appforthem.Clases.ServiceMap;
import com.example.appforthem.R;
import static com.example.appforthem.Activities.LoginActivity.backendlessUser;

public class HomeActivity extends AppCompatActivity {
    Button btn_alerta;
    TextView datosUser;
    String BTN_ALERTA = "";
    String BTN_STOP_ALERTA = "";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor prefsEditor;
    GridView opciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btn_alerta = findViewById(R.id.alert);
        opciones = findViewById(R.id.opciones);
        datosUser = findViewById(R.id.datosUser);
        datosUser.setText(backendlessUser.getProperty("name").toString() + " " + backendlessUser.getProperty("last_name").toString());
        BTN_ALERTA = "ENVIAR ALERTA";
        BTN_STOP_ALERTA = "DETENER ALERTA";
        int images[] = new int[]{R.drawable.alerta, R.drawable.camera_icon, R.drawable.woman_profil3, R.drawable.woman_face2,
                R.drawable.woman_profile2, R.drawable.settings};
        String titulo[] = new String[]{"Protector", "Opcion 2", "Opcion 3", "Opcion 4", "Opcion 5", "Ajustes"};
        CustomAdapterOptions adapterOptions = new CustomAdapterOptions(getApplicationContext(), images, titulo);
        opciones.setAdapter(adapterOptions);
        sharedPreferences = getApplicationContext().getSharedPreferences("HOME", MODE_PRIVATE);
        prefsEditor = sharedPreferences.edit();

        if (sharedPreferences != null && sharedPreferences.contains("BTN_TEXT_PRESSED")) {
            btn_alerta.setText(sharedPreferences.getString("BTN_TEXT_PRESSED", ""));
        }
        btn_alerta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BTN_STOP_ALERTA.equalsIgnoreCase(btn_alerta.getText().toString())) {
                    stopAlert();
                    showToast("DETENER ALERTA");
                    prefsEditor.putString("BTN_TEXT_PRESSED", BTN_ALERTA);
                } else {
                    sendAlert();
                    prefsEditor.putString("BTN_TEXT_PRESSED", BTN_STOP_ALERTA);

                }
                prefsEditor.apply();
                btn_alerta.setText(sharedPreferences.getString("BTN_TEXT_PRESSED", ""));

            }
        });

        opciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(getApplicationContext(), Protector.class));
                        break;
                    case 5:
                        startActivity(new Intent(getApplicationContext(), Ajustes.class));
                        break;

                }
            }
        });
    }

    @SuppressLint("WrongConstant")
    private void showProgressBar() {
        startActivity(new Intent(getApplicationContext(), CustomProgressBar.class));
    }

    private void stopAlert() {
        stopService(new Intent(this, ServiceMap.class));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.map) {
            startActivity(new Intent(getApplicationContext(), MapsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendAlert() {
        startService(new Intent(this, ServiceMap.class));
    }

    void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private boolean runtime_permissions() {
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                //enableAlerta();
            } else {
                runtime_permissions();
            }
        }
    }

    /*
    private void enableAlerta() {
        btn_alerta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendAlert();
            }
        });
    }*/
}
