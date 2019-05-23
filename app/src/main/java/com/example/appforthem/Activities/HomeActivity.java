package com.example.appforthem.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.backendless.exceptions.BackendlessException;
import com.example.appforthem.Clases.BackendlessSettings;
import com.example.appforthem.Clases.Constants;
import com.example.appforthem.Clases.CustomAdapterOptions;
import com.example.appforthem.Clases.ServiceMap;
import com.example.appforthem.Clases.UserSessionManager;
import com.example.appforthem.R;
import com.github.ybq.android.spinkit.style.MultiplePulseRing;

import java.io.File;


import static com.example.appforthem.Activities.LoginActivity.backendlessUser;

public class HomeActivity extends AppCompatActivity {
    public static String whereActivity = "";
    private Button btn_alerta;
    private TextView datosUser;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor prefsEditor;
    private GridView opciones;
    public static ProgressBar progressBar;
    private MultiplePulseRing multiplePulseRing;
    public static String FOLDER_AUDIO = "";
    private String sdCardState = "";
    public static LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sdCardState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equalsIgnoreCase(sdCardState)) {
            FOLDER_AUDIO = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AppForThem/Audios";
            File file = new File(FOLDER_AUDIO);
            if (!file.exists()) {
                if (file.mkdirs()) {  //crea directios incluyendo la carpeta padre
                    BackendlessSettings.showToast(getApplicationContext(), "Se creó el directorio " + FOLDER_AUDIO);
                } else {
                    BackendlessSettings.showToast(getApplicationContext(), "No se creó el directorio " + FOLDER_AUDIO);
                }
            }
        }
        multiplePulseRing = new MultiplePulseRing();
        progressBar = findViewById(R.id.pbHome);
        btn_alerta = findViewById(R.id.alert);
        opciones = findViewById(R.id.opciones);
        datosUser = findViewById(R.id.datosUser);
        if (!runtime_permissions()) {
            enable_buttons();
        }
        initData();
        makeGridView();
    }

    private void enable_buttons() {
        btn_alerta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constants.ENVIAR_ALERTA.equalsIgnoreCase(btn_alerta.getText().toString())) {
                    if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        sendAlert();
                        prefsEditor.putString(Constants.BTN_TXT_VALUE, Constants.ALERTA_ENVIADA);
                        prefsEditor.putBoolean(Constants.BTN_ENABLED, false);
                        prefsEditor.putBoolean(Constants.ALARMA_ACTIVA, true);
                        prefsEditor.apply();
                        btn_alerta.setText(sharedPreferences.getString(Constants.BTN_TXT_VALUE, ""));
                        btn_alerta.setEnabled(sharedPreferences.getBoolean(Constants.BTN_ENABLED, true));
                    } else {
                        Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    }
                }
            }
        });
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
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgressDrawable(multiplePulseRing);
        startService(new Intent(this, ServiceMap.class));
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
                enable_buttons();
            } else {
                runtime_permissions();
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        whereActivity = HomeActivity.class.getSimpleName();
        if (sharedPreferences != null) {
            if (sharedPreferences.contains(Constants.BTN_TXT_VALUE)) {
                btn_alerta.setText(sharedPreferences.getString(Constants.BTN_TXT_VALUE, ""));
            }
            if (sharedPreferences.contains(Constants.BTN_ENABLED)) {
                btn_alerta.setEnabled(sharedPreferences.getBoolean(Constants.BTN_ENABLED, false));
            }
            if (sharedPreferences.contains("COLOR_BTN")) {
                btn_alerta.setBackgroundColor(sharedPreferences.getInt("COLOR_BTN", 0));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initData() {
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        try {
            if (backendlessUser == null) {
                backendlessUser = UserSessionManager.getBackendlessUser();
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(backendlessUser.getProperty("name").toString()).
                    append(" " + backendlessUser.getProperty("last_name").toString());
            datosUser.setText(stringBuilder.toString());
        } catch (BackendlessException e) {
            System.out.println("Exception trying to get BackendlessUser - " + e.getMessage());
        }
        sharedPreferences = getApplicationContext().getSharedPreferences("HOME", MODE_PRIVATE);
        prefsEditor = sharedPreferences.edit();
    }

    private void makeGridView() {
        int images[] = new int[]{R.drawable.alerta, R.drawable.camera_icon, R.drawable.woman_profil3, R.drawable.woman_face2,
                R.drawable.woman_profile2, R.drawable.settings};
        String titulo[] = new String[]{"Protector", "Opcion 2", "Opcion 3", "Opcion 4", "Opcion 5", "Ajustes"};
        CustomAdapterOptions adapterOptions = new CustomAdapterOptions(getApplicationContext(), images, titulo);
        opciones.setAdapter(adapterOptions);
        opciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(getApplicationContext(), Protector.class));
                        break;
                    case 5:
                        if (sharedPreferences.contains(Constants.PIN_ENABLED)) {
                            if (!sharedPreferences.getBoolean(Constants.PIN_ENABLED, true)) {
                                startActivity(new Intent(getApplicationContext(), ActivitySetPin.class));
                            } else {
                                startActivity(new Intent(getApplicationContext(), PinActivityNext.class));
                            }
                        } else {
                            startActivity(new Intent(getApplicationContext(), ActivitySetPin.class));
                        }
                        break;
                }
            }
        });
    }
}

